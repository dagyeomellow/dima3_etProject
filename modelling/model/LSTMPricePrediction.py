# 차후 4개월 전기요금 시계열 예측: PCA, LSTM
import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.preprocessing import MinMaxScaler,StandardScaler
from sklearn.decomposition import PCA
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense
from dateutil.relativedelta import relativedelta
import datetime as dt
## 역대 전기요금표 불러오기(2019년7월~2024년3월)
df=pd.read_csv('./db/데이터/PRICES.csv',encoding='cp949',index_col=0)
info_df=pd.read_csv('./db/데이터/KEPCO_DECISION.csv',encoding='cp949',index_col=0)


## 모델의 예측값 담아줄 데이터프레임 생성
now=dt.datetime.now()
result_df=pd.DataFrame(index= [(now + relativedelta(months=+i)).strftime('%Y%m') for i in range(4)], columns=df.columns)
result_df

## info_df 스케일링
scaler=StandardScaler()
data_scaled=scaler.fit_transform(info_df)


## 9개의 변수를 2개로 축소하기
pca = PCA(n_components=3) # 주성분을 몇개로 할지 결정
printcipalComponents = pca.fit_transform(data_scaled)
print("설명되는 분산: ",pca.explained_variance_ratio_) ## 3개의 주성분으로 전체 분산의 90.56% 설명
principalDf = pd.DataFrame(data=printcipalComponents, columns = ['principal component1', 'principal component2','principal component3'], index=info_df.index)
principalDf
# 주성분 데이터프레임에서 가격표에 있는 인덱스만 취하기
train_df= principalDf.drop([row for row in principalDf.index if row not in df.index])
for c in result_df.columns:
    # 주성분과 타겟값 결합
    tr_data=pd.concat([train_df,df[[c]]],axis=1)
    # 학습 데이터 준비
    X, y = [], []
    look_back = 6  # 6개월 바탕으로 예측
    
    for i in range(len(tr_data) - look_back-5): ## 5: 4개월치를 예상
        X.append(tr_data.values[i:i + look_back,:-1])
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
    # 4개월 예측
    last_input = tr_data.values[-look_back:,:-1].reshape(1, X.shape[1], X.shape[2])
    predicted = model.predict(last_input)

    # 결과 데이터프레임에 담아주기
    result_df[c]=np.squeeze(predicted).tolist()
result_df.to_csv("./db/데이터/PRICE_PREDICTION.csv", encoding='cp949')
result_df