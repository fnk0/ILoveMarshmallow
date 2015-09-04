package com.gabilheri.ilovemarshmallow.data.api;

import com.gabilheri.ilovemarshmallow.data.endpoint_models.AsinProduct;
import com.gabilheri.ilovemarshmallow.data.endpoint_models.SearchResult;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
public interface ZapposApi {

    @GET("/search")
    Observable<SearchResult> searchItems(
            @Query("term") String searchTerm,
            @Query("page") int page

    );

    @GET("/product/asin/{asinId}")
    Observable<AsinProduct> getAsinProduct(
            @Path("asinId") String asinId
    );
}
