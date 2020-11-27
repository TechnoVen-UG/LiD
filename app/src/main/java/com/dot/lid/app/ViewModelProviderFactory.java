package com.dot.lid.app;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dot.lid.data.local.LocalDataSource;
import com.dot.lid.data.remote.RemoteDataSource;
import com.dot.lid.view.test.BarChartViewModel;
import com.dot.lid.view.test.TestViewModel;
import com.dot.lid.view.training.AchievementViewModel;
import com.dot.lid.view.training.StartTrainingViewModel;

public class ViewModelProviderFactory implements ViewModelProvider.Factory {

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    public ViewModelProviderFactory(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TestViewModel.class)) {
            return (T) new TestViewModel(remoteDataSource, localDataSource);
        }
        if (modelClass.isAssignableFrom(BarChartViewModel.class)) {
            return (T) new BarChartViewModel(localDataSource);
        }
        if (modelClass.isAssignableFrom(AchievementViewModel.class)) {
            return (T) new AchievementViewModel(localDataSource);
        }
        if (modelClass.isAssignableFrom(StartTrainingViewModel.class)) {
            return (T) new StartTrainingViewModel(remoteDataSource, localDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
