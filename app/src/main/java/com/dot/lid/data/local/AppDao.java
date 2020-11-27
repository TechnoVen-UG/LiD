package com.dot.lid.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dot.lid.model.Achievement;
import com.dot.lid.model.CacheQuestion;
import com.dot.lid.model.TestResult;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface AppDao {

    @Insert
    Completable insertTestResult(TestResult testResult);

    @Insert
    Completable insertCacheQuestion(CacheQuestion cacheQuestion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAchievement(List<Achievement> achievements);

    @Query("SELECT * FROM TestResult")
    Single<List<TestResult>> retrieveAllTestResult();

    @Query("SELECT * FROM Achievement WHERE name = :name")
    Single<List<Achievement>> retrieveAchievementByName(String name);

    @Query("SELECT * FROM Achievement")
    Single<List<Achievement>> retrieveAllAchievement();

    @Query("DELETE FROM CacheQuestion WHERE categoryName = :categoryName")
    Completable deleteCacheQuestion(String categoryName);

    @Query("SELECT * FROM CacheQuestion WHERE categoryName = :categoryName")
    Single<List<CacheQuestion>> retrieveCacheQuestion(String categoryName);

}
