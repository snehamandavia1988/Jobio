package utility;

import android.content.Context;

import com.lab360io.jobio.fieldapp.R;

/**
 * Created by SAI on 12/5/2015.
 */
public class ConstantVal {
    public static class ServerResponse {
        public static final String SESSION_EXISTS = "1";//value receive from server as response
        public static final String NO_INTERNET = "001";
        public static final String PARSE_ERROR = "002";
        public static String SERVER_NOT_RESPONDING = "003";
        public static String REQUEST_TIMEOUT = "004";//30 seconds
        public static String SESSION_EXPIRED = "005";//value receive from server as response
        public static String INVALID_LOGIN = "006";//value receive from server as response
        public static String SERVER_ERROR = "007";
        public static String SUCCESS = "008";
        public static String INVALID_QR_CODE = "009";//value receive from server as response
        public static String CLIENT_ERROR = "010";
        public static String BLANCK_RESPONSE = "011";

        public static String getMessage(Context ctx, String strCode) {
            try {
                int intCode = Integer.parseInt(strCode);
                if (intCode == Integer.parseInt(NO_INTERNET)) {
                    return ctx.getString(R.string.strInternetNotAvaiable);
                } else if (intCode == Integer.parseInt(PARSE_ERROR)) {
                    return ctx.getString(R.string.strUnableToParseData);
                } else if (intCode == Integer.parseInt(SERVER_NOT_RESPONDING)) {
                    return ctx.getString(R.string.strServerNotResponding);
                } else if (intCode == Integer.parseInt(REQUEST_TIMEOUT)) {
                    return ctx.getString(R.string.strRequestTimeout);
                } else if (intCode == Integer.parseInt(SESSION_EXPIRED)) {
                    return ctx.getString(R.string.strSessionExpire);
                } else if (intCode == Integer.parseInt(INVALID_LOGIN)) {
                    return ctx.getString(R.string.strInvalidUserNameAndPassword);
                } else if (intCode == Integer.parseInt(SERVER_ERROR)) {
                    return ctx.getString(R.string.strServerError);
                } else if (intCode == Integer.parseInt(SUCCESS)) {
//
                } else if (intCode == Integer.parseInt(INVALID_QR_CODE)) {
                    return ctx.getString(R.string.strQRNotExist);
                } else if (intCode == Integer.parseInt(CLIENT_ERROR)) {
                    return ctx.getString(R.string.strClientError);
                } else if (intCode == Integer.parseInt(BLANCK_RESPONSE)) {
                    return ctx.getString(R.string.strDatacannotReceive);
                }
                return "";
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "";
            }
        }
    }
    //public static final String LOGIN_URL="http://10.0.2.2:80/electrasync_API/index.php/ControllerLogin/login";
    //public static final String LOGIN_URL="http://www.app.electrasync.com/mWebApi/v1/login/login";

    //public static int TODAY_INDEX_FROM_JOB_LIST;
    public static final String IS_QRCODE_CONFIGURE = "isQrConfigure";
    public static final String QRCODE_VALUE = "qrCodeValue";
    public static final String TOKEN = "token";
    public static final String TOKEN_ID = "tokenId";
    public static final String IS_SESSION_EXISTS = "is_session_exists";
    public static int EXIT_REQUEST_CODE = 100;
    public static int EXIT_RESPONSE_CODE = 101;

    public static int INDEX_QRCODE_VERIFICATION_API = 0;

    public static final String getQRCodeVerificationUrl(Context c, String QRCode) {
        return "http://" + QRCode + ".jobio.io/mWebApi/v1/verifycredentials/verifyQRCode";
    }

    public static int INDEX_LOGIN_API = 1;

    public static final String getLoginCredentialsUrl(Context c, String QRCode) {
        return "http://" + QRCode + ".jobio.io/mWebApi/v1/verifycredentials/verifyUserNamePassword";
    }

    public static int INDEX_MODULE_FLAG_API = 2;

    public static final String getModuleFlagUrl(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/getmoduleflag/flag";
    }

    public static int INDEX_USER_DATA_API = 3;

    public static final String getUserDataUrl(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/getuserdata/loaddata";
    }

    public static int INDEX_SAVE_USER_LOCATION = 4;

    public static final String getSaveUserLocationURL(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/userlocation/saveUserLocation";
    }

    public static int INDEX_LOCATION_TRACKING_INTERVAL = 5;

    public static final String getLocationTrackingIntervalURL(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/userlocation/getLocationTrackingInterval";
    }

    public static int INDEX_CHECK_TOKEN_EXITS = 6;

    public static final String getCheckTokenExits(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/verifycredentials/isTokenExits";
    }

    public static int INDEX_GET_JOB_LIST = 7;

    public static final String getJobList(Context c) {
        String subDomain = MyFunction.getStringPreference(c, ConstantVal.QRCODE_VALUE, "");
        return "http://" + subDomain + ".jobio.io/mWebApi/v1/getjoblist/list";
    }
}
