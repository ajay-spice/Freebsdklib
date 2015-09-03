/*
 * Copyright (c) 2015-present, Spice Digital.
 * All rights reserved.
 *
 */
package in.freebsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.List;

import in.freeb.sdk.R;
import in.freeb.sdk.interfaces.FreeBRegistrationListener;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBLogger;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freeb.sdk.utils.FreeBSharedPreference;
import retrofit.RestAdapter;
import retrofit.mime.MultipartTypedOutput;

/**
 * The {@code FreeBSDKRegistration} class contains static functions that handle global configuration for the FreeB
 * library.
 * This will initialize the library in your projects and also completes the registration process in background.
 */
public class FreeBSDKRegistration {

    private static Context mContext;
    private static String freeBAffiliateId="";
    private static String freeBAffiliateAppId="";
    private static String freeBUniqueId="";

    private static double latitude;
    private static double longitude;


    private static FreeBRegistrationListener freeBRegistrationList;
    protected JsonObject curators = null;
    protected RestAdapter restAdapter = null;
    protected MultipartTypedOutput multipartTypedOutput;
    static boolean executeInitService, installationSuccess;

    /**
     * Authenticates this client as belonging to your application.
     * This must be called before your
     * application can use the FreeB library. The recommended way is to put a call to
     * {@code FreeBSDKRegistration.initialize} in your {@code Application}'s {@code onCreate} method:
     *
     * <pre>
     * public class MyApplication extends Application {
     *   public void onCreate() {
     *     FreeBSDKRegistration.initialize(this, this, &quot;your client key&quot;, &quot;your application id&quot;);
     *   }
     * }
     * </pre>
     *
     * @param freeBRegistrationListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     * @param context                   The active {@link Context} for your application.
     * @param affiliateAppId            The application id provided in the FreeB dashboard.
     * @param affiliateId               The client id provided in the FreeB dashboard.
     */
    public static void initialize(FreeBRegistrationListener freeBRegistrationListener,
                                  Context context, String affiliateId, String affiliateAppId) {

        // Thread creation for Uncaught Exception
        try {

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                   //Default thread which will be catch all the Uncaught Exceptions for the whole FreeB SDK library

                }
            });
        } catch (Exception e) {
            FreeBSDKLogger.e("FreeBSDKRegistration",
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

        // initialize the listener reference in the library project
        freeBRegistrationList = freeBRegistrationListener;

        // save the ids for the future reference
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        FreeBSharedPreference.savePreferences(context, FreeBConstants.AFFILIATE_ID, affiliateId);
        FreeBSharedPreference.savePreferences(context, FreeBConstants.AFFILIATE_APP_ID, affiliateAppId);


        // initialize the ids in the library project
        freeBAffiliateId = affiliateId;
        freeBAffiliateAppId = affiliateAppId;
        installationSuccess = true;
        mContext = context;
        FreeBSDKLogger.d("Registration", "Initialization");

        if (freeBAffiliateId.equals("") || freeBAffiliateAppId.equals("")) {
            // In case of empty value for Affiliate ID (User id) or Affiliate App ID (App id)
            try {
                if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                    FreeBCommonUtility.showToast(mContext, mContext
                            .getString(R.string.empty_value));
                }
                freeBRegistrationList.onRegistrationFailed(
                        FreeBConstants.initServiceSuccess,
                        mContext.getString(R.string.empty_value));

            } catch (Exception e) {

                FreeBSDKLogger.e("FreeBSDKRegistration",
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);


            }
        }else{

        if (FreeBCommonUtility.isNetworkAvailable(mContext)) {   // check network connection is available
            if (prefs.getString(FreeBConstants.ID, null) == null
                    || prefs.getString(FreeBConstants.ID, null).equals("")
                    || !Build.VERSION.RELEASE.equals(prefs.getString(
                    FreeBConstants.VERSION, null))
                    || !FreeBCommonUtility.getDeviceId(mContext).equals(
                    (prefs.getString(FreeBConstants.DEVICE_ID, null)))) {
                //completes the registration process in background
                doRegistration();
            }

        } else {
            // In case of No Internet Connection, it will send the information to client app about the operation failure
            try {
                if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                    FreeBCommonUtility.showToast(mContext, mContext
                            .getString(R.string.please_check_connection));
                }
                freeBRegistrationList.onRegistrationFailed(
                        FreeBConstants.initServiceSuccess,
                        mContext.getString(R.string.please_check_connection));

            } catch (Exception e) {

                FreeBSDKLogger.e("FreeBSDKRegistration",
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);


            }
        }
    }

    }

    /**
     * Authenticates this client as belonging to your application.
     * This must be called before your
     * application can use the FreeB library. The recommended way is to put a call to
     * {@code FreeBSDKRegistration.initialize} in your {@code Application}'s {@code onCreate} method:
     *
     * <pre>
     * public class MyApplication extends Application {
     *   public void onCreate() {
     *     FreeBSDKRegistration.initialize(this, this, &quot;your client key&quot;, &quot;your application id&quot;,&quot;your unique id&quot;);
     *   }
     * }
     * </pre>
     *
     * @param freeBRegistrationListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     * @param context                   The active {@link Context} for your application.
     * @param affiliateAppId            The application id provided in the FreeB dashboard.
     * @param affiliateId               The client id provided in the FreeB dashboard.
     * @param uniqueId                  Unique id will be Phone Number, Email any param provided by the user.
     */

     // Overwrite function with different number of parameters
    public static void initialize(
            FreeBRegistrationListener freeBRegistrationListener,
            Context context, String affiliateId, String affiliateAppId,
            String uniqueId) {

        // Setup handler for uncaught exceptions.
        try {

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                    //Default Handler thread which will be catch all the Uncaught Exceptions for the whole FreeB SDK library

                }
            });
        } catch (Exception e) {
            FreeBSDKLogger.e("FreeBSDKRegistration",
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

        // initialize the listener reference in the library project
        freeBRegistrationList = freeBRegistrationListener;

        // save the ids for the future use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        FreeBSharedPreference.savePreferences(context,FreeBConstants.AFFILIATE_ID,affiliateId);
        FreeBSharedPreference.savePreferences(context,FreeBConstants.AFFILIATE_APP_ID,affiliateAppId);
        FreeBSharedPreference.savePreferences(context,FreeBConstants.UNIQUE_ID,uniqueId);

        // initialize the ids in the library project
        freeBAffiliateId = affiliateId;
        freeBAffiliateAppId = affiliateAppId;
        freeBUniqueId = uniqueId;
        installationSuccess = true;
        mContext = context;

        if (freeBAffiliateId.equals("") || freeBAffiliateAppId.equals("") || freeBUniqueId.equals("")) {
            // In case of empty value for Affiliate ID (User id) or Affiliate App ID (App id) or Unique ID
            try {
                if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                    FreeBCommonUtility.showToast(mContext, mContext
                            .getString(R.string.empty_value2));
                }
                freeBRegistrationList.onRegistrationFailed(
                        FreeBConstants.initServiceSuccess,
                        mContext.getString(R.string.empty_value2));

            } catch (Exception e) {

                FreeBSDKLogger.e("FreeBSDKRegistration",
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);


            }
        }else {
            if (FreeBCommonUtility.isNetworkAvailable(mContext)) {           // check network connection is available
                if (prefs.getString(FreeBConstants.ID, null) == null
                        || prefs.getString(FreeBConstants.ID, null).equals("")
                        || !Build.VERSION.RELEASE.equals(prefs.getString(
                        FreeBConstants.VERSION, null))
                        || !FreeBCommonUtility.getDeviceId(mContext).equals(
                        (prefs.getString(FreeBConstants.DEVICE_ID, null)))) {
                    //completes the registration process in background
                    doRegistration();
                }

            } else {
                // In case of No Internet Connection, it will send the information to client app about the operation failure
                try {
                    if (context.getResources().getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(mContext, mContext
                                .getString(R.string.please_check_connection));
                    }
                    freeBRegistrationList.onRegistrationFailed(
                            FreeBConstants.initServiceSuccess,
                            mContext.getString(R.string.please_check_connection));

                } catch (Exception e) {
                    FreeBLogger.e(FreeBCommonUtility.LOG_TAG,
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);

                }
            }
        }
    }





    /**
     * A {@code doRegistration} is used to run code requesting a registration for a
     * user.
     *
     * The easiest way to use a {@code callRegistration} is through an anonymous inner
     * class which performs operation using AsyncTask. Override the {@code onPostExecute} function to specify what the callback should do after the
     * request is complete. The {@code onPostExecute} function will be run in the UI thread, while the request
     * happens in a background thread. This ensures that the UI does not freeze while the request
     * happens.
     *
     * For example, this sample code requests to perform the registration process reset for a user and calls a different function
     * depending on whether the request succeeded or not.
     */
    public static synchronized void doRegistration() {

        new Thread(new Runnable() {

            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new callRegistration(freeBRegistrationList)
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    new callRegistration(freeBRegistrationList).execute();

            }

        }).start();

    }

    /**
     * Interacts with server to perform the registration process for
     * FreeBSDK.
     */
    public static class callRegistration extends AsyncTask<Void, Void, String> {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        // This is the reference to the associated listener
        FreeBRegistrationListener taskListener;

        public callRegistration(FreeBRegistrationListener taskListener) {
            // The listener reference is passed in through the constructor
            this.taskListener = taskListener;
        }

        @Override
        protected String doInBackground(Void... unused) {
            String responseJson = "";
            synchronized (this) {
                ;

                if (prefs.getString(FreeBConstants.ID, null) == null
                        || prefs.getString(FreeBConstants.ID, null).equals("")
                        || !Build.VERSION.RELEASE.equals(prefs.getString(
                        FreeBConstants.VERSION, null))
                        || !FreeBCommonUtility.getDeviceId(mContext)
                        .equals((prefs.getString(
                                FreeBConstants.DEVICE_ID, null)))) {

                    FreeBSDKLogger.d("Registration", "Server Communication");

                    try {
                        Location location = FreeBCommonUtility
                                .getReturnLocation(mContext);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    } catch (Exception e) {
                        latitude = 0.0;
                        longitude = 0.0;
                        FreeBSDKLogger.e("FreeBSDKRegistration",
                                e.getMessage() != null ? e.getMessage()
                                        : FreeBConstants.WRONG);

                    }

                    try {

                        String density = FreeBCommonUtility
                                .getDensity(mContext);

                        String isAppStaging = FreeBCommonUtility
                                .getAppStatus(mContext);

                        // Start network connection

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(
                                "https://www.spay.in/FreeBSDK/registerAction");

                        List<NameValuePair> nameValuePairs = FreeBCommonUtility
                                .getRegistrationParams(mContext,
                                        freeBAffiliateId, freeBAffiliateAppId,
                                        density, String.valueOf(latitude),
                                        String.valueOf(longitude), isAppStaging);

                        FreeBSDKLogger.d("Registration Request", nameValuePairs.toString());


                        httppost.setEntity(new UrlEncodedFormEntity(
                                nameValuePairs));


                        HttpResponse response = httpclient.execute(httppost);

                        HttpEntity entity = response.getEntity();
                        responseJson = EntityUtils.toString(entity).toString();

                        ProtocolVersion protocolVersion = response.getProtocolVersion();
                        FreeBSDKLogger.d("Registration Proctocol Version", "" + protocolVersion.toString());
                        FreeBSDKLogger.d("Registration StatusLine", "" + response.getStatusLine().toString());

                        org.apache.http.Header[] headerarray = response.getAllHeaders();
                        for (org.apache.http.Header header : headerarray) {

                            FreeBSDKLogger.d("Registration Header", header.getName() + "," + header.getValue());
                        }

                        FreeBSDKLogger.d("Registration Response", responseJson);


                        JSONObject response1 = new JSONObject(responseJson);
                        String status = response1.optString("status");

                        FreeBSDKLogger.d("STATUS TAG", status);

                        if (status.equals("ok")) {

                            Editor editor = prefs.edit();
                            editor.putString(FreeBConstants.ID,
                                    response1.optString("trackingDeviceId"));
                            editor.commit();

                        }

                    } catch (Exception e) {

                        FreeBSDKLogger.e("FreeBSDKRegistration",
                                e.getMessage() != null ? e.getMessage()
                                        : FreeBConstants.WRONG);


                    }

                } else
                    responseJson = null;

            }

            return responseJson;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            synchronized (this) {
                if (result != null && !result.equals("")) {
                    JSONObject mainObject;
                    try {
                        mainObject = new JSONObject(result.toString());
                        String status = mainObject.optString("status");

                        if (status.equals("ok")) {

                            Editor editor = prefs.edit();
                            editor.putString(FreeBConstants.DEVICE_ID,
                                    FreeBCommonUtility.getDeviceId(mContext));
                            editor.putString(FreeBConstants.VERSION,
                                    Build.VERSION.RELEASE);
                            editor.commit();

                            taskListener.onRegistrationSuccess(
                                    mainObject.optString("errorCode"),
                                    mainObject.optString("message"));
                            if(FreeBOffers.retryoffers)
                            {
                                FreeBOffers offers=new FreeBOffers(mContext);
                            }


                            FreeBSDKLogger.d("FINAL STATUS FOR REGISTRATION", "SUCCESS");


                        } else {
                            if (mContext.getResources()
                                    .getString(R.string.showErrorMessage)
                                    .equals("true")) {
                                FreeBCommonUtility.showToast(mContext,
                                        mainObject.optString("message"));
                            }
                            taskListener.onRegistrationFailed(
                                    mainObject.optString("errorCode"),
                                    mainObject.optString("message"));

                            FreeBSDKLogger.d("REGISTRATION FAILED", mainObject.optString("message"));
                            FreeBSDKLogger.d("REGISTRATION FAILED ERROR CODE", mainObject.optString("errorCode"));

                        }
                    } catch (Exception e) {

                        if (mContext.getResources()
                                .getString(R.string.showErrorMessage)
                                .equals("true")) {
                            FreeBCommonUtility.showToast(mContext, e
                                    .getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);
                        }
                        taskListener.onRegistrationFailed(
                                FreeBConstants.initServiceFailed, e
                                        .getMessage() != null ? e.getMessage()
                                        : FreeBConstants.WRONG);

                        FreeBSDKLogger.e(FreeBConstants.initServiceFailed, e
                                .getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);

                        // Crashlytics.logException(e);

                    }
                } else {
                    if (mContext.getResources()
                            .getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(mContext,
                                FreeBConstants.WRONG);
                    }
                    taskListener.onRegistrationFailed(
                            FreeBConstants.initServiceFailed,
                            FreeBConstants.WRONG);

                    FreeBSDKLogger.d(FreeBConstants.initServiceFailed, FreeBConstants.WRONG);

                }
            }
        }
    }

}
