package com.grolex.licenseplatescheckerua.ui.carinfo;

import com.grolex.licenseplatescheckerua.data.model.CarInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class exposing authenticated user details to the UI.
 */
@Data
@AllArgsConstructor
class CarInfoView {
    private CarInfo carInfo;
}