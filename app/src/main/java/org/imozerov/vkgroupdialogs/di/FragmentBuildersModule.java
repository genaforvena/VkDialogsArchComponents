package org.imozerov.vkgroupdialogs.di;

import org.imozerov.vkgroupdialogs.chat.ChatFragment;
import org.imozerov.vkgroupdialogs.chatlist.ChatListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ChatListFragment chatListFragment();

    @ContributesAndroidInjector
    abstract ChatFragment chatFragment();
}
