package org.bmsk.data.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.bmsk.data.datasource.RemoteDatasource
import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.model.NetworkResult
import org.bmsk.domain.model.UploadImageResult
import org.bmsk.domain.repository.AnalyzeRepository
import javax.inject.Inject

class AnalyzeRepositoryImpl @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
): AnalyzeRepository {

    override suspend fun getAnalyzedInfo(): Flow<AnalyzedInfo?> = flow {
        emitAll(
            remoteDatasource.getMeasuredResult().map { result ->
                when (result) {
                    is NetworkResult.Success -> result.data
                    is NetworkResult.Error -> null // or some default value
                    is NetworkResult.Loading -> null // or some default value
                }
            }
        )
    }

    override suspend fun uploadImage(image: Bitmap): Flow<UploadImageResult?> = flow {
        emitAll(
            remoteDatasource.uploadImage(image).map { result ->
                when (result) {
                    is NetworkResult.Success -> result.data
                    is NetworkResult.Error -> null // or some default value
                    is NetworkResult.Loading -> null // or some default value
                }
            }
        )
    }
}