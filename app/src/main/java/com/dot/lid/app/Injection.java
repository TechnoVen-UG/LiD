package com.dot.lid.app;

import android.content.Context;

import com.dot.lid.data.local.AppDao;
import com.dot.lid.data.local.AppRoomDatabase;
import com.dot.lid.data.local.LocalDataSource;
import com.dot.lid.data.local.LocalDataSourceImpl;
import com.dot.lid.data.remote.RemoteDataSource;
import com.dot.lid.data.remote.RemoteDataSourceImpl;
import com.dot.lid.repository.HistoryRepository;
import com.dot.lid.repository.HistoryRepositoryImpl;
import com.dot.lid.repository.HumanityRepository;
import com.dot.lid.repository.HumanityRepositoryImpl;
import com.dot.lid.repository.PoliticsRepository;
import com.dot.lid.repository.PoliticsRepositoryImpl;
import com.dot.lid.repository.StateRepository;
import com.dot.lid.repository.StateRepositoryImpl;


public class Injection {

    private static RemoteDataSource provideRemoteDataSource() {
        PoliticsRepository politicsRepository = new PoliticsRepositoryImpl();
        StateRepository stateRepository = new StateRepositoryImpl();
        HistoryRepository historyRepository = new HistoryRepositoryImpl();
        HumanityRepository humanityRepository = new HumanityRepositoryImpl();
        return new RemoteDataSourceImpl(politicsRepository, stateRepository,
                historyRepository, humanityRepository);
    }

    private static LocalDataSource provideLocalDataSource(Context context) {
        AppRoomDatabase database = AppRoomDatabase.getInstance(context);
        AppDao appDao = database.getAppDaoInstance();
        return new LocalDataSourceImpl(appDao);
    }

    public static ViewModelProviderFactory provideViewModelProviderFactory(Context context) {
        RemoteDataSource remoteDataSource = provideRemoteDataSource();
        LocalDataSource localDataSource = provideLocalDataSource(context);
        return new ViewModelProviderFactory(remoteDataSource, localDataSource);
    }
}
