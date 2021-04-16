package com.udacity.shoestore

import androidx.lifecycle.*
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {

    private var shoesList: MutableList<Shoe> = listOf<Shoe>().toMutableList()
    private val _shoesData = MutableLiveData<List<Shoe>>()
    private val _errorNameData = MutableLiveData<String>()
    private val _errorSizeData = MutableLiveData<String>()
    private val _errorCompanyData = MutableLiveData<String>()
    private val _errorDescriptionData = MutableLiveData<String>()
    private val _successData = MutableLiveData<Boolean>()

    val shoeNameData = MutableLiveData<String>()
    val shoeSizeData = MutableLiveData<String>()
    val shoeCompanyData = MutableLiveData<String>()
    val shoeDescriptionData = MutableLiveData<String>()

    private var hasError = false

    val shoesData: LiveData<List<Shoe>>
        get() = _shoesData
    val errorNameData: LiveData<String>
        get() = _errorNameData
    val errorSizeData: LiveData<String>
        get() = _errorSizeData
    val errorCompanyData: LiveData<String>
        get() = _errorCompanyData
    val errorDescriptionData: LiveData<String>
        get() = _errorDescriptionData
    val successData: LiveData<Boolean>
        get() = _successData


    fun addShoe() {

        hasError = false
        if(shoeNameData.value.isNullOrEmpty()) {
            hasError = true
            _errorNameData.value = "Field name cannot be empty."
        } else {
            _errorNameData.value = null
        }
        if(shoeSizeData.value.isNullOrEmpty()) {
            hasError = true
            _errorSizeData.value = "Field size cannot be empty."
        } else {
            _errorSizeData.value = null
        }
        if(shoeCompanyData.value.isNullOrEmpty()) {
            hasError = true
            _errorCompanyData.value = "Field company cannot be empty."
        } else {
            _errorCompanyData.value = null
        }
        if(shoeDescriptionData.value.isNullOrEmpty()) {
            hasError = true
            _errorDescriptionData.value = "Field description cannot be empty."
        } else {
            _errorDescriptionData.value = null
        }
        if(!hasError) {
            shoesList.add(
                Shoe(shoeNameData.value ?: "",
                    shoeSizeData.value?.toDouble() ?: 0.0,
                    shoeCompanyData.value ?: "",
                    shoeDescriptionData.value ?: "")
            )
            _shoesData.value = shoesList
            shoeNameData.value = null
            shoeSizeData.value = null
            shoeCompanyData.value = null
            shoeDescriptionData.value = null
            _successData.value = true
        }
    }

    fun setSuccessData(boolean: Boolean) {
        _successData.value = false
    }
}