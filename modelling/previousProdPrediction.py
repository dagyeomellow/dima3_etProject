## 2024 04 07 작업(파이프라인 구성 완료)
# note: 아마도 db에서 불러오는 거는 oracle 이용방식으로 다 바꿔줘야 할듯(파이썬과 오라클 연동)
# 입력값: 위도, 경도 (예시 = 광진구(서울)) + 발전용량
lon=127.08310898209
lat=37.538313496774
installed_capacity=3


import pandas as pd
import geopandas as gpd
from shapely.geometry import Point
df=pd.read_csv('./db/WEATHER_POINT_SOLAR.csv', encoding='cp949')
for i,r in df.iterrows():
    df.loc[i,'geometry']=Point(r['LONGITUDE'],r['LATITUDE'])
gdf=gpd.GeoDataFrame(df,geometry='geometry',crs='EPSG:4326')

# 입력받은 지점과 가장 가까운 관측지점 찾기
## 입력받은 지점을 Point 객체로 변환
input_loc= Point(lon,lat)
## 입력받은 지점과의 거리차이 파생컬럼 생성
gdf['DISTANCE']=gdf['geometry'].apply(lambda x: input_loc.distance(x))
## 최단 거리 지점의 지점 번호 가져오기
stn=gdf[gdf['DISTANCE']==gdf['DISTANCE'].min()]['STN'].values[0]

# 최단거리 지점의 기상데이터 수집
df=pd.read_csv('./db/WEATHER.csv', encoding='cp949')
## stn이 같은 관측지점의 날씨정보만 가져오기
my_df=df[df['STN']==stn]
## 설비용량 추가
my_df['INSTALLED_CAPACITY']=installed_capacity

### 모델링 인풋 형식: 설비용량, 일사량, 평균기온
input_df=my_df[['INSTALLED_CAPACITY','SI_DAY','TA_AVG']]

import pickle
## 스케일러 및 모델 호출
with open('scalerProd.pickle', 'rb') as f:
    scaler=pickle.load(f)
with open('predictProd.pickle', 'rb') as f:
    model=pickle.load(f)

# 예측 수행하여 my_df에 예측량 추가
for i, r in input_df.iterrows():
    y_pred=model.predict(scaler.transform(r.values.reshape(-1,3)))
    ## 태양광발전량은 음수일 수 없으니 relu적용
    if y_pred<0:
        y_pred=0
    my_df.loc[i,'pred']=y_pred

# 예측값을 월별로 합산, 딕셔너리형태로 반환(키: 년월, 값: 예측값)
my_df['month']=pd.to_datetime(df['OBS_DATE']).apply(lambda x: x.strftime('%Y%m'))
month_pred=my_df.groupby('month')['pred'].sum()
output_data=dict()
for i,v in  zip(month_pred.index,month_pred):
    output_data[i]=v