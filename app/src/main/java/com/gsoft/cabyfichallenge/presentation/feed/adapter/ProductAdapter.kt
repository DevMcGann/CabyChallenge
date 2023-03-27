package com.gsoft.cabyfichallenge.presentation.feed.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gsoft.cabyfichallenge.data.model.Product

import com.gsoft.cabyfichallenge.databinding.FeedItemBinding
import javax.inject.Inject

class ProductAdapter @Inject constructor()
    : ListAdapter<Product, ProductAdapter.ViewHolder>(Comparator){

    private var listener: ((item: Product) -> Unit)? = null

    fun setOnItemClickListener(listener: (item: Product) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FeedItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(
            getItem(position),
            listener
        )

    class ViewHolder(
        private val itemBinding: FeedItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            productResponse: Product,
            listener: ((item: Product) -> Unit)?
        ) {
                itemBinding.tCode.text = productResponse.code
                itemBinding.itemText.text = productResponse.name
                itemBinding.itemPrice.text = "$ ${productResponse.price}"
                itemBinding.bAddCart.setOnClickListener { listener?.invoke(productResponse) }
        }
    }

    object Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }
}