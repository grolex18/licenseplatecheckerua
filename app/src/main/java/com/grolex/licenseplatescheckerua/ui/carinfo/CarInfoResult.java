package com.grolex.licenseplatescheckerua.ui.carinfo;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class CarInfoResult {
    @Nullable
    private CarInfoView success;
    @Nullable
    private Integer error;

    CarInfoResult(@Nullable Integer error) {
        this.error = error;
    }

    CarInfoResult(@Nullable CarInfoView success) {
        this.success = success;
    }

    @Nullable
    CarInfoView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}