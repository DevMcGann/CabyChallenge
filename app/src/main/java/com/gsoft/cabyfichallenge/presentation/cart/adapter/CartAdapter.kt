package com.gsoft.cabyfichallenge.presentation.cart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gsoft.cabyfichallenge.databinding.CartItemBinding
import com.gsoft.cabyfichallenge.domain.entity.CartItemCount

import javax.inject.Inject

class CartAdapter @Inject constructor() :
    ListAdapter<CartItemCount, CartAdapter.ViewHolder>(Comparator) {

    private var listener: ((item: String) -> Unit)? = null

    fun setOnItemClickListener(listener: (item: String) -> Unit) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(
            getItem(position),
            listener
        )

    class ViewHolder(
        private val itemBinding: CartItemBinding,

        ) : RecyclerView.ViewHolder(itemBinding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(
            productResponse: CartItemCount,
            listener: ((item: String) -> Unit)?
        ) {
            fun shouldOfferStringBeShown(): Boolean {
                return (productResponse.minQuantity != null && productResponse.discountType != null && productResponse.quantity >= productResponse.minQuantity)
            }


            itemBinding.tCartCode.text = productResponse.code
            itemBinding.tName.text = productResponse.name
            itemBinding.tUnitPrice.text = "$ ${productResponse.originalPrice}"
            itemBinding.tQuantity.text = "X${productResponse.quantity}"
            itemBinding.tSubTotal.text = "$${productResponse.price}"
            itemBinding.tDiscountString.isVisible = shouldOfferStringBeShown()
            itemBinding.tDiscountString.text = productResponse.promoString
            itemBinding.bDelete.setOnClickListener { listener?.invoke(productResponse.code) }
        }
    }

    object Comparator : DiffUtil.ItemCallback<CartItemCount>() {
        override fun areItemsTheSame(oldItem: CartItemCount, newItem: CartItemCount): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: CartItemCount, newItem: CartItemCount): Boolean =
            oldItem == newItem
    }
}