package org.imozerov.vkgroupdialogs.di;

import org.imozerov.vkgroupdialogs.MainActivity;
import org.imozerov.vkgroupdialogs.chat.ChatActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract ChatActivity contributeChatActivity();
}
