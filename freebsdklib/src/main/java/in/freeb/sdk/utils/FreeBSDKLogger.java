package in.freeb.sdk.utils;

import android.util.Log;

/**
 * Logger utility is for logging debugging and error message in the application.
 * Here whether to log or not that is depends on {@code enableLogging}
 *
 */
public class FreeBSDKLogger {

	/**
	 *
     * @param tag set log tag
     * @param message set log message which has to be display
	 */
	public static void d(String tag, String message) {
		if (FreeBBuildConfig.DEBUG)
			Log.d(tag, message != null ? message : "");
	}

	/**
	 *
     * @param tag set log tag
     * @param message set log message which has to be display
	 */
	public static void e(String tag, String message) {
		if (FreeBBuildConfig.DEBUG)
			Log.e(tag, message != null ? message : "");
	}

	/**
	 *
     * @param tag set log tag
     * @param message set log message which has to be display
	 */
	public static void v(String tag, String message) {
		if (FreeBBuildConfig.DEBUG)
			Log.v(tag, message != null ? message : "");
	}
	
	/**
	 * Start logging debugging and error messages of our application
	 * @param shouldLogging input true for enable Logging else false
	 */
	public static void enableLogging(boolean shouldLogging) {
		FreeBBuildConfig.DEBUG = shouldLogging;
	}
}
