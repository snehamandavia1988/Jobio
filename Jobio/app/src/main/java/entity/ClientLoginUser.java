package entity;

import android.content.Context;

import java.util.Date;

import utility.ConstantVal;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class ClientLoginUser {
    String userName, password, token;
    int tokenId;
    boolean isSessionExpire;

    public ClientLoginUser() {
    }

    public ClientLoginUser(String userName, String password, String token, int tokenId, boolean isSessionExpire) {
        this.userName = userName;
        this.password = password;
        this.token = token;
        this.tokenId = tokenId;
        this.isSessionExpire = isSessionExpire;
    }

    public ClientLoginUser(String userName, String password,boolean isSessionExpire) {
        this.userName = userName;
        this.password = password;
        this.isSessionExpire=isSessionExpire;
    }

    public boolean isSessionExpire() {
        return isSessionExpire;
    }

    public void setIsSessionExpire(boolean isSessionExpire) {
        this.isSessionExpire = isSessionExpire;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public void saveDatatoPreference(Context c) {
        MyFunction.setStringPreference(c, ClientAdminUser.Fields.USER_NAME, this.getUserName());
        MyFunction.setStringPreference(c, ClientAdminUser.Fields.PASSWORD, this.getPassword());
        MyFunction.setStringPreference(c, ConstantVal.TOKEN, this.getToken());
        MyFunction.setIntPreference(c, ConstantVal.TOKEN_ID, this.getTokenId());
        MyFunction.setBooleanPreference(c, ConstantVal.IS_SESSION_EXISTS, true);
//        MyFunction.setStringPreference(c, ConstantVal.QRCODE_VALUE, this.getQRCode());
//        MyFunction.setBooleanPreference(c, ConstantVal.IS_QRCODE_CONFIGURE, true);
    }

    public static void clearCache(Context c) {
        BusinessAccountdbDetail.clearCache(c);
        BusinessAccountMaster.clearCache(c);
        ClientAdminUser.clearCache(c);
        ClientAdminUserAppsRel.clearCache(c);
        ClientEmployeeMaster.clearCache(c);
        //ClientLocationTrackingInterval.clearCache(c);
        MyFunction.clearPreference(c, ConstantVal.TOKEN);
        MyFunction.clearPreference(c, ConstantVal.TOKEN_ID);
        MyFunction.clearPreference(c, ConstantVal.IS_SESSION_EXISTS);
    }
}
