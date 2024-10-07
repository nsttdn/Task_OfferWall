package com.example.task_offerwall.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_offerwall.data.models.ApiEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.task_offerwall.data.repositories.ApiRepository

class MainViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _objectId = MutableStateFlow<String?>(null)
    private val _objectType = MutableStateFlow<String?>(null)
    val objectType: StateFlow<String?> = _objectType
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message
    private val _url = MutableStateFlow<String?>(null)
    val url: StateFlow<String?> = _url

    private var allEntities: List<ApiEntity> = emptyList()
    private var currentIndex = 0

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                allEntities = repository.getAllIds()

                if (allEntities.isNotEmpty()) {
                    updateObjectData(allEntities[currentIndex].id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun nextObject() {
        if (allEntities.isNotEmpty()) {
            currentIndex = (currentIndex + 1) % allEntities.size
            updateObjectData(allEntities[currentIndex].id)
        }
    }

    private fun updateObjectData(id: String) {
        viewModelScope.launch {
            val objectResponse = repository.getObjectById(id)
            _objectType.value = objectResponse.type
            _message.value = objectResponse.message
            _url.value = objectResponse.url
        }
    }
}
