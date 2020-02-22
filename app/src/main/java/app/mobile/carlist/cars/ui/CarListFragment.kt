package app.mobile.carlist.cars.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import app.mobile.carlist.R
import app.mobile.carlist.data.Result
import app.mobile.carlist.databinding.FragmentCarsBinding
import app.mobile.carlist.di.Injectable
import app.mobile.carlist.di.injectViewModel
import app.mobile.carlist.util.setDivider
import javax.inject.Inject

class CarListFragment : Fragment(), Injectable
{
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding : FragmentCarsBinding
    lateinit var viewModel: CarsViewModel
    lateinit var adapter : CarListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setUpUi(inflater,container)
        subscribeUi()
        return binding.root
    }
    private fun setUpUi(inflater: LayoutInflater,container: ViewGroup?) {
        viewModel = injectViewModel(viewModelFactory)
        binding = FragmentCarsBinding.inflate(inflater, container, false)
        adapter = CarListAdapter()
        binding.apply {
            toolbar.setupWithNavController(findNavController())
            carList.setDivider(R.drawable.recycler_view_divider)
            carList.adapter = adapter
            pullToRefresh.setOnRefreshListener {
                viewModel.refreshCars()
            }
        }
    }
    private fun subscribeUi() {

        viewModel.cars.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {

                    result.data?.let {
                        binding.apply {
                            showData()
                        }
                        adapter.submitList(it) }

                }
                Result.Status.LOADING -> {
                    if (!binding.pullToRefresh.isRefreshing) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
                Result.Status.ERROR -> {
                    binding.apply {
                        showErrorMessage()
                    }

                }
            }
        })
    }

    private fun FragmentCarsBinding.showData() {
        progressBar.visibility = View.GONE
        pullToRefresh.isRefreshing = false
        errorView.visibility = View.GONE
    }

    private fun FragmentCarsBinding.showErrorMessage() {
        progressBar.visibility = View.GONE
        pullToRefresh.isRefreshing = false
        errorView.visibility = View.VISIBLE
    }
}