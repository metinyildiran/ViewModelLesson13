package com.example.viewmodellesson13.viewmodel

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.viewmodellesson13.util.Util.showSnackbar
import com.example.viewmodellesson13.ui.adapter.ProductAdapter
import com.example.viewmodellesson13.databinding.ActivityProductsBinding
import com.example.viewmodellesson13.data.state.ProductListState
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
                                    binding.root.showSnackbar("${product.name} favorilere eklendi") {
                                        viewModel.setFavourite(false, product)
                                    }
                                } else {
                                    binding.root.showSnackbar("${product.name} favorilerden çıkarıldı") {
                                        viewModel.setFavourite(true, product)
                                    }
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