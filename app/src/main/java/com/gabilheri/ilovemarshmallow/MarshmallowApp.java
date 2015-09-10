package com.gabilheri.ilovemarshmallow;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.gabilheri.ilovemarshmallow.data.api.ZapposApi;
import com.gabilheri.ilovemarshmallow.data.api.ZapposClient;
import com.squareup.leakcanary.LeakCanary;

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

        // Don't need to be inside if DEBUG because the
        // non debug version of the library does nothing
        LeakCanary.install(this);

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());

            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(
                                    Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(
                                    Stetho.defaultInspectorModulesProvider(this))
                            .build());
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
