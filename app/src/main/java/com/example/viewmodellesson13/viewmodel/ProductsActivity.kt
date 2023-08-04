package com.example.viewmodellesson13.viewmodel

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.viewmodellesson13.adapter.ProductAdapter
import com.example.viewmodellesson13.databinding.ActivityProductsBinding
import com.example.viewmodellesson13.model.ProductListState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    private val viewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()

        listeners()
    }

    private fun listeners() {
        binding.btnGetProducts.setOnClickListener {
            viewModel.getProducts()
        }
    }

    private fun observers() {
        lifecycleScope.launch {
            viewModel.productListState.collect {
                when (it) {
                    is ProductListState.Idle -> Unit
                    is ProductListState.Loading -> {
                        binding.rvProducts.adapter =
                            ProductAdapter(this@ProductsActivity, listOf()) {}
                        binding.progressBar.visibility = VISIBLE
                    }

                    is ProductListState.Result -> {
                        binding.rvProducts.adapter =
                            ProductAdapter(this@ProductsActivity, it.products) { product ->
                                if (!product.isFavourite) {
                                    Snackbar.make(
                                        binding.root,
                                        "${product.name} favorilere eklendi",
                                        Snackbar.LENGTH_SHORT
                                    ).setAction("Geri al") {
                                        viewModel.setFavourite(false, product)
                                    }.show()
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        "${product.name} favorilerden çıkarıldı",
                                        Snackbar.LENGTH_SHORT
                                    ).setAction("Geri al") {
                                        viewModel.setFavourite(true, product)
                                    }.show()
                                }

                                viewModel.setFavourite(!product.isFavourite, product)
                            }
                        binding.progressBar.visibility = GONE
                    }

                    is ProductListState.Error -> {
                        it.exception.printStackTrace()

                        binding.rvProducts.adapter =
                            ProductAdapter(this@ProductsActivity, listOf()) {}
                        binding.progressBar.visibility = GONE

                        AlertDialog.Builder(this@ProductsActivity).setMessage("An error occurred")
                            .setPositiveButton("Try Again") { _, _ ->
                                viewModel.getProducts()
                            }.setOnDismissListener {
                                viewModel.getProducts()
                            }.create().show()
                    }
                }
            }
        }
    }
}