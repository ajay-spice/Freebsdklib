package in.freebsdk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.NameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.freeb.sdk.R;
import in.freeb.sdk.interfaces.FreeBOffersListener;
import in.freeb.sdk.interfaces.FreeBRegistrationListener;
import in.freeb.sdk.model.FreeBOfferData;
import in.freeb.sdk.utils.Application;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBLogger;
import in.freebsdk.api.FreeBAPI;
import in.freebsdk.api.FreeBRest;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;

/**
 * The {@code FreeBOffers} class contains static functions that handle network operations to receive offers data from server
 * and show to the user.
 */
public class FreeBOffers implements
        FreeBRegistrationListener, FreeBOffersListener {

    private Context context;
    private Intent intent;
    private static double latitude, longitude;
    private static String affiliateId="", affiliateAppId="", uniqueId="";
    private String tracking_id="";
    private String offerTitle = "", offerTextColor = "", offerLayoutColor = "", itemId = "";

    protected MultipartTypedOutput multipartTypedOutput;
    protected static int isShown = 0;
    protected static boolean retryoffers;
    protected ProgressDialog pBar;
    protected List<FreeBOfferData.FetchOffer> offersList;
    protected List<NameValuePair> nameValuePairs;

    /***
     * Called to initialize class and set required parameters.This will perform
     * the functionality regarding offers to show in the application that are
     * available through library.
     *
     * @param context Context to get the resources

     */
    public FreeBOffers(Context context) {

        this.context = context;

        pBar = new ProgressDialog(context);
        pBar.setMessage("Please wait...");
        pBar.setCanceledOnTouchOutside(false);

        pBar.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (Application.freeBOffersListener != null)
                    Application.freeBOffersListener
                            .onDialogDismiss(FreeBConstants.diaglogDismiss);

            }
        });

        if (retryoffers) {
            setOnFreeBOffersListener(FreeBOffers.this);
        }
    }

    /***
     * This method is required if you intend to use your own title{@code offerTitle} ,
     *  title color{@code offerTextColor} and title layout background color{@code offerLayoutColor}
     *  for offers list display.
     *
     *  It is defined by {@code FreeBOffers.setTitle("","", "")} or
     *
     * This must be called before your
     * application can call to the setOnFreeBOffresListener.
     *
     * @param offerTitle-set title of the screen according the requirement.
     * @param offerTextColor-set text color of the title given.
     * @param offerLayoutColor-set background color of title layout.
     */
    public void setTitle(String offerTitle, String offerTextColor,
                         String offerLayoutColor) {

        this.offerTitle = offerTitle;
        this.offerTextColor = offerTextColor;
        this.offerLayoutColor = offerLayoutColor;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(FreeBConstants.TITLE, offerTitle);
        editor.putString(FreeBConstants.TEXTCOLOR, offerTextColor);
        editor.putString(FreeBConstants.LAYOUTCOLOR, offerLayoutColor);
        editor.commit();

    }

    /***
     * You must define {@code FreeBOffers.setItemId} for a specific module which you want to be upgrade by consuming
     * offers in your activity:
     *
     * @param itemId set id of particular item that need to be unlock
     */
    public void setItemId(String itemId) {

        this.itemId = itemId;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(FreeBConstants.ITEMID, itemId);
        editor.commit();

    }

    /**
     * This must be called to fetch the offers and show offers in your application.
     * You must invoke on Action Listener {@code FreeBOffers.setOnFreeBOffersListener}
     *
     *  btn.setOnClickListener(new View.OnClickListener() {
     *  Override
     * public void onClick(View v) {
     * try {
     *   FreeBOffers offers=new FreeBOffers(this);
     *   offers.setOnFreeBOffersListener(this);
     *   freeBOffers.setTitle("Pick Any Offer to unlock your premium features","#FFFFFF", "#FF6D00");
     *
     *  } catch (Exception e) {
     *  }
     * }
     * });
     *
     * @param freeBOffersListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     *
     */


    @SuppressLint("NewApi")
    public void setOnFreeBOffersListener(FreeBOffersListener freeBOffersListener) {

        Application.freeBOffersListener = freeBOffersListener;
        pBar.show();

        if (FreeBCommonUtility.isNetworkAvailable(context)) {  // check network connection is available

             // AsyncTask class to fetch the offers data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                new RequestTask()
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                new RequestTask().execute();


        }

        else {
            // In case of No Internet Connection, it will send the information to client app about the operation failure
            if (pBar.isShowing())
                pBar.dismiss();
            try {
                if (context.getResources().getString(R.string.showErrorMessage)
                        .equals("true")) {
                    FreeBCommonUtility
                            .showToast(
                                    context,
                                    context.getString(R.string.please_check_connection));
                }
                if (Application.freeBOffersListener != null) {
                    Application.freeBOffersListener
                            .onOffersFailed(
                                    FreeBConstants.offersFailedCode,
                                    context.getString(R.string.please_check_connection));
                }
            } catch (Exception e) {

                FreeBLogger.e(FreeBCommonUtility.LOG_TAG,
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);

            }
        }
    }



    /**
     * AsyncTask class to perform background operation.
     */
    class RequestTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... uri) {
            try {

                loadOffers();
            } catch (Exception e) {

                // Crashlytics.logException(e);
                if (Application.freeBOffersListener != null) {
                    Application.freeBOffersListener.onOffersFailed(
                            FreeBConstants.offersFailedCode,
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void void1) {
            super.onPostExecute(void1);
        }
    }

    /**
     * This function will execute web service to fetch server data and load into
     * the application.
     */
    public void loadOffers() {

        try {

            String locationUser = "";

            try {
                Location location = FreeBCommonUtility
                        .getReturnLocation(context);
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                try {
                    Geocoder geocoder = new Geocoder(context,
                            Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        locationUser = addresses.toString();
                    }
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                    FreeBLogger.e(FreeBCommonUtility.LOG_TAG, e.getMessage());
                }

            } catch (Exception e) {
                latitude = 0.0;
                longitude = 0.0;
                FreeBLogger.e(FreeBCommonUtility.LOG_TAG, e.getMessage());
                // Crashlytics.logException(e);
            }
            OkHttpClient okHttpClient = null;
            try {

                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(context);
                affiliateId = prefs.getString(FreeBConstants.AFFILIATE_ID, "");
                affiliateAppId = prefs.getString(
                        FreeBConstants.AFFILIATE_APP_ID, "");
                uniqueId = prefs.getString(FreeBConstants.UNIQUE_ID, "");
                String density = FreeBCommonUtility.getDensity(context);
                String isAppStaging = FreeBCommonUtility.getAppStatus(context);
                multipartTypedOutput = FreeBCommonUtility.getOffresParams(
                        context, affiliateId, affiliateAppId, locationUser,
                        density, String.valueOf(latitude),
                        String.valueOf(longitude), isAppStaging, uniqueId);

                File httpCacheDirectory = new File(context.getCacheDir(),
                        "responses");

                Cache cache = null;
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

                okHttpClient = new OkHttpClient();
                if (cache != null) {
                    okHttpClient.setCache(cache);
                }
            } catch (Exception e) {

            }

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(FreeBRest.ROOT)
                    .setClient(new OkClient(okHttpClient))
                    .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            if (FreeBCommonUtility.isNetworkAvailable(context)) {
                                int maxAge = 120; // read from cache for 2 minutes

                                request.addHeader("Cache-Control",
                                        "public, max-age=" + maxAge);
                            } else {
                                int maxStale = 60 * 60 * 24 * 28; // tolerate  4-weeks stale

                                request.addHeader("Cache-Control",
                                        "public, only-if-cached, max-stale="
                                                + maxStale);
                            }
                        }
                    }).build();

            FreeBAPI methods = restAdapter.create(FreeBAPI.class);

            methods.downloadOffers(multipartTypedOutput,
                    new Callback<FreeBOfferData>() {

                        @Override
                        public void failure(RetrofitError arg0) {
                            retryoffers = false;
                            if (pBar.isShowing())
                                pBar.dismiss();
                            if (context.getResources()
                                    .getString(R.string.showErrorMessage)
                                    .equals("true")) {
                                FreeBCommonUtility.showToast(context,
                                        FreeBConstants.WRONG);
                            }
                            if (Application.freeBOffersListener != null) {
                                Application.freeBOffersListener.onOffersFailed(
                                        FreeBConstants.offersFailedCode,
                                        arg0.toString());
                            }
                        }

                        @Override
                        public void success(FreeBOfferData arg0, Response arg1) {

                            retryoffers = false;
                            if (arg0.getStatus().equals("ok")) {
                                offersList = arg0.getPayload().getFetchOffers();
                                if (offersList == null) {
                                    if (pBar.isShowing())
                                        pBar.dismiss();
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility
                                                .showToast(context,
                                                        "Offers are not currently available");
                                    }
                                    Application.freeBOffersListener
                                            .onOffersFailed(
                                                    arg0.getErrorCode(),
                                                    arg0.getMessage());
                                } else if (offersList.size() == 0) {

                                    if (pBar.isShowing())
                                        pBar.dismiss();
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility
                                                .showToast(context,
                                                        "Offers are not currently available");
                                    }
                                    if (Application.freeBOffersListener != null) {
                                        Application.freeBOffersListener
                                                .onOffersFailed(
                                                        arg0.getErrorCode(),
                                                        arg0.getMessage());
                                    }
                                } else if (offersList.size() > 0) {

                                    if (Application.freeBOffersListener != null) {
                                        Application.freeBOffersListener
                                                .onOffersLoaded(
                                                        arg0.getErrorCode(),
                                                        arg0.getMessage());
                                    }
                                    try {

                                        if (pBar.isShowing())
                                            showOffers();
                                        else
                                            new RequestTask().cancel(true);
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }
                            } else if (arg0.getStatus().equals("fail")) {

                                if (pBar.isShowing())
                                    pBar.dismiss();

                                if (Application.freeBOffersListener != null) {
                                    Application.freeBOffersListener
                                            .onOffersFailed(
                                                    arg0.getErrorCode(),
                                                    arg0.getMessage());
                                }

                                SharedPreferences prefs = PreferenceManager
                                        .getDefaultSharedPreferences(context);

                                tracking_id = prefs.getString(
                                        FreeBConstants.ID, "");
                                if (tracking_id == null
                                        || tracking_id.equals("")) {
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility.showToast(context,
                                                "Connecting...");
                                    }
                                    retryoffers = true;
                                    affiliateId = prefs.getString(
                                            FreeBConstants.AFFILIATE_ID, "");
                                    affiliateAppId = prefs
                                            .getString(
                                                    FreeBConstants.AFFILIATE_APP_ID,
                                                    "");
                                    FreeBSDKRegistration.initialize(
                                            FreeBOffers.this, context, affiliateId,
                                            affiliateAppId);
                                }

                                else {
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility.showToast(context,
                                                "Please try again");
                                    }
                                }

                            }

                        }
                    });

        } catch (Exception e) {
            if (pBar.isShowing())
                pBar.dismiss();
            if (context.getResources().getString(R.string.showErrorMessage)
                    .equals("true")) {
                FreeBCommonUtility.showToast(context,
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
            }
            if (Application.freeBOffersListener != null) {
                Application.freeBOffersListener.onOffersFailed(
                        FreeBConstants.offersFailedCode,
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
            }

        }

    }

    /**
     * Called to show the offers data in the application.
     *
     * @throws Exception some times exception occurs due to bad response
     */
    public void showOffers() throws Exception {
        if (pBar.isShowing())
            pBar.dismiss();
        if (FreeBCommonUtility.isAppOnForeground(context)) {

            intent = new Intent(context, FreeBOffersListActivity.class);
            intent.putParcelableArrayListExtra(FreeBConstants.OFFERS,
                    (ArrayList<? extends Parcelable>) offersList);
            context.startActivity(intent);
            if (Application.freeBOffersListener != null) {
                Application.freeBOffersListener.onShowOffers();
            }
            isShown++;
        } else {
            isShown = 0;

        }

    }



    @Override
    public void onRegistrationFailed(String code, String errorMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRegistrationSuccess(String code, String errorMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onOffersFailed(String code, String errorMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onOffersLoaded(String freeBOffers, String code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onOffersInstallSuccess(String code, String response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onOffersInstallFailure(String code, String response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onShowOffers() {
        // TODO Auto-generated method stub

    }

    @Override
    public void noOfferInstalled(String code, String errorMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLeaveApplication(String code, String errorMessage) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDialogDismiss(String errorMessage) {
        // TODO Auto-generated method stub

    }

}
