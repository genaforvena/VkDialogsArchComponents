package org.imozerov.vkgroupdialogs.di;

import org.imozerov.vkgroupdialogs.chatlist.ChatListActivity;
import org.imozerov.vkgroupdialogs.chat.ChatActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract ChatListActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract ChatActivity contributeChatActivity();
}
