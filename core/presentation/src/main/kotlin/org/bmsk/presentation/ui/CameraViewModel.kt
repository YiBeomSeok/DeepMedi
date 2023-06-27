package org.bmsk.presentation.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.bmsk.presentation.model.GuideState
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
) : ViewModel() {

    private val _guideState = MutableStateFlow(GuideState("얼굴 인식을 위해\n화면을 응시해 주세요.", "화면을 응시"))
    val guideState = _guideState.asStateFlow()
}