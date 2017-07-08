package org.imozerov.vkgroupdialogs.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import org.imozerov.vkgroupdialogs.persistance.db.DatabaseCreator;
import org.imozerov.vkgroupdialogs.persistance.db.entities.MessageEntity;

import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private static final MutableLiveData ABSENT = new MutableLiveData();

    private final int chatId;

    private final LiveData<List<MessageEntity>> observableMessages;

    public ChatViewModel(@NonNull Application application,
                         final int chatId) {
        super(application);
        this.chatId = chatId;

        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        observableMessages = Transformations.switchMap(databaseCreator.isDatabaseCreated(), new Function<Boolean, LiveData<List<MessageEntity>>>() {
            @Override
            public LiveData<List<MessageEntity>> apply(Boolean isDbCreated) {
                if (!isDbCreated) {
                    return ABSENT;
                } else {
                    return databaseCreator.getDatabase().messageDao().loadMessages(ChatViewModel.this.chatId);
                }
            }
        });

        databaseCreator.createDb(this.getApplication());
    }

    public LiveData<List<MessageEntity>> getMessages() {
        return observableMessages;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final int chatId;

        public Factory(@NonNull Application application, int chatId) {
            this.application = application;
            this.chatId = chatId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ChatViewModel(application, chatId);
        }
    }
}
