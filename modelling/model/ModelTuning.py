import pandas as pd
import pickle
import matplotlib.pyplot as plt

from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import KFold, GridSearchCV, train_test_split
from lightgbm import LGBMRegressor
from sklearn.metrics import mean_absolute_error

df=pd.read_csv('./db/학습데이터.csv').dropna()
## 현재 발전용량과 생산량은 집단의 총량이므로 10으로 나눠준다.
df['installed_capacity']=df['installed_capacity'].apply(lambda x: x/10)
df['day_total_prod']=df['day_total_prod'].apply(lambda x:x/10)
print(df.columns)

# 독립변수와 종속변수를 나눠준다
X=df[['installed_capacity', 'shortwave_radiation','avg_temperature']].values
y=df['day_total_prod'].values

# 학습용 및 검증용 데이터 분리 및 스케일링
X_train, X_val, y_train,y_val = train_test_split(X,y, random_state=10, shuffle=True, test_size=0.15)
scaler=StandardScaler()
x_train= scaler.fit_transform(X_train)
x_val = scaler.transform(X_val)
with open('./scalerProd.pickle', 'wb') as f:
    pickle.dump(scaler, f, pickle.HIGHEST_PROTOCOL)
    print("스케일러 저장")

# 그리드 서치 파라미터 지정
params={
    'n_estimators': [50,100,150,200,400]
    , 'learning_rate': [0.05,0.1,0.2]
    , 'max_depth': [-1,3,7,12]
    , 'num_leaves' : [31,-1,13, 67]
}

# 모델 구축
lgbmr=LGBMRegressor()
gscv=GridSearchCV(lgbmr, param_grid=params, scoring='neg_mean_absolute_error', cv = 3, refit=True, n_jobs=1, verbose=0)
gscv.fit(x_train,y_train)

# 결과 확인 및 예측
print('best parameters : ', gscv.best_params_)
print('best score : ', gscv.best_score_)
best_model = gscv.best_estimator_
y_pred = best_model.predict(x_val)
print(mean_absolute_error(y_val, y_pred))

# 모델 저장
with open('./predictProd.pickle', 'wb') as f:
    pickle.dump(best_model,f,pickle.HIGHEST_PROTOCOL)
    print("모델 저장완료")

