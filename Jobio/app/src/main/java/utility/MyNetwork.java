package utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * Created by SAI on 12/7/2015.
 */
public class MyNetwork {
    static String[][] paramNames = {{"qrcode"}, {"user_name", "password", "location", "device_version", "os_name"},
            {"admin_user_Id", "token_id"},
            {"user_name", "password", "qrcode", "token_id"},
            {"latitude", "longitude", "gps_type", "reverse_geo_code_name", "admin_user_id", "app_type", "job_id", "token_id"},
            {"token_id"},
            {"token_id"}, {"employee_id", "token_id"}};

    public String getDataFromWebAPI(Context mContext, String strURL, String[] paramValues, int index, boolean isRequireToSync) {
        //isRequireToSync is use to check whether data need to sync from device to server

        Logger.debug("URL:" + strURL);
        Logger.debug("Index:" + index);
        Logger.debug("Length:" + paramNames[index].length);
        String result = "";
        String data = "";
        try {
            for (int i = 0; i < paramNames[index].length; i++) {
                data += "&" + URLEncoder.encode(paramNames[index][i], "UTF-8")
                        + "=" + URLEncoder.encode(paramValues[i], "UTF-8");
            }
            if (!data.equals(""))
                data = data.substring(1, data.length());
            Logger.debug("data:" + data);

            // Add one more param in method to check whether record needs to add in sync mechanism or not
            //Save transaction into the sync table and status =0 and keep the id
            //check internet if yes make request or 001 and return the function
            // finally if you get result code = 5, 6, 7, 8 then turn th status as 1 for the transaction

            if (!isNetworkAvailable(mContext)) {
                result = ConstantVal.ServerResponse.NO_INTERNET;
            } else {
                URL url = new URL(strURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setConnectTimeout(5000);
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(data);
                wr.flush();
                urlConnection.connect();
                Logger.debug("Response code:" + urlConnection.getResponseCode());
                int resoponseCode = urlConnection.getResponseCode();
                Logger.debug("Response message:" + urlConnection.getResponseMessage());
                if (resoponseCode >= 500 && resoponseCode <= 520) {
                    result = ConstantVal.ServerResponse.SERVER_ERROR;
                } else if (index == ConstantVal.INDEX_QRCODE_VERIFICATION_API && (resoponseCode >= 400 && resoponseCode <= 451)) {
                    result = ConstantVal.ServerResponse.INVALID_QR_CODE;
                } else if (resoponseCode >= 400 && resoponseCode <= 451) {
                    result = ConstantVal.ServerResponse.CLIENT_ERROR;
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer res = new StringBuffer();
                    char[] chBuff = new char[1000];
                    int len = 0;
                    while ((len = in.read(chBuff)) > 0)
                        res.append(new String(chBuff, 0, len));
                    in.close();
                    result = res.toString();
                    Logger.debug("Server Result:" + result);
                    if (result == null || result.equals("")) {
                        result = ConstantVal.ServerResponse.BLANCK_RESPONSE;
                    }
                    if (result.equals(ConstantVal.ServerResponse.INVALID_QR_CODE)) {
                        //result = ConstantVal.ServerResponse.INVALID_QR_CODE;
                    } else if (result.equals(ConstantVal.ServerResponse.INVALID_LOGIN)) {
                        //result = ConstantVal.ServerResponse.INVALID_LOGIN;
                    } else if (result.equals(ConstantVal.ServerResponse.SESSION_EXPIRED)) {
                        //result = ConstantVal.ServerResponse.SESSION_EXPIRED
                        boolean isSessionExists = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_SESSION_EXISTS, false);
                        if (isSessionExists) {
                            MyFunction.logOutUser(mContext);
                            Intent intent = new Intent();
                            intent.setAction("jobio.io.SESSION_EXPIRE");
                            mContext.sendBroadcast(intent);
                        }
                    } else if (result.equals(ConstantVal.ServerResponse.SESSION_EXISTS)) {

                    } else {
                        boolean isValid = MyFunction.isValidJSON(result);
                        if (!isValid) {
                            result = ConstantVal.ServerResponse.PARSE_ERROR;
                        } else {
                            //result=result;
                        }
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            result = ConstantVal.ServerResponse.INVALID_QR_CODE;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            result = ConstantVal.ServerResponse.REQUEST_TIMEOUT;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        Logger.debug("Server Result:" + result);
        return result;
    }

    public boolean isNetworkAvailable(Context mContext) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            //Toast.makeText(mContext, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            //Toast.makeText(mContext, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
}
