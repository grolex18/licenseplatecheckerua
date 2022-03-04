package com.grolex.licenseplatescheckerua.data;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.grolex.licenseplatescheckerua.data.model.CarInfo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginDataSource {
    private class Connection extends AsyncTask {
        @Override
        protected Object doInBackground(Object... arg0) {
            String plateNumber = (String) arg0[0];
            try {
                URL reqURL = new URL("https://opendatabot.com/api/v3/public/transport?number=" + plateNumber);
                HttpURLConnection request = (HttpURLConnection) (reqURL.openConnection());
                request.setRequestMethod("GET");
                request.connect();
                InputStream in = new BufferedInputStream(request.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }

                return new Gson().fromJson(total.toString(), CarInfo.class);
            } catch (IOException e) {
                return null;
            }

        }
    }

    public Result<CarInfo> fetchInfo(String username, String password) {
        try {
            return new Result.Success<>(new Connection().execute(username).get());
        } catch (Exception e) {
            return new Result.Error(new IOException("Network error", e));
        }
    }
}