package org.bmsk.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import org.bmsk.data.datasource.RemoteDatasource
import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.model.UploadImageResult
import org.bmsk.domain.repository.AnalyzeRepository
import javax.inject.Inject

class AnalyzeRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
): AnalyzeRepository {

    override suspend fun getAnalyzedInfo(): Flow<AnalyzedInfo?> {
        return remoteDatasource.getMeasuredResult()
    }

    override suspend fun uploadImage(image: Bitmap): Flow<UploadImageResult?> {
        return remoteDatasource.uploadImage(image)
    }
}