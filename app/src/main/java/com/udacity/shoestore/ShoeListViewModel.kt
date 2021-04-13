package com.udacity.shoestore

import androidx.lifecycle.*
import com.udacity.shoestore.models.ErrorModel
import com.udacity.shoestore.models.Shoe

class ShoeListViewModel : ViewModel() {

    private var shoesList: MutableList<Shoe> = listOf<Shoe>().toMutableList()
    private val _shoesData = MutableLiveData<List<Shoe>>()
    private val _errorData = MutableLiveData<ErrorModel>()
    private val _successData = MutableLiveData<Boolean>()

    val shoesData: LiveData<List<Shoe>>
        get() = _shoesData
    val errorData: LiveData<ErrorModel>
        get() = _errorData
    val successData: LiveData<Boolean>
        get() = _successData


    fun addShoe(name: String?, size: String?, company: String?, description: String?) {
        when {
            name.isNullOrEmpty() -> {
                _errorData.value = ErrorModel("Field name cannot be empty.")
            }
            size.isNullOrEmpty() -> {
                _errorData.value = ErrorModel("Field size cannot be empty.")
            }
            company.isNullOrEmpty() -> {
                _errorData.value = ErrorModel("Field company cannot be empty.")
            }
            description.isNullOrEmpty() -> {
                _errorData.value = ErrorModel("Field description cannot be empty.")
            }
            else -> {
                shoesList.add(Shoe(name, size.toDouble(), company, description))
                _shoesData.value = shoesList
                _successData.value = true
            }
        }
    }

    fun setSuccessData(boolean: Boolean) {
        _successData.value = false
    }
}