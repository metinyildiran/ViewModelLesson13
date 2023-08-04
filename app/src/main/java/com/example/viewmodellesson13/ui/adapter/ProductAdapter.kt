package com.example.viewmodellesson13.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.viewmodellesson13.R
import com.example.viewmodellesson13.data.model.Product
import com.example.viewmodellesson13.databinding.ProductListItemBinding

class ProductAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val toggleFavourite: (product: Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductListItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]

        holder.ivProduct.load(product.imageUrl)
        holder.tvName.text = product.name
        holder.ivFavourite.setOnClickListener {
            toggleFavourite.invoke(product)
        }

        if (product.isFavourite)
            holder.ivFavourite.setImageResource(R.drawable.baseline_star_24)
        else
            holder.ivFavourite.setImageResource(R.drawable.baseline_star_outline_24)
    }

    class ProductsViewHolder(binding: ProductListItemBinding) : ViewHolder(binding.root) {
        val ivProduct = binding.ivProduct
        val tvName = binding.tvName
        val ivFavourite = binding.ivFavourite
    }
}