package com.dot.lid.data.local;

import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.TestResult;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalDataSourceImpl implements LocalDataSource {
    private AppDao appDao;

    public LocalDataSourceImpl(AppDao appDao) {
        this.appDao = appDao;
    }

    @Override
    public Completable insertTestResult(TestResult testResult) {
        return appDao.insertTestResult(testResult);
    }

    @Override
    public Completable insertCacheQuestion(CacheQuestion cacheQuestion) {
        return appDao.insertCacheQuestion(cacheQuestion);
    }

    @Override
    public Completable insertAchievement(List<Achievement> achievements) {
        return appDao.insertAchievement(achievements);
    }


    @Override
    public Single<List<TestResult>> retrieveAllTestResult() {
        return appDao.retrieveAllTestResult();
    }

    @Override
    public Single<List<CacheQuestion>> retrieveCacheQuestion(String categoryName) {
        return appDao.retrieveCacheQuestion(categoryName);
    }

    @Override
    public Single<List<Achievement>> retrieveAchievementByName(String name) {
        return appDao.retrieveAchievementByName(name);
    }

    @Override
    public Single<List<Achievement>> retrieveAllAchievement() {
        return appDao.retrieveAllAchievement();
    }

    @Override
    public Completable deleteCacheQuestion(String categoryName) {
        return appDao.deleteCacheQuestion(categoryName);
    }
}
