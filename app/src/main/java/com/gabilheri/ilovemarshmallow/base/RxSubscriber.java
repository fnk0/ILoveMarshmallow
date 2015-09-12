package com.gabilheri.ilovemarshmallow.base;

import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/3/15.
 *
 * Helper class to simplify the RxJava calls. It unsubscribes itself by default to avoid Memory leaks
 * it also takes care of logging any errors
 *
 * @param <T>
 *     The type of data to be returned
 */
public class RxSubscriber<T> extends Subscriber<T> {

    RxCallback<T> callback;

    public RxSubscriber(RxCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onCompleted() {
        unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e, String.format("Error on the subscriber: %s", e.getMessage()));
        callback.onDataError(e);
    }

    @Override
    public void onNext(T data) {
        callback.onDataReady(data);
    }
}
