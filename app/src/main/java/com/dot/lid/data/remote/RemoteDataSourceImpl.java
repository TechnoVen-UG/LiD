package com.dot.lid.data.remote;

import android.util.Log;

import com.dot.lid.data.CallbackListener;
import com.dot.lid.model.Question;
import com.dot.lid.repository.HistoryRepository;
import com.dot.lid.repository.HumanityRepository;
import com.dot.lid.repository.PoliticsRepository;
import com.dot.lid.repository.StateRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static final String TAG = "sayed";

    private PoliticsRepository politicsRepository;
    private StateRepository stateRepository;
    private HistoryRepository historyRepository;
    private HumanityRepository humanityRepository;
    private int mergeCounter = 0;

    public RemoteDataSourceImpl(PoliticsRepository politicsRepository,
                                StateRepository stateRepository,
                                HistoryRepository historyRepository,
                                HumanityRepository humanityRepository) {
        this.politicsRepository = politicsRepository;
        this.stateRepository = stateRepository;
        this.historyRepository = historyRepository;
        this.humanityRepository = humanityRepository;
    }

    @Override
    public void getQuestionsByState(String languageName, String stateName, CallbackListener listener) {
        stateRepository.retrieve(languageName, stateName, listener);
    }

    @Override
    public void getQuestionsByPolitics(String languageName, String subCategory, CallbackListener listener) {
        politicsRepository.retrieve(languageName, subCategory, listener);
    }

    @Override
    public void getQuestionsByHistory(String languageName, String subCategory, CallbackListener listener) {
        historyRepository.retrieve(languageName, subCategory, listener);
    }

    @Override
    public void getQuestionsByHumanity(String languageName, String subCategory, CallbackListener listener) {
        humanityRepository.retrieve(languageName, subCategory, listener);
    }

    @Override
    public void getAllQuestionsExceptSate(String languageName, final CallbackListener listener) {
        final List<Question> questionList = new ArrayList<>();
        final Queue<List<Question>> queue = new LinkedList<>();
        CallbackListener callbackListener = new CallbackListener() {
            @Override
            public void onSuccess(final List<Question> questions) {
                queue.add(questions);
                mergeCounter++;
                if (mergeCounter == 3) {
                    mergeCounter = 0;
                    for (List<Question> questionList1 : queue) {
                        questionList.addAll(questionList1);
                    }
                    listener.onSuccess(questionList);
                }

            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        };

        getPoliticsQuestion(languageName, callbackListener);
        getHistoryQuestion(languageName, callbackListener);
        getHumanityQuestion(languageName, callbackListener);
    }

    @Override
    public void getAllQuestionsBySate(String languageName, CallbackListener listener) {
        stateRepository.retrieve(languageName, listener);
    }

    @Override
    public void getAllQuestionsByPolitics(String languageName, CallbackListener listener) {
        politicsRepository.retrieve(languageName, listener);
    }

    @Override
    public void getAllQuestionsByHistory(String languageName, CallbackListener listener) {
        historyRepository.retrieve(languageName, listener);
    }

    @Override
    public void getAllQuestionsByHumanity(String languageName, CallbackListener listener) {
        humanityRepository.retrieve(languageName, listener);
    }

    private void getPoliticsQuestion(final String languageName, final CallbackListener listener) {
        politicsRepository.retrieve(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                Log.d(TAG, "onSuccess: pilitics" + questions.size());
                listener.onSuccess(questions);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    private void getHistoryQuestion(final String languageName, final CallbackListener listener) {
        historyRepository.retrieve(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                listener.onSuccess(questions);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    private void getHumanityQuestion(String languageName, final CallbackListener listener) {
        humanityRepository.retrieve(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                listener.onSuccess(questions);
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

}
