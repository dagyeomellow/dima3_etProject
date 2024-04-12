## 생산량 추정
# lon=127.08310898209
# lat=37.538313496774

# input
# lon=126
# lat=38
# installed_capacity=3

# library
import os
print(os.getcwd)
import pandas as pd
import geopandas as gpd
from shapely.geometry import Point
import pickle
import oracledb

# location_x: 경도(longitude), location_y: 위도(latitude)
# return: map(키:년월, 값:추정발전량)
def predictRecentProduction(location_x, location_y, installed_capacity):

    try:
        con = oracledb.connect(user="c##et", password="et", dsn="localhost:1521/orcl")
    # 기존 코드...
        query=""

        query="SELECT * FROM WEATHER_POINT_SOLAR"
        df=pd.read_sql(query,con)
        # con.close()
        for i,r in df.iterrows():
            df.loc[i,'geometry']=Point(r['LONGITUDE'],r['LATITUDE'])
        gdf=gpd.GeoDataFrame(df,geometry='geometry',crs='EPSG:4326')

        # 입력받은 지점과 가장 가까운 관측지점 찾기
        ## 입력받은 지점을 Point 객체로 변환
        input_loc= Point(location_x,location_y)
        ## 입력받은 지점과의 거리차이 파생컬럼 생성
        gdf['DISTANCE']=gdf['geometry'].apply(lambda x: input_loc.distance(x))
        ## 최단 거리 지점의 지점 번호 가져오기
        stn=gdf[gdf['DISTANCE']==gdf['DISTANCE'].min()]['STN'].values[0]
        print(stn)
        print(gdf[gdf["STN"]==stn])

        query=f"SELECT * FROM WEATHER WHERE STN = '{stn}'"
        # con = oracledb.connect(user="c##et", password="et", dsn="localhost:1521/orcl")
        # 최단거리 지점의 기상데이터 수집
        df=pd.read_sql(query,con)
        con.close()
        # stn이 같은 관측지점의 날씨정보만 가져오기
        # my_df=df[df['STN']==stn]
        ## 설비용량 추가
        df['INSTALLED_CAPACITY']=installed_capacity

        ### 모델링 인풋 형식: 설비용량, 일사량, 평균기온
        input_df=df[['INSTALLED_CAPACITY','SI_DAY','TA_AVG']]


        ## 스케일러 및 모델 호출
        with open('./scalerProd.pickle', 'rb') as f:
            scaler=pickle.load(f)
        with open('./predictProd.pickle', 'rb') as f:
            model=pickle.load(f)

        # 예측 수행하여 my_df에 예측량 추가
        for i, r in input_df.iterrows():
            y_pred=model.predict(scaler.transform(r.values.reshape(-1,3)))
            ## 태양광발전량은 음수일 수 없으니 relu적용
            if y_pred<0:
                y_pred=0
            df.loc[i,'pred']=y_pred

        # 예측값을 월별로 합산, 리스트형태로 반환(키: 년월, 값: 예측값)
        df['month']=pd.to_datetime(df['OBS_DATE']).apply(lambda x: x.strftime('%Y%m'))
        month_pred=df.groupby('month')['pred'].sum()
        output_data=dict()
        for i,v in  zip(month_pred.index,month_pred):
            output_data[i]=v
    except Exception as e:
        print(f"Error occurred: {e}")
    finally:
        if con is not None:
            con.close()
    return output_data;

# result=predictRecentProduction(127,35,5)
# print(result)

from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from starlette.responses import JSONResponse

import pickle
import numpy as np
import pandas as pd

# Model 생성
class Item(BaseModel):
    locationX: float
    locationY: float
    installedCapacity: float

# app 개발
app= FastAPI()
@app.post(path="/",status_code=201)
async def read(item: Item):
    print("python 도착")

    dicted=dict(item)

    location_x=dicted['locationX']
    location_y=dicted['locationY']
    installed_capacity=dicted['installedCapacity']
        
    output_data=predictRecentProduction(location_x, location_y, installed_capacity)

    print("=======pred: ",output_data)

    return JSONResponse(output_data)


if __name__ == "__main__":
    uvicorn.run(app,host="127.0.0.1", port=8000) # terminal: uvicorn main:app --reload
    # 항상 유비콘이 먼저 구동되ㅐ고서 스프링이 구동되어야해
