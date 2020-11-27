package com.dot.lid.view.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dot.lid.data.CallbackListener;
import com.dot.lid.data.Resource;
import com.dot.lid.data.local.LocalDataSource;
import com.dot.lid.data.remote.RemoteDataSource;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.Question;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.dot.lid.utils.DatabaseToken.COLL_HISTORY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_HUMANITY_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_POLITICS_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.COLL_STATE_ALL_QUESTION;
import static com.dot.lid.utils.DatabaseToken.DOC_BUNDESLANDER;
import static com.dot.lid.utils.DatabaseToken.DOC_GESCHICHTE_UND_VERANTWORTUNG;
import static com.dot.lid.utils.DatabaseToken.DOC_MENSCH_UND_GESELLSCHAFT;
import static com.dot.lid.utils.DatabaseToken.DOC_POLITIK_IN_DER_DEMOKARITE;

public class StartTrainingViewModel extends ViewModel {
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;
    private final MutableLiveData<Resource<List<Question>>> questionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<CacheQuestion>>> retrieveCacheQuestionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<Boolean>> insertedAchievementLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<Boolean>> deletedCacheQuestionLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private int counter = 0;


    public StartTrainingViewModel(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    LiveData<Resource<List<Question>>> observeTrainingQuestion() {
        return questionLiveData;
    }

    LiveData<Resource<List<CacheQuestion>>> observeRetrieveCacheQuestion() {
        return retrieveCacheQuestionLiveData;
    }

    LiveData<Resource<Boolean>> observeInsertedAchievement() {
        return insertedAchievementLiveData;
    }

    LiveData<Resource<Boolean>> observeDeletedCacheQuestion() {
        return deletedCacheQuestionLiveData;
    }

    void insertCacheQuestion(CacheQuestion cacheQuestion) {
        insertedAchievementLiveData.setValue(Resource.<Boolean>loading(null));
        localDataSource.insertCacheQuestion(cacheQuestion)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    void insertAchievement(List<Achievement> achievements) {
        insertedAchievementLiveData.setValue(Resource.<Boolean>loading(null));
        localDataSource.insertAchievement(achievements)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        insertedAchievementLiveData.setValue(Resource.success(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertedAchievementLiveData.setValue(Resource.<Boolean>error("", null));
                    }
                });
    }

    void retrieveCacheQuestion(String categoryName) {
        retrieveCacheQuestionLiveData.setValue(Resource.<List<CacheQuestion>>loading(null));
        localDataSource.retrieveCacheQuestion(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CacheQuestion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<CacheQuestion> cacheQuestions) {
                        retrieveCacheQuestionLiveData.setValue(Resource.success(cacheQuestions));
                    }

                    @Override
                    public void onError(Throwable e) {
                        retrieveCacheQuestionLiveData.setValue(Resource.<List<CacheQuestion>>error("", null));
                    }
                });
    }

    void deleteCacheQuestion(String categoryName) {
        deletedCacheQuestionLiveData.setValue(Resource.<Boolean>loading(null));
        localDataSource.deleteCacheQuestion(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        deletedCacheQuestionLiveData.setValue(Resource.success(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        deletedCacheQuestionLiveData.setValue(Resource.<Boolean>error("", null));
                    }
                });
    }

    void getTrainingQuestion(String languageName, String mainCategory, String subCategory, boolean isAllQuestion) {
        if (isAllQuestion) {
            retrieveAllQuestionForState(languageName, subCategory);
        } else {
            if (mainCategory.equals(DOC_BUNDESLANDER)) {
                if (subCategory.equals(COLL_STATE_ALL_QUESTION)) {
                    retrieveAllQuestionForState(languageName);
                } else {
                    retrieveQuestionByState(languageName, subCategory);
                }
            } else if (mainCategory.equals(DOC_POLITIK_IN_DER_DEMOKARITE)) {
                if (subCategory.equals(COLL_POLITICS_ALL_QUESTION)) {
                    retrieveAllQuestionForPolitics(languageName);
                } else {
                    retrieveQuestionByPolitics(languageName, subCategory);
                }
            } else if (mainCategory.equals(DOC_GESCHICHTE_UND_VERANTWORTUNG)) {
                if (subCategory.equals(COLL_HISTORY_ALL_QUESTION)) {
                    retrieveAllQuestionForHistory(languageName);
                } else {
                    retrieveQuestionByHistory(languageName, subCategory);
                }
            } else if (mainCategory.equals(DOC_MENSCH_UND_GESELLSCHAFT)) {
                if (subCategory.equals(COLL_HUMANITY_ALL_QUESTION)) {
                    retrieveAllQuestionForHumanity(languageName);
                } else {
                    retrieveQuestionByHumanity(languageName, subCategory);
                }
            }
        }
    }

    private void retrieveQuestionByPolitics(String languageName, String subCategory) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getQuestionsByPolitics(languageName, subCategory, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveAllQuestionForPolitics(String languageName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getAllQuestionsByPolitics(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveQuestionByHistory(String languageName, String subCategory) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getQuestionsByHistory(languageName, subCategory, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveAllQuestionForHistory(String languageName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getAllQuestionsByHistory(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveQuestionByHumanity(String languageName, String subCategory) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getQuestionsByHumanity(languageName, subCategory, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveAllQuestionForHumanity(String languageName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getAllQuestionsByHumanity(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveAllQuestionForState(String languageName, final String stateName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        final List<Question> questionList = new ArrayList<>();
        final Queue<List<Question>> queue = new LinkedList<>();
        CallbackListener listener = new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                queue.add(questions);
                counter++;
                if (counter == 2) {
                    counter = 0;
                    for (List<Question> questions1 : queue) {
                        questionList.addAll(questions1);
                    }
                    questionLiveData.setValue(Resource.success(questionList));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        };

        retrieveAllQuestionExceptState(languageName, listener);
        retrieveQuestionByState(languageName, stateName, listener);
    }

    private void retrieveAllQuestionForState(String languageName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getAllQuestionsBySate(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveQuestionByState(final String languageName, String stateName) {
        questionLiveData.setValue(Resource.<List<Question>>loading(null));
        remoteDataSource.getQuestionsByState(languageName, stateName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    questionLiveData.setValue(Resource.<List<Question>>error("", null));
                } else {
                    questionLiveData.setValue(Resource.success(questions));
                }
            }

            @Override
            public void onFailure() {
                questionLiveData.setValue(Resource.<List<Question>>error("", null));
            }
        });
    }

    private void retrieveQuestionByState(final String languageName, String stateName, final CallbackListener listener) {
        remoteDataSource.getQuestionsByState(languageName, stateName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    listener.onFailure();
                } else {
                    listener.onSuccess(questions);
                }
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    private void retrieveAllQuestionExceptState(String languageName, final CallbackListener listener) {
        remoteDataSource.getAllQuestionsExceptSate(languageName, new CallbackListener() {
            @Override
            public void onSuccess(List<Question> questions) {
                if (questions.size() <= 0) {
                    listener.onFailure();
                } else {
                    listener.onSuccess(questions);
                }
            }

            @Override
            public void onFailure() {
                listener.onFailure();
            }
        });
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
