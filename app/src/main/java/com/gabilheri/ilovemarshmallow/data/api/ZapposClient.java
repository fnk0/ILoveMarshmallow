package com.gabilheri.ilovemarshmallow.data.api;

import android.app.Application;

import com.gabilheri.ilovemarshmallow.BuildConfig;

import retrofit.RestAdapter;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class ZapposClient {

    //https://zappos.amazon.com/mobileapi/v1/search?term=adidas
    public static final String API_CONTEXT = "mobileapi";
    public static final String API_VERSION = "v1";
    public static final String API_BASE_URL = "https://zappos.amazon.com";

    /**
     * Using the Application class which is a Singleton by nature guarantees this client to be a
     * singleton. Will also keep the activities from holding references to it avoiding memory leaks
     *
     * @param application
     *      Application on which this client should be initialized
     * @return
     *      The Api on which to make calls
     */
    public static ZapposApi initApi(Application application) {
        return new RestAdapter.Builder()
                .setEndpoint(buildApiEndpointURL())
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setClient(NetworkClient.getOkClient(application))
                .build().create(ZapposApi.class);
    }

    private static String buildApiEndpointURL() {
        return String.format("%s/%s/%s", API_BASE_URL, API_CONTEXT, API_VERSION);
    }
}
