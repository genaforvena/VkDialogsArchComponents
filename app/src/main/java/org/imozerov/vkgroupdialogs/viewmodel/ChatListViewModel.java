package org.imozerov.vkgroupdialogs.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator;
import org.imozerov.vkgroupdialogs.persistance.db.entities.ChatEntity;

import java.util.List;


public class ChatListViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<List<ChatEntity>> chats;

    public ChatListViewModel(Application application) {
        super(application);

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        LiveData<Boolean> databaseCreated = databaseCreator.isDatabaseCreated();
        chats = Transformations.switchMap(databaseCreated,
                new Function<Boolean, LiveData<List<ChatEntity>>>() {
                    @Override
                    public LiveData<List<ChatEntity>> apply(Boolean isDbCreated) {
                        if (!Boolean.TRUE.equals(isDbCreated)) {
                            return ABSENT;
                        } else {
                            return databaseCreator.getDatabase().chatDao().loadChats();
                        }
                    }
                });

        databaseCreator.createDb(this.getApplication());
    }

    public LiveData<List<ChatEntity>> getChats() {
        return chats;
    }
}
