package com.example.viewmodellesson13.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var count: MutableLiveData<Int> = MutableLiveData(0)

    val fruit: MutableLiveData<String> = MutableLiveData("")

    private val _fruits: MutableLiveData<MutableList<String>> =
        MutableLiveData(mutableListOf("Elma", "Armut", "Çilek", "Kiraz"))
    val fruits: LiveData<MutableList<String>> = _fruits

    fun counterButtonClicked() {
        count.value?.let {
            count.postValue(it + 1)
        }
    }

    fun setFruit(f: String): String {
        fruit.postValue(f)

        return fruit.value!!
    }

    fun addFruit(fruit: String) {
        _fruits.value?.let {
            it.add(fruit)
            _fruits.postValue(it)
        }
    }
}