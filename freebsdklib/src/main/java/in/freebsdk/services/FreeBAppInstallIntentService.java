package in.freebsdk.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.Application;
import in.freeb.sdk.utils.FreeBAsynWebReqUtils;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freebsdk.FreeBOffersListActivity;
import in.freebsdk.broadcastreceiver.WakefullReceiverAppInstall;
import in.freebsdk.database.FreeBDataBaseAdapter;

/**
 *
 * This class will itself called when an offer will installed or uninstall  through FreeB SDK and it will contain information about the operation
 * Success or Failure.
 */
public class FreeBAppInstallIntentService extends IntentService {

	private final String packageAdded = "android.intent.action.PACKAGE_ADDED";
	private final String packageInstalled = "android.intent.action.PACKAGE_INSTALL";
	private final String packageUninstalled = "android.intent.action.PACKAGE_REMOVED";
	private String pkgName;
	FreeBDataBaseAdapter dataBaseAdapter;
	Context context;
	Intent intent;
	boolean isAppInstall;

	public FreeBAppInstallIntentService() {
		super("AppInstallIntentService");
	}

	public FreeBAppInstallIntentService(String name) {
		super(name);
	}

    /**
     * This method is invoked on the worker thread with a request to process.
     * @param intent The value passed to startService(Intent).

     */
	@Override
	protected void onHandleIntent(Intent intent) {
		this.intent = intent;
		this.context = this;
		Uri uri = intent.getData();
		// this will be the name of the package
		pkgName = uri != null ? uri.getSchemeSpecificPart() : null;
		// Log.i("ladoo", "package name  " + pkgName);
		dataBaseAdapter = new FreeBDataBaseAdapter(context);

		switch (intent.getAction().toString()) {

		case packageAdded:

			// Log.i("freeb", "app added");
			FreeBSDKLogger.d("FreeB App Install", "App Added");
			setAppStatusInstall(context, pkgName);

			break;
		case packageUninstalled:
			if (FreeBCommonUtility.isNetworkAvailable(context)) {
				// Log.i("freeb", "app uninstall");
				FreeBSDKLogger.d("FreeB App Install", "App Uninstall");
				setAppStatusUninstall(context, pkgName);
			} else {
				if (dataBaseAdapter != null) {
					if (dataBaseAdapter.getPackageName().contains(pkgName)
							&& !dataBaseAdapter.getAppStatus(pkgName).equals(
									FreeBConstants.APP_STATUS_INTEREST)) {
						dataBaseAdapter.updateAppStatus(pkgName,
								FreeBConstants.APP_STATUS_UNINSTALL);
						dataBaseAdapter.updateSentStatus(pkgName, "false");
						dataBaseAdapter.close();
						FreeBSDKLogger.d("FreeB App Install", FreeBConstants.APP_STATUS_UNINSTALL);
					}
				}
			}
			break;
		default:
			break;
		}
		WakefullReceiverAppInstall.completeWakefulIntent(intent);

	}

    /**
     *
     * When an app will Uninstall,and {@code t} will send information to the server about the App uninstallation
     * @param context Context to get the resources
     * @param pkgName Package Name  App on which action has to be performed
     */
	private void setAppStatusUninstall(final Context context,
			final String pkgName) {
		try {

			dataBaseAdapter = new FreeBDataBaseAdapter(context);
			if (dataBaseAdapter.getPackageName().contains(pkgName)
					&& !dataBaseAdapter.getAppStatus(pkgName).equals(
							FreeBConstants.APP_STATUS_INTEREST)) {
				try {
					if (FreeBCommonUtility.isNetworkAvailable(context)) {
						isAppInstall = false;
						HttpClient client = new DefaultHttpClient();
						HttpPost post = new HttpPost(
								FreeBConstants.CORE_REPORT_URL + ""
										+ FreeBConstants.APP_REPORTING);
						List<NameValuePair> nameValuePairs = FreeBAsynWebReqUtils
								.getRequestParamsAppReporting(context,
										dataBaseAdapter, pkgName,
										FreeBConstants.APP_STATUS_UNINSTALL);
						FreeBSDKLogger.d("App Uninstall Request", nameValuePairs.toString());
						post.setEntity(new UrlEncodedFormEntity(
								(nameValuePairs)));
						HttpResponse response = client.execute(post);
						String responseText = EntityUtils.toString(response
								.getEntity());
						
						ProtocolVersion protocolVersion = response.getProtocolVersion();
						FreeBSDKLogger.d("App Reporting Proctocol Version", ""+protocolVersion.toString());
						FreeBSDKLogger.d("App Reporting StatusLine", ""+response.getStatusLine().toString());
						
						org.apache.http.Header[] headerarray = response.getAllHeaders();
						 for (org.apache.http.Header header : headerarray) {
						        
						        FreeBSDKLogger.d("App Reporting Header",header.getName()+","+header.getValue());
						    }
						 
						 
						FreeBSDKLogger.d("App Uninstall Response", responseText);
						// Log.d("Test", responseText);
						processFinish(
								responseText,
								FreeBConstants.RESULT_CODE_APP_UNINSTALL_REPORTING);
					}
				} catch (Exception e) {
					// Crashlytics.logException(e);
					FreeBSDKLogger.e(getClass().getName(),
							e.getMessage() != null ? e.getMessage()
									: FreeBConstants.WRONG);
				}

			}

		} catch (Exception e) {
			// Crashlytics.logException(e);
			FreeBSDKLogger.e(getClass().getName(),
					e.getMessage() != null ? e.getMessage()
							: FreeBConstants.WRONG);
		}
	}

    /**
     * When an app will be install ,and {@code t} will send information to the server about the App installation
     * @param context Context to get the resources
     * @param pkgName Package Name  App on which action has to be performed
     */
	private void setAppStatusInstall(Context context, final String pkgName) {
		// Log.i("freeb", "setAppStatusInstall");
		try {
			dataBaseAdapter = new FreeBDataBaseAdapter(context);
			if (dataBaseAdapter.getPackageName().contains(pkgName)
					&& dataBaseAdapter.getAppStatus(pkgName).equals(
							FreeBConstants.APP_STATUS_INTEREST)) {
				try {
					if (FreeBCommonUtility.isNetworkAvailable(context)) {

						isAppInstall = true;
						HttpClient client = new DefaultHttpClient();
						HttpPost post = new HttpPost(
								FreeBConstants.CORE_REPORT_URL + ""
										+ FreeBConstants.APP_REPORTING);
						List<NameValuePair> nameValuePairs = FreeBAsynWebReqUtils
								.getRequestParamsAppReporting(context,
										dataBaseAdapter, pkgName,
										FreeBConstants.APP_STATUS_INSTALL);
						FreeBSDKLogger.d("App Install Request", nameValuePairs.toString());
						post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						HttpResponse response = client.execute(post);
						String responseText = EntityUtils.toString(response
								.getEntity());
						
						ProtocolVersion protocolVersion = response.getProtocolVersion();
						FreeBSDKLogger.d("App Reporting Proctocol Version", ""+protocolVersion.toString());
						FreeBSDKLogger.d("App Reporting StatusLine", ""+response.getStatusLine().toString());
						
						org.apache.http.Header[] headerarray = response.getAllHeaders();
						 for (org.apache.http.Header header : headerarray) {
						        
						        FreeBSDKLogger.d("App Reporting Header",header.getName()+","+header.getValue());
						    }
						 
						FreeBSDKLogger.d("App Install Response", responseText);
						// Log.d("Test", responseText);
						processFinish(responseText,
								FreeBConstants.RESULT_CODE_APP_REPORTING);
					}
				} catch (Exception e) {
					
					// Crashlytics.logException(e);
					if (Application.freeBOffersListener != null) {
						Application.freeBOffersListener.onOffersInstallFailure(
								FreeBConstants.offersFailedCode,
								FreeBConstants.WRONG);
						if (context != null) {
							if (context.getResources()
									.getString(R.string.showErrorMessage)
									.equals("true")) {
								FreeBCommonUtility.showToast(context,
										FreeBConstants.WRONG);
							}
						}
						
						FreeBSDKLogger.e("App Install setAppStatusUninstall", e
								.getMessage() != null ? e.getMessage()
								: FreeBConstants.WRONG);
					}
				}

			}

		} catch (Exception e) {
			if (Application.freeBOffersListener != null) {
				Application.freeBOffersListener.onOffersInstallFailure(
						FreeBConstants.offersFailedCode, FreeBConstants.WRONG);
				if (context != null) {
					if (context.getResources()
							.getString(R.string.showErrorMessage)
							.equals("true")) {
						FreeBCommonUtility.showToast(context,
								FreeBConstants.WRONG);
					}
				}
				FreeBSDKLogger.e("App Install setAppStatusUninstall", e
						.getMessage() != null ? e.getMessage()
						: FreeBConstants.WRONG);
			}
		}
	}

    /**
     * Tells you when a install completes successfully, and also the user who executed the install package.
     *  when a software package is removed successfully, again logging the user behind the operation.
     * App Install/Uninstall Process Finish and {@code t} will contain information about the App installation or uninstallation
     *
     * @param output Repsonse sent by the server
     * @param code  Response code
     */
	public void processFinish(String output, String code) {

		if (isAppInstall) {
                 // Installation operation completed successfully.
			try {
				JSONObject jObj = new JSONObject(output);

				if (jObj.optString("status").equals("ok"))
					if (Application.freeBOffersListener != null) {
						Application.freeBOffersListener.onOffersInstallSuccess(
								code, output);
						FreeBSDKLogger.d("App Install SUCCESS",code);
					} else if (jObj.optString("status").equals("fail"))
						if (Application.freeBOffersListener != null) {
							Application.freeBOffersListener
									.onOffersInstallFailure(code, output);
							FreeBSDKLogger.d("App Install FAILED",code +"  "+output);
						}
				FreeBOffersListActivity.instance.finish();
			} catch (JSONException e) {
				// Crashlytics.logException(e);
				if (Application.freeBOffersListener != null) {
					Application.freeBOffersListener.onOffersInstallFailure(
							FreeBConstants.offersFailedCode,
							FreeBConstants.WRONG);
					if (context != null) {
						if (context.getResources()
								.getString(R.string.showErrorMessage)
								.equals("true")) {
							FreeBCommonUtility.showToast(context,
									FreeBConstants.WRONG);
						}
					}
				}
				FreeBSDKLogger.e("App Install Process Finish", e
						.getMessage() != null ? e.getMessage()
						: FreeBConstants.WRONG);
			}

		}
		String jsonFromBundle = output;
		if (!jsonFromBundle.contains(FreeBCommonUtility.NETWORK_UNAVAILABLE)) {
            //  Removal completed successfully
			if (code.equals(FreeBConstants.RESULT_CODE_APP_REPORTING)) {
				if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
						&& !jsonFromBundle.contains("fail")) {
					
					FreeBSDKLogger.d("App Install Process Finish","App has installed onfinish");
					if (dataBaseAdapter != null) {
						dataBaseAdapter.updateAppStatus(pkgName,
								FreeBConstants.APP_STATUS_INSTALL);
						dataBaseAdapter.close();
					} else {
						dataBaseAdapter.close();
					}
					context.startService(new Intent(context,
							FreeBAppOpenService.class));
				}
			} else if (code
					.equals(FreeBConstants.RESULT_CODE_APP_UNINSTALL_REPORTING)) {

				if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
						&& !jsonFromBundle.contains("fail")) {
					// Log.i("ladoo","app has installed onfinish");
					FreeBSDKLogger.d("App Uninstall Process Finish","App has uninstalled onfinish");
					if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
							&& !jsonFromBundle.contains("fail")) {
						// Log.i("ladoo","app has installed onfinish");
						FreeBSDKLogger.d("App Uninstall Process Finish","App has uninstalled onfinish");
						if (dataBaseAdapter != null) {
							dataBaseAdapter.updateAppStatus(pkgName,
									FreeBConstants.APP_STATUS_UNINSTALL);
							dataBaseAdapter.updateSentStatus(pkgName, "true");
							dataBaseAdapter.close();
						}
					} else {
						dataBaseAdapter.close();
					}
				}
			}
			if (code.equals(FreeBConstants.RESULT_CODE_INSTALLED_APPS)) {
				if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
						&& !jsonFromBundle.contains("fail")) {

				}
			}
		}

		try {
			dataBaseAdapter.close();
		} catch (Exception e) {
			// Crashlytics.logException(e);
			FreeBSDKLogger.e(getClass().getName(),
					e.getMessage() != null ? e.getMessage()
							: FreeBConstants.WRONG);
		}
	}

}
