package com.dot.lid.view.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dot.lid.data.CallbackListener;
import com.dot.lid.data.Resource;
import com.dot.lid.data.local.LocalDataSource;
import com.dot.lid.data.remote.RemoteDataSource;
import com.dot.lid.model.Question;
import com.dot.lid.model.TestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestViewModel extends ViewModel {
    private static final List<Question> allQuestion = new ArrayList<>();
    private final MutableLiveData<Resource<List<Question>>> testQuestionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<Boolean>> insertedResultLiveData = new MutableLiveData<>();
    private final RemoteDataSource dataSource;
    private final LocalDataSource localDataSource;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private int counter = 0;

    public TestViewModel(RemoteDataSource dataSource, LocalDataSource localDataSource) {
        this.dataSource = dataSource;
        this.localDataSource = localDataSource;
    }

    LiveData<Resource<List<Question>>> observeTestQuestion() {
        return testQuestionLiveData;
    }

    LiveData<Resource<Boolean>> observeInsertedResult() {
        return insertedResultLiveData;
    }

    void insertTestResult(TestResult testResult) {
        insertedResultLiveData.setValue(Resource.<Boolean>loading(null));
        localDataSource.insertTestResult(testResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        insertedResultLiveData.setValue(Resource.success(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertedResultLiveData.setValue(Resource.<Boolean>error("", null));
                    }
                });
    }

    void getQuestion(String languageName, final String stateName) {
        testQuestionLiveData.setValue(Resource.<List<Question>>loading(null));
        final List<Question> stateQuestion = new ArrayList<>();
        TestQuestionListener listener = new TestQuestionListener() {
            @Override
            public void onSuccess(List<Question> questions, boolean isSateQuestion) {
                if (isSateQuestion) {
                    stateQuestion.addAll(questions);
                } else {
                    allQuestion.addAll(questions);
                }
                counter++;
                if (allQuestion.size() > 0) {
                    counter = 0;
                    makeQuestion(stateQuestion);
                } else {
                    if (counter == 2) {
                        counter = 0;
                        makeQuestion(stateQuestion);
                    }
                }
            }

            @Override
            public void onFailure() {
                testQuestionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        };

        if (allQuestion.size() <= 0) {
            retrieveAllQuestion(languageName, listener);
        }
        retrieveQuestionByState(languageName, stateName, listener);

    }

    private void retrieveQuestionByState(final String languageName, String stateName, final TestQuestionListener listener) {

        dataSource.getQuestionsByState(languageName, stateName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    listener.onFailure();
                } else {
                    listener.onSuccess(questions, true);
                }
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    private void retrieveAllQuestion(String languageName, final TestQuestionListener listener) {
        dataSource.getAllQuestionsExceptSate(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    listener.onFailure();
                } else {
                    listener.onSuccess(questions, false);
                }
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    private void makeQuestion(List<Question> stateQuestion) {
        List<Question> finalQuestion = new ArrayList<>();
        List<Question> otherQuestion = new ArrayList<>(allQuestion);
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(stateQuestion.size() - 1);
            finalQuestion.add(stateQuestion.get(index));
            stateQuestion.remove(index);
        }

        for (int i = 0; i < 30; i++) {
            int index = random.nextInt(otherQuestion.size() - 1);
            finalQuestion.add(otherQuestion.get(index));
            otherQuestion.remove(index);
        }
        testQuestionLiveData.setValue(Resource.success(finalQuestion));
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    private interface TestQuestionListener {
        void onSuccess(List<Question> questions, boolean isSateQuestion);

        void onFailure();
    }
}
