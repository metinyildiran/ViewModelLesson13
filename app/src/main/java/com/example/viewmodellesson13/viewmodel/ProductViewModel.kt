package com.example.viewmodellesson13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson13.data.model.Product
import com.example.viewmodellesson13.data.state.ProductListState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val productList = mutableListOf(
        Product(
            "Ampul",
            "https://img.vivense.com/480x320/images/1abdfa6009f6470696f4869d7bf8a3e0.jpg"
        ),
        Product(
            "Kablo",
            "https://st1.myideasoft.com/shop/hr/44/myassets/products/322/nyaf-kablo.jpg?revision=1514979689"
        ),
        Product("Florasan", "https://productimages.hepsiburada.net/s/25/375/10124950437938.jpg"),
        Product(
            "Masa",
            "https://akn-evidea.a-cdn.akinoncdn.com/products/2021/01/25/54109/98822fbc-e467-4e0c-bc07-eb6045aa05bc_size1000x1000_cropTop.jpg"
        ),
        Product(
            "Sehpa",
            "https://img.vivense.com/480x320/images/dfc69a77fbba482ca121f679c5221011.jpg"
        ),
        Product("Sandalye", "https://cilek.com/cdn/shop/products/21.08.8483.00_1.png?v=1618834429"),
        Product(
            "Tornavida",
            "https://st2.myideasoft.com/idea/cd/40/myassets/products/414/tornavida-fiyat.jpg?revision=1640171860"
        ),
    )

    private val _productListState: MutableStateFlow<ProductListState> =
        MutableStateFlow(ProductListState.Idle)
    val productListState: StateFlow<ProductListState> = _productListState

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            _productListState.value = ProductListState.Loading
            delay(3000)
            _productListState.value =
                if ((0..1).random() == 0) ProductListState.Result(productList) else ProductListState.Error(
                    NullPointerException()
                )
        }
    }

    fun setFavourite(isFavourite: Boolean, product: Product) {
        productList[productList.indexOf(product)].isFavourite = isFavourite
        _productListState.value = ProductListState.Result(productList)
    }
}