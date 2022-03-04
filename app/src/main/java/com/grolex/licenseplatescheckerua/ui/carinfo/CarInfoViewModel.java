package com.grolex.licenseplatescheckerua.ui.carinfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.grolex.licenseplatescheckerua.data.CarInfoRepository;
import com.grolex.licenseplatescheckerua.data.Result;
import com.grolex.licenseplatescheckerua.data.model.CarInfo;
import com.grolex.licenseplatescheckerua.R;

public class CarInfoViewModel extends ViewModel {

    private MutableLiveData<CarFormState> carFormState = new MutableLiveData<>();
    private MutableLiveData<CarInfoResult> fetchResult = new MutableLiveData<>();
    private CarInfoRepository carInfoRepository;

    CarInfoViewModel(CarInfoRepository carInfoRepository) {
        this.carInfoRepository = carInfoRepository;
    }

    LiveData<CarFormState> getCarFormState() {
        return carFormState;
    }

    LiveData<CarInfoResult> getFetchResult() {
        return fetchResult;
    }

    public void fetchInfo(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<CarInfo> result = carInfoRepository.fetch(username, password);

        if (result instanceof Result.Success) {
            CarInfo data = ((Result.Success<CarInfo>) result).getData();
            fetchResult.setValue(new CarInfoResult(new CarInfoView(data)));
        } else {
            fetchResult.setValue(new CarInfoResult(R.string.fetch_failed));
        }
    }

    public void carInfoDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            carFormState.setValue(new CarFormState(R.string.invalid_username, null));
        } else {
            carFormState.setValue(new CarFormState(true));
        }
    }

    // A placeholder car plate validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }
}