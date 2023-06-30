package org.bmsk.domain.usecase

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.model.UploadImageResult
import org.bmsk.domain.repository.AnalyzeRepository
import javax.inject.Inject

class AnalyzeUseCase @Inject constructor(
    private val repository: AnalyzeRepository
) {
    suspend fun getAnalyzedInfo(): Flow<AnalyzedInfo?> {
        return repository.getAnalyzedInfo()
    }

    suspend fun uploadImage(image: Bitmap): Flow<UploadImageResult?> {
        return repository.uploadImage(image)
    }
}