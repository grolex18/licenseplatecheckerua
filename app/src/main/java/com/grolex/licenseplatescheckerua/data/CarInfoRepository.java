package com.grolex.licenseplatescheckerua.data;

import com.grolex.licenseplatescheckerua.data.model.CarInfo;

public class CarInfoRepository {

    private static volatile CarInfoRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private CarInfo user = null;

    // private constructor : singleton access
    private CarInfoRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CarInfoRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new CarInfoRepository(dataSource);
        }
        return instance;
    }

    private void setCarInfoResult(CarInfo user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<CarInfo> fetch(String username, String password) {
        Result<CarInfo> result = dataSource.fetchInfo(username, password);
        if (result instanceof Result.Success) {
            setCarInfoResult(((Result.Success<CarInfo>) result).getData());
        }
        return result;
    }
}