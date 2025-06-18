package com.example.scoopie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scoopie.repository.ProductRepository
import com.example.scoopie.model.ProductModel

class ProductViewModel(val repo: ProductRepository) : ViewModel() {

    fun addProduct(
        model: ProductModel,
        callback: (Boolean, String) -> Unit
    ) {
        repo.addProduct(model, callback)
    }

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    ) {
        repo.updateProduct(productId, data, callback)
    }

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit
    ) {
        repo.deleteProduct(productId, callback)
    }

    private val _products = MutableLiveData<ProductModel?>()
    val products: LiveData<ProductModel?> get() = _products

    private val _allProducts = MutableLiveData<List<ProductModel?>>()
    val allProducts: LiveData<List<ProductModel?>> get() = _allProducts

    private var _loading = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
        get() = _loading

    fun getProductById(
        productId: String,
    ) {
        repo.getProductById(productId) { success, msg, data ->
            if (success) {
                _products.postValue(data)
            } else {
                _products.postValue(null)

            }
        }
    }


    fun getAllProduct() {
        _loading.postValue(true)
        repo.getAllProduct { success, msg, data ->
            if (success) {
                _allProducts.postValue(data)
            } else {
                _loading.postValue(false)
                _allProducts.postValue(emptyList())
            }
        }
    }
}


