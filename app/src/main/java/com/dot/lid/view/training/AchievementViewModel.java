package com.dot.lid.view.training;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dot.lid.data.Resource;
import com.dot.lid.data.local.LocalDataSource;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.TestResult;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AchievementViewModel extends ViewModel {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private LocalDataSource localDataSource;
    private MutableLiveData<Resource<List<TestResult>>> testResultLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Achievement>>> achievementLiveData = new MutableLiveData<>();


    public AchievementViewModel(LocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    LiveData<Resource<List<TestResult>>> observerTestResult() {
        return testResultLiveData;
    }


    LiveData<Resource<List<Achievement>>> observeAchievementLiveData() {
        return achievementLiveData;
    }

    void retrieveTestResult() {
        testResultLiveData.setValue(Resource.<List<TestResult>>loading(null));
        localDataSource.retrieveAllTestResult()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<TestResult>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<TestResult> testResults) {
                        if (testResults.size() > 0) {
                            testResultLiveData.setValue(Resource.success(testResults));
                        } else {
                            testResultLiveData.setValue(Resource.<List<TestResult>>error("", null));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        testResultLiveData.setValue(Resource.<List<TestResult>>error("", null));
                    }
                });
    }

    void retrieveAllAchievement() {
        localDataSource.retrieveAllAchievement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Achievement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Achievement> achievements) {
                        achievementLiveData.setValue(Resource.success(achievements));
                    }

                    @Override
                    public void onError(Throwable e) {
                        achievementLiveData.setValue(Resource.<List<Achievement>>error("", null));
                    }
                });
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
