import os
import pandas as pd
import numpy as np

import matplotlib.pyplot as plt

from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import KFold
from lightgbm import LGBMRegressor
from xgboost import XGBRegressor
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score, mean_squared_error, mean_absolute_error

# print(os.getcwd())
data=pd.read_csv('../db/학습데이터.csv').dropna()

# data.dropna(inplace=True)
X= data.values[:,1:]
y=data.values[:,0].reshape(-1,1)

# print(X.shape)
# print(y.shape)

scaler=StandardScaler()
X_scaled=scaler.fit_transform(X)

kf= KFold(n_splits = 5, shuffle= True)

r2_list=[]
rmse_list=[]
mae_list=[]

for train_idx, test_idx in kf.split(X_scaled):
    X_train, X_test = X_scaled[train_idx], X_scaled[test_idx]
    y_train, y_test = y[train_idx], y[test_idx]

    # model=LGBMRegressor()
    # model=XGBRegressor()
    # model.fit(X_train, y_train)
    model= RandomForestRegressor()
    model.fit(X_train,np.reshape(y_train,len(y_train)))
    y_pred=model.predict(X_test)

    # plt.scatter(y_test, y_pred)
    # plt.xlabel('y_test')
    # plt.ylabel('y_pred')
    
    # plt.plot([i for i in range(0,10000)],[j for j in range(0,10000)])
    # plt.show()
    
    r2=r2_score(y_test,y_pred)
    r2_list.append(r2)
    rmse=np.sqrt(mean_squared_error(y_test,y_pred))
    rmse_list.append(rmse)
    mae=mean_absolute_error(y_test,y_pred)
    mae_list.append(mae)

print("rfr avg r2: ", np.mean(r2_list))
print("rfr avg rmse: ", np.mean(rmse_list))
print("rfr avg mae: ", np.mean(mae_list))
print(y_pred)

print
