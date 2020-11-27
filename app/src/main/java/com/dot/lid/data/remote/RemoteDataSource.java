package com.dot.lid.data.remote;

import com.dot.lid.data.CallbackListener;

public interface RemoteDataSource {
    void getAllQuestionsExceptSate(String languageName, CallbackListener listener);

    void getAllQuestionsBySate(String languageName, CallbackListener listener);

    void getAllQuestionsByPolitics(String languageName, CallbackListener listener);

    void getAllQuestionsByHistory(String languageName, CallbackListener listener);

    void getAllQuestionsByHumanity(String languageName, CallbackListener listener);

    void getQuestionsByState(String languageName, String stateName, CallbackListener listener);

    void getQuestionsByPolitics(String languageName, String subCategory, CallbackListener listener);

    void getQuestionsByHistory(String languageName, String subCategory, CallbackListener listener);

    void getQuestionsByHumanity(String languageName, String subCategory, CallbackListener listener);
}
