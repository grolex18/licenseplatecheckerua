package com.grolex.licenseplatescheckerua.ui.carinfo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grolex.licenseplatescheckerua.R;
import com.grolex.licenseplatescheckerua.data.model.CarInfo;

public class CarInfoActivity extends AppCompatActivity {

    private CarInfoViewModel carInfoViewModel;
    private TextView status;
    private TextView number;
    private TextView model;
    private TextView year;
    private TextView date;
    private TextView registration;
    private TextView capacity;
    private TextView color;
    private TextView body;
    private TextView kind;
    private TextView regAddrKoatuu;
    private TextView ownWeight;
    private TextView dep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carinfo);
        carInfoViewModel = ViewModelProviders.of(this, new CarInfoViewModelFactory())
                .get(CarInfoViewModel.class);

        final EditText plateNumberEditText = findViewById(R.id.platenumber);
        status = findViewById(R.id.status);
        number = findViewById(R.id.number);
        model = findViewById(R.id.model);
        year = findViewById(R.id.year);
        date = findViewById(R.id.date);
        registration = findViewById(R.id.registration);
        capacity = findViewById(R.id.capacity);
        color = findViewById(R.id.color);
        body = findViewById(R.id.body);
        kind = findViewById(R.id.kind);
        regAddrKoatuu = findViewById(R.id.regAddrKoatuu);
        ownWeight = findViewById(R.id.ownWeight);
        dep = findViewById(R.id.dep);
        
        final Button fetchCarInfo = findViewById(R.id.fetchCarInfo);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        carInfoViewModel.getCarFormState().observe(this, new Observer<CarFormState>() {
            @Override
            public void onChanged(@Nullable CarFormState carFormState) {
                if (carFormState == null) {
                    return;
                }
                fetchCarInfo.setEnabled(carFormState.isDataValid());
                if (carFormState.getUsernameError() != null) {
                    plateNumberEditText.setError(getString(carFormState.getUsernameError()));
                }
            }
        });

        carInfoViewModel.getFetchResult().observe(this, new Observer<CarInfoResult>() {
            @Override
            public void onChanged(@Nullable CarInfoResult carInfoResult) {
                if (carInfoResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (carInfoResult.getError() != null) {
                    updateUiWithUser(new CarInfoView(null));
                }
                if (carInfoResult.getSuccess() != null) {
                    updateUiWithUser(carInfoResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                carInfoViewModel.carInfoDataChanged(plateNumberEditText.getText().toString(),
                        null);
            }
        };
        plateNumberEditText.addTextChangedListener(afterTextChangedListener);

        fetchCarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                carInfoViewModel.fetchInfo(plateNumberEditText.getText().toString(),
                        null);
            }
        });
    }

    private void updateUiWithUser(CarInfoView model) {
        if (model.getCarInfo() != null) {
            status.setText(R.string.status_found);
            CarInfo carInfo = model.getCarInfo();
            number.setText(carInfo.getNumber());
            this.model.setText(carInfo.getModel());
            year.setText(carInfo.getYear());
            date.setText(carInfo.getDate());
            registration.setText(carInfo.getRegistration());
            capacity.setText(carInfo.getCapacity());
            color.setText(carInfo.getColor());
            body.setText(carInfo.getBody());
            kind.setText(carInfo.getKind());
            regAddrKoatuu.setText(carInfo.getRegAddrKoatuu());
            ownWeight.setText(carInfo.getOwnWeight());
            dep.setText(carInfo.getDep());
        } else {
            status.setText(R.string.status_not_found);
            number.setText("");
            this.model.setText("");
            year.setText("");
            date.setText("");
            registration.setText("");
            capacity.setText("");
            color.setText("");
            body.setText("");
            kind.setText("");
            regAddrKoatuu.setText("");
            ownWeight.setText("");
            dep.setText("");
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}