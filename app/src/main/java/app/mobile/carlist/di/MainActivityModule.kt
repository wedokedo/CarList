package app.mobile.carlist.di

import app.mobile.carlist.di.FragmentsModule
import app.mobile.carlist.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun contributeMainActivity(): MainActivity
}