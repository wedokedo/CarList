package app.mobile.carlist.cars.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.mobile.carlist.R
import app.mobile.carlist.cars.data.Car
import app.mobile.carlist.databinding.CarListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.car_list_item.view.*


class CarListAdapter : ListAdapter<Car, CarListAdapter.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = getItem(position)
        holder.apply {
            bind(createOnClickListener(position),car)
            itemView.tag = car
            Glide.with(itemView.context).load(car.picture).apply(RequestOptions.circleCropTransform()).placeholder(
                R.drawable.photo_place_holder).error(R.drawable.photo_place_holder).into(itemView.car_img)
        }

    }
    private fun createOnClickListener(position:Int): View.OnClickListener {
        return View.OnClickListener {
            val car = getItem(position)
            val direction = CarListFragmentDirections.actionCarToDetail(car)
            Navigation.findNavController(it).navigate(direction)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CarListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }


    class ViewHolder(
        private val binding: CarListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener,item: Car) {
            binding.apply {
                clickListener = listener
                car = item
                executePendingBindings()
            }
        }
    }
    class DiffCallback : DiffUtil.ItemCallback<Car>() {

        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem.make.equals(newItem.make)
        }

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
            return oldItem == newItem
        }


}
}