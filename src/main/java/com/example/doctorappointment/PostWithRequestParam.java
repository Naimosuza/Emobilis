package com.example.doctorappointment;

import android.content.Context;
import android.preference.PreferenceActivity;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

public class PostWithRequestParam {

    private OnPostWithReqParamServiceCallListener listener;
    private RequestParams postData;
    private Context context;
    private String url;
    private boolean isLoaderRequired;
    private ACProgressFlower dialog = null;
    private ProgressBar pb = null;

    public interface OnPostWithReqParamServiceCallListener {

        void onSucceedToPostCall(JSONObject response);

        void onFailedToPostCall(int statusCode, String msg);
    }

    public PostWithRequestParam(Context context, String url, RequestParams postData, boolean isLoaderRequired, OnPostWithReqParamServiceCallListener listener) {
        this.listener = listener;
        this.postData = postData;
        this.context = context;
        this.isLoaderRequired = isLoaderRequired;
        this.url = url;
    }


    public void execute() {

        if (!ConnectionDetector.internetCheck(context, true)) {
            return;
        }

        if (isLoaderRequired) {
            dialog = HttpRequestHandler.getInstance().getProgressBar(context);
        }

        HttpRequestHandler.getInstance().postWithRequestParam(url, postData, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                if (dialog != null) {
                    dialog.show();
                } else {
                    if (pb != null) {
                        pb.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                else {
                    if (pb != null) {
                        pb.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                Logger.json(response.toString());
                if (listener != null)
                    listener.onSucceedToPostCall(response);
            }

            @Override
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    if (listener != null) {
                        if (statusCode == HTTP_BAD_REQUEST) {
                            if (errorResponse.has(Constant.AB_Message) && errorResponse.has(Constant.AB_IsSuccess)) {
                                String msg = errorResponse.getString(Constant.AB_Message);
                                if (msg != null && !msg.isEmpty())
                                    listener.onFailedToPostCall(statusCode, msg);
                                else
                                    listener.onFailedToPostCall(statusCode, context.getString(R.string.msg_server_error));

                            } else {
                                listener.onFailedToPostCall(statusCode, context.getString(R.string.msg_server_error));
                            }
                        } else {
                            listener.onFailedToPostCall(statusCode, context.getString(R.string.msg_server_error));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (listener != null)
                    listener.onFailedToPostCall(statusCode, context.getString(R.string.error_msg_connection_timeout));
            }

            @Override
            public void onFailure(int statusCode, PreferenceActivity.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                if (listener != null)
                    listener.onFailedToPostCall(statusCode, context.getString(R.string.error_msg_connection_timeout));
            }
        });
    }
}
