package com.app.spice.freebapp;

import in.freeb.sdk.interfaces.FreeBRegistrationListener;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freebsdk.FreeBSDKRegistration;

/**
 * Created by CH-E01073 on 19-08-2015.
 */
public class Application extends android.app.Application implements
        FreeBRegistrationListener {

    @Override
    public void onCreate() {
        super.onCreate();

        FreeBSDKLogger.enableLogging(true);
        FreeBSDKRegistration.initialize(this, getApplicationContext(), getResources().getString(R.string.freeb_publisherId),
                getResources().getString(R.string.freeb_appId));
    }

    @Override
    public void onRegistrationFailed(String code, String errorMessage) {
        FreeBCommonUtility.showToast(getApplicationContext(), errorMessage);

    }

    @Override
    public void onRegistrationSuccess(String code, String errorMessage) {
        FreeBCommonUtility.showToast(getApplicationContext(), errorMessage);
    }

}
