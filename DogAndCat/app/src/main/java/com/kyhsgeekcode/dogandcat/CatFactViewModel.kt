package com.kyhsgeekcode.dogandcat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CatFactViewModel : ViewModel() {
    private val _facts = MutableStateFlow<List<CatFact>>(emptyList())
    val facts: StateFlow<List<CatFact>> = _facts

    private val _dogUrl = MutableStateFlow<String?>(null)
    val dogUrl: StateFlow<String?> = _dogUrl

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing

    private val isDogRefreshing = MutableStateFlow(false)
    private val isCatRefreshing = MutableStateFlow(false)

    val isRefreshing = combine(isDogRefreshing, isCatRefreshing) { dog, cat ->
        dog || cat
    }

    fun fetchFacts() {
        print("1")
        viewModelScope.launch {
            isCatRefreshing.value = true
            print("2")
            val facts = mutableListOf<CatFact>()
            repeat(5) {
                print("3")
                val fact = ApiClient.catFactService.getCatFact()
                facts.add(fact)
//                _facts.value = _facts.value + fact.fact
            }
            _facts.value = facts
            isCatRefreshing.value = false
        }
        print("4")
    }

    fun fetchDog() {
        viewModelScope.launch {
            isDogRefreshing.value = true
            try {
                val dog = ApiClient.dogApi.getRandomDogImage()
                _dogUrl.value = dog.message
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isDogRefreshing.value = false
        }
    }
}