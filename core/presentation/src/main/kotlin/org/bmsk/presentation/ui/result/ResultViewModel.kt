package org.bmsk.presentation.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.usecase.AnalyzeUseCase
import org.bmsk.presentation.model.ListItem
import org.bmsk.presentation.model.UserHealthStateItem
import org.bmsk.presentation.model.UserProfileItem
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val useCase: AnalyzeUseCase
) : ViewModel() {

    private val _dataState = MutableStateFlow<List<ListItem>>(emptyList())
    val dataState = _dataState.asStateFlow()

    init {
        viewModelScope.launch {
            val list = mutableListOf<ListItem>()
            useCase.getAnalyzedInfo().collectLatest {
                it?.let {
                    list.apply {
                        add(it.toProfile())
                        add(it.toHealthStateItem())
                    }
                    _dataState.value = list
                }
            }
        }
    }

    private fun AnalyzedInfo.toProfile() = UserProfileItem(
        name = name,
        profile = profile,
        cumulantMinusPoint = cumulantMinusPoint
    )

    private fun AnalyzedInfo.toHealthStateItem() = UserHealthStateItem(
        bpm = bpm,
        sys = sys,
        dia = dia,
        resp = resp,
        fatigue = fatigue,
        stress = stress,
        temp = temp,
        alcohol = alcohol,
        spo2 = spo2
    )
}