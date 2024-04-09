# member_id 를 바탕으로 과거 소비량 불러와서 시계열 예측하여 추후 3개월 소비량 예측

import pandas as pd
import oracledb
from statsmodels.tsa.arima.model import ARIMA
import datetime as dt

# input
member_id="test_th"

def predictCons(member_id):
    con=oracledb.connect(user="c##et", password="et", dsn="localhost:1521/orcl")

    query=f"SELECT * FROM CONSUMPTIONS WHERE CONSUMER_ID = (SELECT CONSUMER_ID FROM CONSUMERS WHERE MEMBER_ID = '{member_id}') ORDER BY CONS_DATE"
    df=pd.read_sql(query,con)
    data=df["CONS_ELECTRICITY"]
    model=ARIMA(data, order=(6,1,3))
    model_fit=model.fit()

    forecast=model_fit.forecast(steps=4)

    output_data={}
    for t, d in zip([i for i in range(4)],forecast.values):
        output_data[t]=d
    return output_data
