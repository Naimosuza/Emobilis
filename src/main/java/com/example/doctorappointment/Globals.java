package com.example.doctorappointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.logging.Logger;

public class Globals { extends MultiDexApplication
{
        SharedPreferences sp;
        SharedPreferences.Editor editor;
        public static String TAG = "Globals";
        private static Globals instance;
        private DaoSession daoSession;


        @Override
        public void onCreate() {
            super.onCreate();
            MultiDex.install(this);
            instance = this;
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "users-db"); //The users-db here is the name of our database.
            Database db = helper.getWritableDb();
            daoSession = new DaoMaster(db).newSession();
            Logger.addLogAdapter(new AndroidLogAdapter() {
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return true;
                }
            });
        }

        public static synchronized Globals getInstance() {
            return instance;
        }
        }

        public DaoSession getDaoSession() {
            return daoSession;
        }

        /**
         * @param context
         * @param message
         */

        public SharedPreferences getSharedPref() {
            return sp = (sp == null) ? getSharedPreferences(Constant.MM_secrets, Context.MODE_PRIVATE) : sp;
        }

        public SharedPreferences.Editor getEditor() {
            return editor = (editor == null) ? getSharedPref().edit() : editor;
        }



        // storing model class in prefrence
        public static String toJsonString(UserLoginDetail params) {
            if (params == null) {
                return null;
            }
            Type mapType = new TypeToken<UserLoginDetail>() {
            }.getType();
            Gson gson = new Gson();
            return gson.toJson(params, mapType);
        }

        public static UserLoginDetail toUserDetails(String params) {
            if (params == null)
                return null;

            Type mapType = new TypeToken<UserLoginDetail>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(params, mapType);
        }

        public void setUserDetails(UserLoginDetail userMap) {
            getEditor().putString(Constant.AB_USER_MAP, toJsonString(userMap));
            getEditor().commit();
        }

        public static String trimString(AppCompatEditText textView) {
            return textView.getText().toString().trim();
        }

        // public static void logoutProcess(Context context) {
           // Intent i_logout = new Intent(context, LoginActivity.class);
            // set the new task and clear flags
            //i_logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //context.startActivity(i_logout);
        //}

        public static void showDialog(final Context context, final OnDialogClickListener listener,
        String title, String desc, String positiveButtonText,
                String negativeButtonText,
        boolean isCancelable, final int position) {
            if (desc == null || desc.isEmpty())
                return;
            final AlertDialog.Builder dialog = new Builder(context);
            dialog.setCancelable(isCancelable);
            dialog.setTitle(title).setMessage(desc);

            if (positiveButtonText != null && !positiveButtonText.isEmpty())
                dialog.setPositiveButton(positiveButtonText, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        listener.OnDialogPositiveClick(position);
                    }
                });
            else
                dialog.setPositiveButton("", null);
            if (negativeButtonText != null && !negativeButtonText.isEmpty())
                dialog.setNegativeButton(negativeButtonText, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        listener.OnDialogNegativeClick();
                    }
                });
            else {
                dialog.setNegativeButton("", null);
            }


            dialog.create().show();
        }

        public interface OnDialogClickListener {
            void OnDialogPositiveClick(int position);

            void OnDialogNegativeClick();
        }
    }

    public static void showToast(Context context, String message) {

        if (message == null || message.isEmpty() || context == null)
            return;

        //1st way to instantly update Toast message: with toasty library
        if (toast == null) {
            toast = Toasty.normal(context, message);
        }
        View v = toast.getView();
        if (v != null) {
            TextView tv = (TextView) v.findViewById(R.id.toast_text);
            if (tv != null)
                tv.setText(message);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static void logoutProcess(Context context) {
        Intent i_logout = new Intent(context, LoginActivity.class);
        // set the new task and clear flags
        i_logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i_logout);
    }

    public static Object getInstance() {
    }

    public static void hideKeyboard(Activity contextActivity) {
    try {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public boolean getUserDetails()
    {
        return toUserDetails(getSharedPref().getString(Constant.AB_USER_MAP, null));
    }
}