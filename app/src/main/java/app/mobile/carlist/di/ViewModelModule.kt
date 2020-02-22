package app.mobile.carlist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.mobile.carlist.cars.ui.CarsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CarsViewModel::class)
    abstract fun bindCarsViewModel(viewModel: CarsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
