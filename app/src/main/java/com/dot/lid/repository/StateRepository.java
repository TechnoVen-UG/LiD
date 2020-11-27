package com.dot.lid.repository;

import com.dot.lid.data.CallbackListener;

public interface StateRepository {
    void retrieve(String languageName, CallbackListener listener);

    void retrieve(String languageName, String collectionName, CallbackListener listener);
}
