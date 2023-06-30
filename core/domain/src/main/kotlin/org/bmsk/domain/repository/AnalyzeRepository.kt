package org.bmsk.domain.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.model.UploadImageResult

interface AnalyzeRepository {
    suspend fun getAnalyzedInfo(): Flow<AnalyzedInfo?>
    suspend fun uploadImage(image: Bitmap): Flow<UploadImageResult?>
}