package com.lirik.lagu.app.model;

/**
 * Created by omayib on 3/22/15.
 */
public interface Result<T> {
    void onSuccess(T result);
    void onFailure();
}
