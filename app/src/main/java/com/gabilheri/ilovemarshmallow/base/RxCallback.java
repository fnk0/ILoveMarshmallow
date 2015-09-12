package com.gabilheri.ilovemarshmallow.base;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/3/15.
 *
 * Callback to be used with a RxSubscriber to receive a event when data is retrieved from the internet
 *
 * @param <T>
 *     Default data to be returned by this Callback
 */
public interface RxCallback<T> {

    void onDataReady(T data);
    void onDataError(Throwable e);

}
