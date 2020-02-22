package app.mobile.carlist.carDetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import app.mobile.carlist.R
import app.mobile.carlist.databinding.FragmentCarDetailsBinding
import app.mobile.carlist.di.Injectable
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_car_details.*


class CarDetailsFragment : Fragment(), Injectable {
    lateinit var binding: FragmentCarDetailsBinding

    val args: CarDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarDetailsBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        binding.apply {
            clickListener = createComingSoonOnClickListener()
            car = args.car
            collapsingToolbar.apply {
                setupWithNavController(binding.toolbar, findNavController())
                setTitleEnabled(false)
            }
            car?.let {
                toolbar.setTitle(it.make)
                Glide.with(binding.root.context).load(it.picture).placeholder(
                    R.drawable.photo_place_holder
                ).error(R.drawable.photo_place_holder).into(this.carImage)
            }
        }
    }

    private fun createComingSoonOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            Snackbar.make(
                binding.root,
                resources.getText(R.string.coming_soon),
                Snackbar.LENGTH_LONG
            ).show();
        }

    }
}
