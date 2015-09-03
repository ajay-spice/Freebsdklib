package in.freeb.sdk.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings.Secure;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import in.freeb.sdk.R;
import in.freebsdk.services.FreeBServiceAppInstall;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

/**
 * Provides utility functions for working with Anonymously logged-in users.
 *
 */
public class FreeBCommonUtility {
	/******** @author of class CH-E00953/Aalok Sharma *********/

	public static final String LOG_TAG = "FREEB_SDK";
	public static final String NETWORK_UNAVAILABLE = "NETWORK_UNAVAILABLE";
	private static double latitude;
	private static double longitude;

	public static String FACEBOOK_APP_LINK = "fb://page/762757640440760";
	public static String FACEBOOK_BROWSER_LINK = "https://www.facebook.com/pages/FreeB/762757640440760";
	public static String TWITTER_APP_LINK = "twitter://user?screen_name=freeBofficial";
	public static String TWITTER_BROWSER_LINK = "https://twitter.com/freeBofficial";
	public static String GOOGLE_PLUS_LINK = "https://plus.google.com/101411241056406439810/posts";
	public static String GOOGLE_PLUS_APP_LINK = "google://user?screen_name=freeBofficial";
	public static String GOOGLE_PLUS_BROWSER_LINK = "https://plus.google.com/freeBofficial";

	private static boolean isDebug = true;

	/**
	 * To check whether string supplied is null or empty
	 * 
	 * @param string any input string
	 * @return true if string is null or empty or else false
	 */
	@SuppressLint("NewApi")
	public static boolean isStringEmtyOrNull(String string) {
		return (string == null || string.isEmpty());
	}

	/** Show any generic alert dialog
     * @param context  context to get resources
     * @param message input display message
     */
	public static void showAlertDialogAndFinishActivity(final Context context,
			String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(message);
		dialog.setCancelable(false);
		dialog.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						((Activity) context).finish();
						((Activity) context).overridePendingTransition(
								R.anim.righttoleftanimreturn,
								R.anim.lefttorightanimreturn);
					}
				}).setIcon(R.drawable.app_icon);

		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /** Show any generic alert dialog
     *
     * @param mContext context to get the resources
     * @param suffix  set the suffix
     * @return URL
     */
	public static String getUrl(Context mContext, String suffix) {
		String URL = "";
		try {
			if (suffix.equals(FreeBConstants.FEEDBACK)) {
				URL = FreeBConstants.CORE_URL
						+ suffix
						+ "deviceId="
						+ encodeString(FreeBCommonUtility.getDeviceId(mContext))
						+ "&mobileNumber="
						+ encodeString(FreeBCommonUtility.getMobileNo(mContext))
						+ "&mobileApp=true"
						+ "&handsetMake="
						+ encodeString(Build.MANUFACTURER)
						+ "&handsetModel="
						+ encodeString(Build.MODEL)
						+ "&mobileAppVersion="
						+ encodeString(FreeBCommonUtility
								.getVersionCode(mContext))
						+ "&emailId="
						+ FreeBCommonUtility.getEmailId(mContext)
						+ "&latitude="
						+ 0.0
						+ "&longitude="
						+ 0.0
						+ "&mobileOs="
						+ "Android"
						+ encodeString(Build.VERSION.RELEASE)
						+ "&udf1="
						+ encodeString(FreeBCommonUtility.getLAC(mContext))
						+ "&udf2="
						+ encodeString(FreeBCommonUtility
								.getWifiMacAddress(mContext))
						+ "&udf3="
						+ encodeString(FreeBCommonUtility.getBluetoothAddress())
						+ "&udf4=&udf5=&imei="
						+ encodeString(FreeBCommonUtility
								.getImeiNumber(mContext)) + "&mcc="
						+ encodeString(FreeBCommonUtility.getMCC(mContext))
						+ "&mnc="
						+ encodeString(FreeBCommonUtility.getMNC(mContext))
						+ "&celId="
						+ encodeString(FreeBCommonUtility.getCellID(mContext))
						+ "&accuracy=" + 0.0 + "&"
						+ FreeBConstants.PACKAGE_NAME_UDF1 + "="
						+ mContext.getPackageName();
			} else {
				URL = FreeBConstants.CORE_URL
						+ ""
						+ suffix
						+ "deviceId="
						+ encodeString(FreeBCommonUtility.getDeviceId(mContext))
						+ "&mobileNumber="
						+ encodeString(FreeBCommonUtility.getMobileNo(mContext))
						+ "&page=1"
						+ "&mobileApp=true"
						+ "&handsetMake="
						+ encodeString(Build.MANUFACTURER)
						+ "&handsetModel="
						+ encodeString(Build.MODEL)
						+ "&mobileAppVersion="
						+ encodeString(FreeBCommonUtility
								.getVersionCode(mContext))
						+ "&emailId="
						+ FreeBCommonUtility.getEmailId(mContext)
						+ "&latitude="
						+ 0.0
						+ "&longitude="
						+ 0.0
						+ "&mobileOs="
						+ "Android"
						+ encodeString(Build.VERSION.RELEASE)
						+ "&udf1="
						+ encodeString(FreeBCommonUtility.getLAC(mContext))
						+ "&udf2="
						+ encodeString(FreeBCommonUtility
								.getWifiMacAddress(mContext))
						+ "&udf3="
						+ encodeString(FreeBCommonUtility.getBluetoothAddress())
						+ "&udf4=&udf5=&imei="
						+ encodeString(FreeBCommonUtility
								.getImeiNumber(mContext)) + "&mcc="
						+ encodeString(FreeBCommonUtility.getMCC(mContext))
						+ "&mnc="
						+ encodeString(FreeBCommonUtility.getMNC(mContext))
						+ "&celId="
						+ encodeString(FreeBCommonUtility.getCellID(mContext))
						+ "&accuracy=" + 0.0 + "&fromDate=&toDate=" + "&"
						+ FreeBConstants.PACKAGE_NAME_UDF1 + "="
						+ mContext.getPackageName();
			}
		} catch (UnsupportedEncodingException e) {
			FreeBSDKLogger.e(LOG_TAG, e.getMessage());
		}
		return URL;

	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	/**
	 * Get Network class

     * @param context
     *            Context to get resources and Network information
	 * @return address or empty string
	 */

	public static String getNetworkClass(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null || !info.isConnected())
			return "-"; // not connected
		if (info.getType() == ConnectivityManager.TYPE_WIFI)
			return "WIFI";
		if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int networkType = info.getSubtype();
			switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return "2G";
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return "3G";
			case TelephonyManager.NETWORK_TYPE_LTE:
				return "4G";
			default:
				return "UNKNOWN";
			}
		}
		return "UNKNOWN";
	}



	/*********************************************************************
	 * Method to hide the virtual keyboard
	 *
     * @param context
     *            Context to hide the keyboard
     * @param editText
     *            input edittext reference
     * @param v
     *            input view where action as to performed
	 * ******************************************************************/
	public static void hideVirtualKeyboard(Context context, EditText editText,
			View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (editText != null) {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		} else {
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	/*********************************************************************
	 * Method to show the virtual keyboard
	 *
     * @param context
     *            Context to show the keyboard
     * @param editText
     *            input edittext reference where Keyboard as to be open
	 * ******************************************************************/
	public static void openVirtualKeyboard(Context context, EditText editText) {

		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
				editText, 0);
		// MLog.v("from openVirtualKeyboard", "executing");
	}

	/***
	 * Method to decode timestamp and return a custom form of decoded string
     * @param timeStampString
     *            input Time Stamp to get the date
     * @param context
     *            Context to get the Date
     *@return date
	 * **/
	public static String decodeTimeStamp(String timeStampString, Context context) {
		SimpleDateFormat currentDateFormat = new SimpleDateFormat("hh:mm aa");
		SimpleDateFormat previousDateFormat = new SimpleDateFormat(
				"MMM dd hh:mm aa");
		String date = "";
		try {
			long timeStampForDecode = Long.parseLong(timeStampString);
			// Date d = new Date();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, -1);// yesterday
			Calendar c1 = Calendar.getInstance();
			c1.setTime(new Date(timeStampForDecode));// ourdate
			Calendar c2 = Calendar.getInstance();// calendar for comparison
			if ((c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
					&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
					&& (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))) {
				date = currentDateFormat.format(new Date(timeStampForDecode));
			} else if ((c1.get(Calendar.DAY_OF_YEAR) == c
					.get(Calendar.DAY_OF_YEAR))
					&& (c1.get(Calendar.MONTH) == c.get(Calendar.MONTH))
					&& (c1.get(Calendar.YEAR) == c.get(Calendar.YEAR))) {
				/*
				 * date =
				 * context.getResources().getString(R.string.yesterday_tag) +
				 * ", " + currentDateFormat.format(new
				 * Date(timeStampForDecode));
				 */
			} else {
				date = previousDateFormat.format(new Date(timeStampForDecode));
			}

		} catch (Exception e) {
			// MLog.v("from decodetimestamp", e.toString());
		}
		return date;
	}

	/**
	 * To get display size
	 *
     * @param context
     *            Context to get resources and device specific display metrics
	 * @return Device Screen Size Info
	 */
	@SuppressLint("NewApi")
	public static Point getDeviceDisplaySize(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}


	public static boolean findBinary(String binaryName) {
		boolean found = false;
		try {
			if (!found) {
				String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
						"/data/local/xbin/", "/data/local/bin/",
						"/system/sd/xbin/", "/system/bin/failsafe/",
						"/data/local/" };
				for (String where : places) {
					if (new File(where + binaryName).exists()) {
						found = true;

						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return found;
	}

	public static boolean isRooted() {
		return findBinary("su");
	}



    /**
     * To get List of Installed App
     *
     * @param context
     *            Context to get the List of Installed App
     * @return List of Installed App
     */

	public static String getInstalledApps(Context context) {
		String packageNames = "";
		try {
			List<ApplicationInfo> list = context.getPackageManager()
					.getInstalledApplications(PackageManager.GET_META_DATA);
			String packageName;
			packageNames = "";
			for (int i = 0; i < list.size(); i++) {
				ApplicationInfo applicationInfo = list.get(i);
				packageName = applicationInfo.packageName;
				packageNames = packageNames + packageName + " \n ";
				// Log.i(TAG, "package name = " + packageName);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Another way gives same output

		/*
		 * List<PackageInfo> pi = context.getPackageManager()
		 * .getInstalledPackages(PackageManager.GET_META_DATA); for (int i = 0;
		 * i < pi.size(); i++) { PackageInfo p = pi.get(i); packageName =
		 * p.packageName; Log.i(TAG, "package name = " + packageName); }
		 */

		return packageNames;
	}

    /**
     * Getting Device Id
     *
     * @param context Context to get the resources
     * @return device id
     */

	public static String getDeviceId(Context context) {
		String deviceId = "";
		try {
			deviceId = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceId;
	}

	/**
	 * Getting mobile country code
	 * 
	 * @param mContext Context to get the instance of TelephonyManager
	 * @return MCC 3-digit Mobile Country Code
	 */
	public static String getMCC(Context mContext) {
		String mcc = "";
		try {
			// retrieve a reference to an instance of TelephonyManager
			TelephonyManager telephonyManager = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			String networkOperator = telephonyManager.getNetworkOperator();
			mcc = networkOperator.substring(0, 3);
		} catch (Exception e) {
			FreeBSDKLogger.e(LOG_TAG, e.getMessage());
		}
		return mcc;
	}

	/**
	 * Getting mobile network code
	 * 
	 * @param mContext  Context to get the instance of TelephonyManager
	 * @return MNC  2 or 3-digit Mobile Network Code
	 */
	public static String getMNC(Context mContext) {
		String mnc = "";
		try {
			// retrieve a reference to an instance of TelephonyManager
			TelephonyManager telephonyManager = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			String networkOperator = telephonyManager.getNetworkOperator();
			mnc = networkOperator.substring(3);
		} catch (Exception e) {
			FreeBSDKLogger.e(LOG_TAG, e.getMessage());
		}
		return mnc;
	}

	/**
	 * Getting CELL ID
	 * 
	 * @param mContext  Context to get the instance of TelephonyManager
	 * @return CELL ID
	 */
	public static String getCellID(Context mContext) {
		String cellID = "";
		try {
			// retrieve a reference to an instance of TelephonyManager
			TelephonyManager telephonyManager = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
					.getCellLocation();
			cellID = String.valueOf(cellLocation.getCid());
		} catch (Exception e) {
			FreeBSDKLogger.e(LOG_TAG, e.getMessage());
		}
		return cellID;
	}

	/**
	 * Getting location area code.
	 * 
	 * @param mContext  Context to get the instance of TelephonyManager
	 * @return LAC gsm location area code
	 */
	public static String getLAC(Context mContext) {
		String lac = "";
		try {
			// retrieve a reference to an instance of TelephonyManager
			TelephonyManager telephonyManager = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
					.getCellLocation();
			lac = String.valueOf(cellLocation.getLac());
		} catch (Exception e) {
			FreeBSDKLogger.e(LOG_TAG, e.getMessage());
		}
		return lac;
	}

    /**
     * Getting Sim ID
     * @param context  Context to get the instance of TelephonyManager
     * @return sim id
     */
	public static String getSimId(Context context) {
		String sim_id = "";

		String subscriberId = "";

		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			sim_id = telephonyManager.getSimSerialNumber();
			subscriberId = telephonyManager.getSubscriberId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Log.i(LOG_TAG, "Sim Id = " + sim_id);
		// Log.i(LOG_TAG, "Sim subscriberId = " + subscriberId);
		return sim_id;
	}

    /**
     * Getting Mobile Number
     * @param context  Context to get the instance of TelephonyManager
     * @return mobile no
     */
	public static String getMobileNo(Context context) {
		String mobileNo = "";
		try {
			SharedPreferences sharedPreference = PreferenceManager
					.getDefaultSharedPreferences(context);
			mobileNo = sharedPreference.getString("authenticated_phone_number",
					"");
			if (!mobileNo.equals("")) {
				return mobileNo;
			} else {
				TelephonyManager telephonyManager = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				mobileNo = telephonyManager.getLine1Number();
				if (mobileNo == null)
					mobileNo = "";
				if (mobileNo.length() > 10)
					mobileNo = mobileNo.substring(mobileNo.length() - 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mobileNo;

	}


    /**
     * Getting Registered Accounts
     * @param context  Context to get the instance of AccountManager
     * @return registered accounts
     */
	public static String getRegisteredAccounts(Context context) {
		String possibleEmail = "";
		try {

			Account[] accounts = AccountManager.get(context).getAccounts();
			for (Account account : accounts) {

				possibleEmail += account.name + "\n";

				// Log.i(LOG_TAG, " possibleEmail= " + possibleEmail);
			}
		} catch (Exception e) {
			// Log.i("Exception", "Exception:" + e);
		}
		return possibleEmail;
	}

    /**
     * Getting Call Details
     * @param context  Context to get the instance of AccountManager
     * @return call duration
     */
	public static long getCallDetails(Context context) {

		long totalDuration = 0;
		try {
			Cursor managedCursor = context.getContentResolver().query(
					CallLog.Calls.CONTENT_URI, null, null, null, null);
			// Log.i(LOG_TAG, "Call detail count" + managedCursor.getCount());
			int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
			int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
			int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
			int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
			while (managedCursor.moveToNext()) {
				String phNumber = managedCursor.getString(number);
				String callType = managedCursor.getString(type);
				String callDate = managedCursor.getString(date);
				Date callDayTime = new Date(Long.valueOf(callDate));
				String callDuration = managedCursor.getString(duration);

				totalDuration += Integer.parseInt(callDuration);
				String dir = null;
				int dircode = Integer.parseInt(callType);
				switch (dircode) {
				case CallLog.Calls.OUTGOING_TYPE:
					dir = "OUTGOING";
					break;

				case CallLog.Calls.INCOMING_TYPE:
					dir = "INCOMING";
					break;

				case CallLog.Calls.MISSED_TYPE:
					dir = "MISSED";
					break;
				}

			}
			managedCursor.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return totalDuration;

	}

    /**
     * Getting SMS Details
     * @param context  Context to get the instance of AccountManager
     * @return sms count
     */
	@SuppressLint("NewApi")
	public static int getSmsDetails(Context context) {
		String number;
		String readStatus, body;
		Uri myMessage = Uri.parse("content://sms/");
		ContentResolver cr = context.getContentResolver();

		int count = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy | hh:mm");
			Date d = new Date();
			Cursor c1 = cr.query(Telephony.Sms.CONTENT_URI, null, null, null,
					null);
			count = c1.getCount();
			while (c1.moveToNext()) {
				number = c1.getString(c1.getColumnIndex(Telephony.Sms.ADDRESS));
				body = c1.getString(c1.getColumnIndex(Telephony.Sms.BODY));
				String date = c1.getString(c1
						.getColumnIndex(Telephony.Sms.DATE));

				try {
					d = sdf.parse(date);

				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			c1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}
    /**
     * Opens Phone Contact List
     * @param context  Context to get the instance of Content Provider
     */
	public static void showContactsPhone(Activity context) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
			context.startActivityForResult(intent, PICK_CONTACT);
			context.overridePendingTransition(R.anim.lefttorightanim,
					R.anim.righttoleftanim);
		} catch (Exception e) {
			// Crashlytics.logException(e);
		}
	}

    /**
     * Set Layout for Contacts
     * @param  ed instance of edittext
     * @param context  Context to get the instance of Content Provider
     * @param resultCode  Check which request we're responding to
     * @param data  The user picked a contact, intent's data uri identifies which contact was selected.
     */
	public static void putContactInEditText(final EditText ed, Context context,
			int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
            // Make sure the request was successful
			Cursor cursor = null;
			String phoneNumber = "";
			List<String> allNumbers = new ArrayList<String>();
			int phoneIdx = 0;
			try {
				Uri contactData = data.getData();
				Cursor c = context.getContentResolver().query(contactData,
						null, null, null, null);
				if (c.moveToFirst()) {
					String name = c
							.getString(c
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					phoneNumber = c
							.getString(c
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					allNumbers.add(phoneNumber);
					// R
					// contact name.
				}

			} catch (Exception e) {
				// error actions
			} finally {
				if (cursor != null) {
					cursor.close();
				}

				final CharSequence[] items = allNumbers
						.toArray(new String[allNumbers.size()]);
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Choose a number");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						String selectedNumber = items[item].toString();
						selectedNumber = selectedNumber.replace("-", "");
						selectedNumber = selectedNumber.replace(" ", "");

						if (selectedNumber.length() > 10)
							selectedNumber = selectedNumber
									.substring(selectedNumber.length() - 10);

						ed.setText(selectedNumber);
					}
				});
				AlertDialog alert = builder.create();
				if (allNumbers.size() > 1) {
					try {
						alert.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String selectedNumber = phoneNumber.toString();
					selectedNumber = selectedNumber.replace("-", "");
					selectedNumber = selectedNumber.replace(" ", "");

					if (selectedNumber.length() > 10)
						selectedNumber = selectedNumber
								.substring(selectedNumber.length() - 10);

					ed.setText(selectedNumber);
					// hasNumberPickFromContact = true;
				}
				if (phoneNumber.length() == 0) {
					// Toast.makeText(getActivity(), "", 700).show();
					AlertDialog.Builder alert2 = new AlertDialog.Builder(
							context);
					alert2.setMessage("No Mobile number found");
					alert2.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

								}
							});
					alert2.setCancelable(true);
					try {
						alert2.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static final int PICK_CONTACT = 100;
	public static final String APP_NAME = "com.flipkart.android";





	/**
	 * Get Imei Number of Device
	 * 
	 * @param context Context to get the instance of TelephonyManager
	 * @return Imei Number
	 */

	public static String getImeiNumber(Context context) {

		String deviceIMEI = "";
		try {
			TelephonyManager tManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceIMEI = tManager.getDeviceId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceIMEI;
	}

	/**
	 * Get Version Code of Application
	 * 
	 * @param context Context to get the instance of PackageInfo
	 * @return Version Code
	 */

	public static String getVersionCode(Context context) {
		PackageInfo pInfo = null;
		int version = 0;
		try {
			pInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
		if (pInfo != null)
			version = pInfo.versionCode;
		return String.valueOf(version);
	}

	/**
	 * Get Email Id Registered with device
	 * 
	 * @param context Context to get the instance of AccountManager
	 * @return Email Id
	 */

	public static String getEmailId(Context context) {
		String possibleEmail = "";
		try {
			Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
			Account[] accounts = AccountManager.get(context).getAccountsByType(
					"com.google");
			for (Account account : accounts) {
				if (emailPattern.matcher(account.name).matches()) {
					possibleEmail = account.name;
					break;
				}
			}
		} catch (Exception e) {
			// Log.i("Exception", "Exception:" + e);
		}
		return possibleEmail;
	}

	/**
	 * Get Installed Apps Package Name
	 * 
	 * @param context Context to get the List of Installed App Package Name
	 * @return Installed Apps Package Name
	 */
	public static String getInstalledAppsPackageName(Context context) {
		String packageNames = "";
		try {
			List<ApplicationInfo> list = context.getPackageManager()
					.getInstalledApplications(PackageManager.GET_META_DATA);
			String packageName;
			packageNames = "";
			for (int i = 0; i < list.size(); i++) {
				ApplicationInfo applicationInfo = list.get(i);
				packageName = applicationInfo.packageName;

				packageNames = packageNames + packageName + ",";

			}
			packageNames = packageNames.substring(0, packageNames.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packageNames;
	}

	/**
	 * Get wifi mac address
	 * 
	 * @param mContext Context to get the instance of WifiManager
	 * @return mac address
	 */
	public static String getWifiMacAddress(Context mContext) {
		String macAddress = "";
		// try {
		// WifiManager wifiManager = (WifiManager) mContext
		// .getSystemService(Context.WIFI_SERVICE);
		// WifiInfo wInfo = wifiManager.getConnectionInfo();
		// macAddress = wInfo.getMacAddress();
		// } catch (Exception e) {
		// }
		return macAddress;
	}

	/**
	 * Get bluetooth mac address
	 * @return mac address
	 */
	public static String getBluetoothAddress() {
		String bluetoothAddress = "";
		// try {
		// BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
		// .getDefaultAdapter();
		//
		// // if device does not support Bluetooth
		// if (mBluetoothAdapter == null) {
		// return "";
		// }
		// bluetoothAddress = mBluetoothAdapter.getAddress();
		// } catch (Exception e) {
		// }
		return bluetoothAddress;
	}

	/**
	 * Get Version codes of all Installed Applications
	 * 
	 * @param context Context to get the List of Installed App Version codes

	 * @return Installed App Version Codes
	 */
	public static String getInstalledAppsVersionCodes(Context context) {
		String versionCodes = "";
		int versionCode;
		List<PackageInfo> packs = context.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			versionCode = p.versionCode;
			versionCodes = versionCodes + String.valueOf(versionCode) + ",";
		}
		versionCodes = versionCodes.substring(0, versionCodes.length() - 1);
		return versionCodes;
	}



	/**
	 * Get Location from device
	 * 
	 * @param context Context to get the User Location via get the Instance of LocationManager
     * @return user location object
	 */

	public static Location getReturnLocation(Context context) {
		Location location = null;

		try {

			FreeBUserLocationUtility userLocationUtility = new FreeBUserLocationUtility(
					context); // Location location =
			// userLocationUtility.getLocation();
			if (userLocationUtility.canGetLocation()) {
				// latitude = userLocationUtility.getLatitude();
				// longitude = userLocationUtility.getLongitude();
				location = userLocationUtility.getLocation();
			} else {
				location = null;
			}
			return location;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}




	/***
	 * Android L (lollipop, API 21) introduced a new problem when trying to
	 * invoke implicit intent,
	 * "java.lang.IllegalArgumentException: Service Intent must be explicit"
	 * 
	 * If you are using an implicit intent, and know only 1 target would answer
	 * this intent, This method will help you turn the implicit intent into the
	 * explicit form.
	 * 
	 * Inspired from SO answer: http://stackoverflow.com/a/26318757/1446466
	 * 
	 * @param context Context to get resources
	 * @param implicitIntent
	 *            - The original implicit intent
	 * @return Explicit Intent created from the implicit original intent
	 */
	public static Intent createExplicitFromImplicitIntent(Context context,
			Intent implicitIntent) {
		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent,
				0);

		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}

		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);

		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);

		// Set the component to be explicit
		explicitIntent.setComponent(component);

		return explicitIntent;
	}


	/**
	 * Schedule service
	 * 
	 * @param context Context to get resources
	 */
	public static void scheduleAppInstallIntentService(Context context) {
		Intent intent = new Intent(context, FreeBServiceAppInstall.class);
		// SpiceSafarService s = new SpiceSafarService(context);
		PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				24 * 60 * 60 * 1000, pi);
	}



    /**
     *  Like on facebook
     *  @param mContext Context to get the resources
     */
	public static void likeOnFacebook(Context mContext) {
		try {
			mContext.getPackageManager().getPackageInfo("com.facebook.katana",
					0);
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(FACEBOOK_APP_LINK)));
		} catch (Exception e) {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(FACEBOOK_BROWSER_LINK)));
		}
	}

    /**
     *  Like on Twitter
     *  @param mContext Context to get the resources
     */
	public static void likeOnTwitter(Context mContext) {
		try {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(TWITTER_APP_LINK)));
		} catch (Exception e) {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(TWITTER_BROWSER_LINK)));
		}
	}

    /**
     *  Like on GooglePlus
     *  @param mContext Context to get the resources
     */
	public static void likeOnGooglePlus(Context mContext) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setClassName("com.google.android.apps.plus",
					"com.google.android.apps.plus.phone.UrlGatewayActivity");
			intent.putExtra("customAppUri", FreeBCommonUtility.GOOGLE_PLUS_LINK);
			mContext.startActivity(intent);
		} catch (Exception e) {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(FreeBCommonUtility.GOOGLE_PLUS_LINK)));
		}
	}


	/**
	 * Encode string
	 * 
	 * @param string input string for encoding
	 * @return encode string
     * @throws java.io.UnsupportedEncodingException when function throws Unsupported Encoding Exception
	 */
	public static String encodeString(String string)
			throws UnsupportedEncodingException {
		// if input string is null then don't encode to avoid NPE.
		if (string == null || string.equals("")) {
			return "";
		}
		return URLEncoder.encode(string, "UTF-8");
	}

   /**
    * Set the Status Bar Color
    *  @param context Context to get the resources
    */
	@SuppressLint("NewApi")
	public static void setStatusBarColor(Context context) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = ((Activity) context).getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.setStatusBarColor(context.getResources().getColor(
						R.color.statusbar_color));
			}
		} catch (NotFoundException e) {
		} catch (Exception e) {
		}
	}

	/**
	 * Check whether particular package is installed or not
	 * 
	 * @param packagename input Package Name
	 * @param context Context to get the instance of PackageInfo
	 * @return true if installed otherwise false
	 */
	public static boolean isPackageInstalled(String packagename, Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * Display toast message
	 * 
	 * @param mContext Context to get the resources
	 * @param message Toast Message which will be displayed
     *@param  numberOfTimes count for how many Number of times it has to be shown
	 */
	public static void displayToast(Context mContext, String message,
			int numberOfTimes) {
		int count = 0;
		while (count < numberOfTimes) {
			++count;
			Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
		}
	}

    /**
     *  Get App Status
     *  @param context Context to get the resources
     *  @return App Status tells us about the application mode
     */
	public static String getAppStatus(Context context) {
		boolean isDebuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
		if (isDebuggable)
			return "STAGING";
		else
			return "PRODUCTION";
	}


    /**
     * Display Alert Dialog
     *
     * @param context Context to get the resources
     * @param message  Message which will be displayed

     */
	// Show dialog on unavailable network connection
	public static void showAlertDialog(Context context, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(message);
		dialog.setCancelable(false);
		dialog.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setIcon(R.drawable.app_icon);

		try {
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    /**
     * Check network connection status
     *  @param context Context to get the resources
     *   @return if available returns true else false
     */
	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		Log.e("", "Network Not Available...");
		return false;
	}

    /**
     * Check IsAppOnForeground
     *  @param context Context to get the resources
     *   @return if success returns true else false
     */
	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}
		final String packageName = context.getPackageName();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
					&& appProcess.processName.equals(packageName)) {
				return true;
			}
		}
		return false;
	}
    /**
     *  Show Toast
     *  @param context Context to get the resources
     *  @param message Message which will be displayed
     */
	public static void showToast(Context context, String message) {

		if (isDebug)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();

	}

    public static MultipartTypedOutput getOffresParams(Context context,
                                                       String affiliateId, String affiliateAppId, String locationUser,
                                                       String density, String deviceLattitude, String deviceLongitude,
                                                       String appStatus,String uniqueId) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart(FreeBConstants.VERSION, new TypedString(
                Build.VERSION.RELEASE));
        multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_ID,
                new TypedString(affiliateId));

        multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_APP_ID,
                new TypedString(affiliateAppId));

        multipartTypedOutput.addPart(FreeBConstants.TRACKING_ID,
                new TypedString(prefs.getString(FreeBConstants.ID, "")));

        multipartTypedOutput.addPart(FreeBConstants.CONN_TYPE, new TypedString(
                FreeBCommonUtility.getNetworkClass(context)));
        multipartTypedOutput.addPart(FreeBConstants.DENSITY, new TypedString(
                density));
        multipartTypedOutput.addPart(FreeBConstants.STAGING, new TypedString(
                appStatus));

        multipartTypedOutput.addPart(FreeBConstants.MOBILE_SDK_VERSION,
                new TypedString(FreeBConstants.SDK_VERSION));

        multipartTypedOutput.addPart(FreeBConstants.DEVICE_ID, new TypedString(
                FreeBCommonUtility.getDeviceId(context)));
        multipartTypedOutput.addPart("mobileApp", new TypedString("1"));
        multipartTypedOutput.addPart("mobileOs", new TypedString("Android"));
        multipartTypedOutput.addPart("mobileAppVersion", new TypedString(
                FreeBCommonUtility.getVersionCode(context)));

        multipartTypedOutput.addPart("location", new TypedString(locationUser));

        multipartTypedOutput.addPart(FreeBConstants.UDF1, new TypedString(""));
        multipartTypedOutput.addPart(FreeBConstants.UDF2, new TypedString(uniqueId));
        multipartTypedOutput.addPart(FreeBConstants.UDF3, new TypedString(""));

        multipartTypedOutput.addPart(FreeBConstants.UDF4, new TypedString(""));
        multipartTypedOutput.addPart(FreeBConstants.UDF5, new TypedString(""));

        multipartTypedOutput.addPart(FreeBConstants.MCC, new TypedString(
                FreeBCommonUtility.getMCC(context)));

        multipartTypedOutput.addPart(FreeBConstants.MNC, new TypedString(
                FreeBCommonUtility.getMNC(context)));
        multipartTypedOutput.addPart(FreeBConstants.CELLID, new TypedString(
                FreeBCommonUtility.getCellID(context)));

        multipartTypedOutput.addPart(FreeBConstants.IMEI, new TypedString(
                FreeBCommonUtility.getImeiNumber(context)));
        multipartTypedOutput.addPart(FreeBConstants.LATITUDE, new TypedString(
                String.valueOf(deviceLattitude)));
        multipartTypedOutput.addPart(FreeBConstants.LONGITUDE, new TypedString(
                String.valueOf(deviceLongitude)));
        multipartTypedOutput.addPart(FreeBConstants.HANDSET_MAKE,
                new TypedString(Build.MANUFACTURER));
        multipartTypedOutput.addPart(FreeBConstants.HANDSET_MODEL,
                new TypedString(Build.MODEL));
        multipartTypedOutput.addPart("mobileNumber", new TypedString(
                FreeBCommonUtility.getMobileNo(context)));
        multipartTypedOutput.addPart(FreeBConstants.EMAIL_ID, new TypedString(
                FreeBCommonUtility.getEmailId(context)));

        return multipartTypedOutput;

    }


    public static void Requestparameters(Context context,
                                         String affiliateId, String affiliateAppId, String locationUser,
                                         String density, String deviceLattitude, String deviceLongitude,
                                         String appStatus) {
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            JSONObject multipartTypedOutput = new JSONObject();

            multipartTypedOutput.put(FreeBConstants.VERSION, new TypedString(
                    Build.VERSION.RELEASE));
            multipartTypedOutput.put(FreeBConstants.AFFILIATE_ID,
                    new TypedString(affiliateId));

            multipartTypedOutput.put(FreeBConstants.AFFILIATE_APP_ID,
                    new TypedString(affiliateAppId));

            multipartTypedOutput.put(FreeBConstants.TRACKING_ID,
                    new TypedString(prefs.getString(FreeBConstants.ID, "")));

            multipartTypedOutput.put(FreeBConstants.CONN_TYPE, new TypedString(
                    FreeBCommonUtility.getNetworkClass(context)));
            multipartTypedOutput.put(FreeBConstants.DENSITY, new TypedString(
                    density));
            multipartTypedOutput.put(FreeBConstants.STAGING, new TypedString(
                    appStatus));

            multipartTypedOutput.put(FreeBConstants.MOBILE_SDK_VERSION,
                    new TypedString(FreeBConstants.SDK_VERSION));

            multipartTypedOutput.put(FreeBConstants.DEVICE_ID, new TypedString(
                    FreeBCommonUtility.getDeviceId(context)));
            multipartTypedOutput.put("mobileApp", new TypedString("1"));
            multipartTypedOutput.put("mobileOs", new TypedString("Android"));
            multipartTypedOutput.put("mobileAppVersion", new TypedString(
                    FreeBCommonUtility.getVersionCode(context)));

            multipartTypedOutput.put("location", new TypedString(locationUser));

            multipartTypedOutput.put(FreeBConstants.UDF1, new TypedString(""));
            multipartTypedOutput.put(FreeBConstants.UDF2, new TypedString(""));
            multipartTypedOutput.put(FreeBConstants.UDF3, new TypedString(""));

            multipartTypedOutput.put(FreeBConstants.UDF4, new TypedString(""));
            multipartTypedOutput.put(FreeBConstants.UDF5, new TypedString(""));

            multipartTypedOutput.put(FreeBConstants.MCC, new TypedString(
                    FreeBCommonUtility.getMCC(context)));

            multipartTypedOutput.put(FreeBConstants.MNC, new TypedString(
                    FreeBCommonUtility.getMNC(context)));
            multipartTypedOutput.put(FreeBConstants.CELLID, new TypedString(
                    FreeBCommonUtility.getCellID(context)));

            multipartTypedOutput.put(FreeBConstants.IMEI, new TypedString(
                    FreeBCommonUtility.getImeiNumber(context)));
            multipartTypedOutput.put(FreeBConstants.LATITUDE, new TypedString(
                    String.valueOf(deviceLattitude)));
            multipartTypedOutput.put(FreeBConstants.LONGITUDE, new TypedString(
                    String.valueOf(deviceLongitude)));
            multipartTypedOutput.put(FreeBConstants.HANDSET_MAKE,
                    new TypedString(Build.MANUFACTURER));
            multipartTypedOutput.put(FreeBConstants.HANDSET_MODEL,
                    new TypedString(Build.MODEL));
            multipartTypedOutput.put("mobileNumber", new TypedString(
                    FreeBCommonUtility.getMobileNo(context)));
            multipartTypedOutput.put(FreeBConstants.EMAIL_ID, new TypedString(
                    FreeBCommonUtility.getEmailId(context)));

            FreeBSDKLogger.d("Offers Request Array", ""+multipartTypedOutput.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    // public static MultipartTypedOutput getRegistrationParams(Context
    // mContext,
    // String affiliateId, String affiliateAppId, String density,
    // String deviceLattitude, String deviceLongitude, String appStatus) {
    // MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
    // try {
    //
    // multipartTypedOutput.addPart(FreeBConstants.VERSION,
    // new TypedString(Build.VERSION.RELEASE));
    // multipartTypedOutput.addPart(FreeBConstants.PACKAGE_NAME_SDK,
    // new TypedString(FreeBConstants.SDK_PACKAGENAME));
    // multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_ID,
    // new TypedString(affiliateId));
    //
    // multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_APP_ID,
    // new TypedString(affiliateAppId));
    // multipartTypedOutput.addPart(
    // FreeBConstants.CONN_TYPE,
    // new TypedString(FreeBCommonUtility
    // .getNetworkClass(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.DENSITY,
    // new TypedString(density));
    // multipartTypedOutput.addPart(FreeBConstants.STAGING,
    // new TypedString(appStatus));
    // multipartTypedOutput.addPart(FreeBConstants.EMAIL_ID,
    // new TypedString(FreeBCommonUtility.getEmailId(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.DEVICE_ID,
    // new TypedString(FreeBCommonUtility.getDeviceId(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.HANDSET_MAKE,
    // new TypedString(Build.MANUFACTURER));
    // multipartTypedOutput.addPart(FreeBConstants.HANDSET_MODEL,
    // new TypedString(Build.MODEL));
    //
    // multipartTypedOutput.addPart(FreeBConstants.MOBILE_OS,
    // new TypedString("Android"));
    // multipartTypedOutput
    // .addPart(
    // FreeBConstants.MOBILE_APP_VERSION,
    // new TypedString(FreeBCommonUtility
    // .getVersionCode(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.PACKAGE_NAME,
    // new TypedString(mContext.getPackageName()));
    // multipartTypedOutput.addPart(FreeBConstants.IS_MOBILE_APP,
    // new TypedString("1"));
    // multipartTypedOutput.addPart(FreeBConstants.MOBILE_SDK_VERSION,
    // new TypedString(FreeBConstants.SDK_VERSION));
    // multipartTypedOutput.addPart(FreeBConstants.IMEI, new TypedString(
    // FreeBCommonUtility.getImeiNumber(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.LATITUDE,
    // new TypedString(String.valueOf(deviceLattitude)));
    // multipartTypedOutput.addPart(FreeBConstants.LONGITUDE,
    // new TypedString(String.valueOf(deviceLongitude)));
    //
    // multipartTypedOutput.addPart(FreeBConstants.UDF1, new TypedString(
    // ""));
    // multipartTypedOutput.addPart(FreeBConstants.UDF2, new TypedString(
    // ""));
    // multipartTypedOutput.addPart(FreeBConstants.UDF3, new TypedString(
    // ""));
    //
    // multipartTypedOutput.addPart(FreeBConstants.UDF4, new TypedString(
    // ""));
    // multipartTypedOutput.addPart(FreeBConstants.UDF5, new TypedString(
    // ""));
    // multipartTypedOutput.addPart(FreeBConstants.MCC, new TypedString(
    // FreeBCommonUtility.getMCC(mContext)));
    //
    // multipartTypedOutput.addPart(FreeBConstants.MNC, new TypedString(
    // FreeBCommonUtility.getMNC(mContext)));
    // multipartTypedOutput.addPart(FreeBConstants.CELLID,
    // new TypedString(FreeBCommonUtility.getCellID(mContext)));
    //
    // multipartTypedOutput.addPart(FreeBConstants.LAC, new TypedString(
    // FreeBCommonUtility.getLAC(mContext)));
    // } catch (Exception e) {
    // }
    // return multipartTypedOutput;
    //
    // }

    public static List<NameValuePair> getRegistrationParams(Context mContext,
                                                            String affiliateId, String affiliateAppId, String density,
                                                            String deviceLattitude, String deviceLongitude, String appStatus) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        try {

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.VERSION,
                    Build.VERSION.RELEASE));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.PACKAGE_NAME_SDK,
                    FreeBConstants.SDK_PACKAGENAME));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_ID, affiliateId));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_APP_ID, affiliateAppId));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.CONN_TYPE,
                    FreeBCommonUtility.getNetworkClass(mContext)));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.DENSITY,
                    density));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.STAGING,
                    appStatus));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.EMAIL_ID,
                    FreeBCommonUtility.getEmailId(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.DEVICE_ID,
                    FreeBCommonUtility.getDeviceId(mContext)));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.HANDSET_MAKE, Build.MANUFACTURER));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.HANDSET_MODEL, Build.MODEL));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MOBILE_OS,
                    "Android"));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.MOBILE_APP_VERSION, FreeBCommonUtility
                    .getVersionCode(mContext)));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.PACKAGE_NAME, mContext.getPackageName()));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.IS_MOBILE_APP, "1"));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.MOBILE_SDK_VERSION,
                    FreeBConstants.SDK_VERSION));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.IMEI,
                    FreeBCommonUtility.getImeiNumber(mContext)));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LATITUDE,
                    String.valueOf(deviceLattitude)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LONGITUDE,
                    String.valueOf(deviceLongitude)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF1, ""));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF2, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF3, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF4, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF5, ""));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MCC,
                    FreeBCommonUtility.getMCC(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MNC,
                    FreeBCommonUtility.getMNC(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.CELLID,
                    FreeBCommonUtility.getCellID(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LAC,
                    FreeBCommonUtility.getLAC(mContext)));

        } catch (Exception e) {

            FreeBSDKLogger.e("Exception While Creating Registration Request Array",e.getMessage() != null ? e.getMessage()
                    : FreeBConstants.WRONG);

        }

        return nameValuePairs;

    }

    public static String getDensity(Context context) {
        String densityDevice = "";
        double density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            densityDevice = "xxxhdpi";
        }
        if (density >= 3.0 && density < 4.0) {
            densityDevice = "xxhdpi";
        }
        if (density >= 2.0) {
            densityDevice = "xhdpi";
        }
        if (density >= 1.5 && density < 2.0) {
            densityDevice = "hdpi";
        }
        if (density >= 1.0 && density < 1.5) {
            densityDevice = "mdpi";
        }
        if (density < 1.0) {
            densityDevice = "ldpi";
        }
        return densityDevice;
    }
}
