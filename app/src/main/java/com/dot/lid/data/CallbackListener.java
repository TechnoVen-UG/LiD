package com.dot.lid.data;

import com.dot.lid.model.Question;

import java.util.List;

public interface CallbackListener {
    void onSuccess(List<Question> questions);

    void onFailure();
}
