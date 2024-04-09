# 차후 4개월 전기요금 시계열 예측: PCA, LSTM
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import MinMaxScaler
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense
## 역대 전기요금표 불러오기(2019년7월~2024년3월)
df=pd.read_csv('./db/prices.csv',encoding='cp949')
df['YEARMONTH']=df['YEARMONTH'].astype('str')
## 역대 물가, 예측전력수요, 
info_df=pd.read_csv('./db/KEPCO_DECISION.csv', encoding='cp949')
info_df.dropna()
## 전기요금표에 YEARMONTH 파생변수(조인용) 만들기
import datetime as dt
for i,r in info_df.iterrows():
    date=dt.datetime(r['YEAR'],r['MONTH'],1)
    info_df.loc[i,'YEARMONTH']=date.strftime('%Y%m')
# info_df

## 모델의 예측값 담아줄 데이터프레임 생성
result_df=pd.DataFrame(index= [ f'{dt.datetime.now().month+i}월_예측' if dt.datetime.now().month+i<13 else f'{dt.datetime.now().month+i-12}월_예측' for i in range(0)], columns=df.columns[1:])
# result_df
for c in result_df.columns:
    df_merge=pd.merge(info_df.drop(['YEAR','MONTH'],axis=1),df[['YEARMONTH',c]],how='left', on='YEARMONTH').dropna()
    data=df_merge.sort_values(by='YEARMONTH').drop('YEARMONTH',axis=1).reset_index().drop('index', axis=1) ## 시간순으로 정렬
    data['ACOAL_PRICE']=data['ACOAL_PRICE'].apply(lambda x: x.replace(',','')).astype('float') 
    data.loc[48,'ACOAL_PRICE']=(data.loc[47,'ACOAL_PRICE']+data.loc[49,'ACOAL_PRICE'])/2 ## ACOAL PRICE 0값 처리

    ## 9개의 변수를 2개로 축소하기
    from sklearn.decomposition import PCA
    pca = PCA(n_components=2) # 주성분을 몇개로 할지 결정
    printcipalComponents = pca.fit_transform(data.values[:,:-1])
    principalDf = pd.DataFrame(data=printcipalComponents, columns = ['principal component1', 'principal component2'])
    principalDf
    print("설명되는 분산: ",pca.explained_variance_ratio_)

    # 주성분과 타겟값 결합
    principalDf['target']= data[data.columns[-1]]
    tr_data=principalDf
    
    # 정규화(주성분만 스케일링)
    scaler = MinMaxScaler()
    scaled_data = scaler.fit_transform(tr_data.values[:,:-1])
    # scaled_data.shape
    # 학습 데이터 준비
    X, y = [], []
    look_back = 6  # 6개월 바탕으로 예측
    for i in range(len(tr_data) - look_back-5): ## 5: 4개월치를 예상
        X.append(scaled_data[i:i + look_back])
        y.append(tr_data.values[i + look_back+1:i + look_back+5,-1])
    X, y = np.array(X), np.array(y)
    # X.shape
    # y
    # 훈련/검증 분할 x
    # X_train, X_val, y_train, y_val = train_test_split(X, y, test_size=0.2, shuffle=False)
    
    # LSTM 모델 구축
    model = Sequential()
    model.add(LSTM(16, activation='relu', input_shape=(X.shape[1],X.shape[2]))) ## 뉴런수 16개 !!! 
    model.add(Dense(4)) ## 4개월치 예측을 위한 출력층
    
    # 모델 최적화도구 및 목표함수 설정
    model.compile(optimizer='adam', loss='mean_squared_error')
    
    # 모델 학습
    model.fit(X, y, epochs=500, batch_size=15)
    # , validation_data=(X_val, y_val)
    # 3개월 예측
    last_input = scaled_data[-look_back:].reshape(1, X.shape[1], X.shape[2])
    predicted = model.predict(last_input)

    # 결과 데이터프레임에 담아주기
    result_df[c]=np.squeeze(predicted).tolist()
    print(result_df[c])
    print("-"*100)
result_df