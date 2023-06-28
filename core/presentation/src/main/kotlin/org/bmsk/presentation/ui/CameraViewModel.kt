package org.bmsk.presentation.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.domain.usecase.AnalyzeUseCase
import org.bmsk.presentation.model.GuideState
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val useCase: AnalyzeUseCase
) : ViewModel() {

    private val _guideState = MutableStateFlow(GuideState("얼굴 인식을 위해\n화면을 응시해 주세요.", "화면을 응시"))
    val guideState = _guideState.asStateFlow()

    fun uploadImage(bitmap: Bitmap) {
        _guideState.value = _guideState.value.copy(
            text = "얼굴 인식 성공",
            partialText = "성공",
            isSendButtonEnabled = true
        )
        viewModelScope.launch {
            useCase.uploadImage(bitmap).first()?.let {
                Log.e("CameraViewModel", it.message)
            }
        }
    }
}