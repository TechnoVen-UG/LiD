package com.dot.lid.data.local;

import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.TestResult;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface LocalDataSource {
    Completable insertTestResult(TestResult testResult);

    Completable insertCacheQuestion(CacheQuestion cacheQuestion);

    Completable insertAchievement(List<Achievement> achievements);

    Single<List<TestResult>> retrieveAllTestResult();

    Single<List<CacheQuestion>> retrieveCacheQuestion(String categoryName);

    Single<List<Achievement>> retrieveAchievementByName(String name);

    Single<List<Achievement>> retrieveAllAchievement();

    Completable deleteCacheQuestion(String categoryName);

}
