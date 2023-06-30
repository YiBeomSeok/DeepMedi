package org.bmsk.presentation.ui.camera

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.bmsk.domain.usecase.AnalyzeUseCase
import org.bmsk.presentation.R
import org.bmsk.presentation.model.GuideState
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val useCase: AnalyzeUseCase
) : ViewModel() {

    private val _guideState = MutableStateFlow(
        GuideState(
            R.string.guide_inital_face_recognition,
            R.string.guide_partial_face_recognition
        )
    )
    val guideState = _guideState.asStateFlow()

    fun uploadImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _guideState.value = _guideState.value.copy(
                loading = true
            )

            useCase.uploadImage(bitmap).collect {
                it?.let {
                    _guideState.value = _guideState.value.copy(
                        text = R.string.guide_face_recoginition_success,
                        partialText = R.string.guide_partial_success,
                        isSendButtonEnabled = true,
                        loading = false
                    )
                }
            }
        }
    }
}