package com.feathernet.jayfit.preferance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * 
 * @author Sandeep Kumar
 *
 */
public class SavedPreferance {

	public static final String CHECK_IN = "CHECK_IN";
	public static final String GOOGLE_LOGINED = "GOOGLE_LOGINED";
	public static final String INTRO_SKIPPED = "INTRO_SKIPPED";
	public static final String LOGIN_SKIPPED = "LOGIN_SKIPPED";
	public static final String EMAIL = "email";
	public static final String NAME = "name";
	public static final String PHOTO_URL = "PHOTO_URL";
	public static final String GMAIL_ACCESS_TOCKEN = "GMAIL_ACCESS_TOCKEN";



	public static void setString(Context context, String key, String value) {
		Editor edit = getPreferance(context).edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static String getString(Context context, String key) {
		return getPreferance(context).getString(key, "");
	}

	public static void setBoolean(Context context, boolean status, String Key) {
		Editor edit = getPreferance(context).edit();
		edit.putBoolean(Key, status);
		edit.commit();
	}

	public static boolean getBoolean(Context context, String key) {
		return getPreferance(context).getBoolean(key, false);
	}



	public static void setGoogleLogined(Context context, boolean status) {
		Editor edit = getPreferance(context).edit();
		edit.putBoolean(GOOGLE_LOGINED, status);
		edit.commit();
	}

	public static boolean getGoogleLogined(Context context) {
		return getPreferance(context).getBoolean(GOOGLE_LOGINED, false);
	}

	private static SharedPreferences getPreferance(Context context) {
		SharedPreferences preferences = android.preference.PreferenceManager
				.getDefaultSharedPreferences(context);
		return preferences;
	}
}
