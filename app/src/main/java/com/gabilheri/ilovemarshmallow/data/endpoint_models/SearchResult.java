package com.gabilheri.ilovemarshmallow.data.endpoint_models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by <a href="mailto:marcusandreog@gmail.com">Marcus Gabilheri</a>
 *
 * @author Marcus Gabilheri
 * @version 1.0
 * @since 9/2/15.
 */
@Parcel
public class SearchResult {

    int totalResults;
    List<SearchResultItem> results;

    public SearchResult() {
    }

    public int getTotalResults() {
        return totalResults;
    }

    public SearchResult setTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public List<SearchResultItem> getResults() {
        return results;
    }

    public SearchResult setResults(List<SearchResultItem> results) {
        this.results = results;
        return this;
    }
}
