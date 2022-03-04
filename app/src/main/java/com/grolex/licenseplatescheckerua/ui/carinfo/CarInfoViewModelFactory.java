package com.grolex.licenseplatescheckerua.ui.carinfo;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.grolex.licenseplatescheckerua.data.LoginDataSource;
import com.grolex.licenseplatescheckerua.data.CarInfoRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given CarInfoViewModel has a non-empty constructor
 */
public class CarInfoViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CarInfoViewModel.class)) {
            return (T) new CarInfoViewModel(CarInfoRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}