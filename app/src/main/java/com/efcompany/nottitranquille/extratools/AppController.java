package com.efcompany.nottitranquille.extratools;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Shalby Omar on 28/01/16.
 */
public class AppController extends Application {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    private SharedPreferences mPreferences;

    public static AppController get() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //mRequestQueue = Volley.newRequestQueue(this);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     *
     * @param headers Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        String cookie = headers.get(SET_COOKIE_KEY);
        if (cookie == null) {
            //the server did not return a cookie so we wont have anything to save
            return;
        }
        SharedPreferences prefs = mPreferences;
        if (null == prefs) {
            return;
        }

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(COOKIE_KEY, cookie);
        editor.commit();
    }

    /**
     * Adds session cookie to headers if exists.
     *
     * @param headers
     */
//    public final void addSessionCookie(Map<String, String> headers) {
//        String sessionId = mPreferences.getString(SESSION_COOKIE, "");
//        if (sessionId.length() > 0) {
//            Log.d("ciao","qualcosa c'è" + sessionId);
//            StringBuilder builder = new StringBuilder();
//            builder.append(sessionId);
////            if (headers.containsKey(SESSION_COOKIE)) {
////                builder.append("; ");
////                builder.append(headers.get(COOKIE_KEY));
////            }
//            headers.put(COOKIE_KEY, builder.toString());
//        }
//
//    }
    public final void addSessionCookie(Map<String, String> headers)
    {
        SharedPreferences prefs = mPreferences;
        String cookie = prefs.getString(COOKIE_KEY, "");
        if (cookie.contains("expires")) {
        /** you might need to make sure that your cookie returns expires when its expired. I also noted that cokephp returns deleted */
            removeCookie();
            headers.put(COOKIE_KEY,"");
        }
        headers.put(COOKIE_KEY,cookie);
    }

    public void removeCookie() {
        SharedPreferences prefs = mPreferences;
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(COOKIE_KEY);
        editor.commit();
    }
}
