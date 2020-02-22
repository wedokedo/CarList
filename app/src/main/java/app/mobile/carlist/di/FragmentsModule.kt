package app.mobile.carlist.di


import app.mobile.carlist.carDetails.ui.CarDetailsFragment
import app.mobile.carlist.cars.ui.CarListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun contributeCarListFragment(): CarListFragment

    @ContributesAndroidInjector
    abstract fun contributeCarDetailsFragment(): CarDetailsFragment
}