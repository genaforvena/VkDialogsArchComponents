package org.imozerov.vkgroupdialogs.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import org.imozerov.vkgroupdialogs.viewmodel.ChatListViewModel;
import org.imozerov.vkgroupdialogs.viewmodel.ChatViewModel;
import org.imozerov.vkgroupdialogs.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChatListViewModel.class)
    abstract ViewModel bindChatListView(ChatListViewModel chatListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    abstract ViewModel bindSearchViewModel(ChatViewModel chatViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
