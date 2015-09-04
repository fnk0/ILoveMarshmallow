package com.gabilheri.ilovemarshmallow;

import android.app.Application;

import com.gabilheri.ilovemarshmallow.data.api.ZapposApi;
import com.gabilheri.ilovemarshmallow.data.api.ZapposClient;

import timber.log.Timber;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public class MarshmallowApp extends Application {

    private static MarshmallowApp mInstance;
    private ZapposApi mZapposApi;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        mInstance = this;
    }

    public static MarshmallowApp instance() {
        return mInstance;
    }

    public ZapposApi api() {
        if(mZapposApi == null) {
            mZapposApi = ZapposClient.initApi(this);
        }
        return mZapposApi;
    }
}
