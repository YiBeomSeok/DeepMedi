package org.bmsk.data.datasource

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.bmsk.data.util.toDomainModel
import org.bmsk.domain.model.NetworkResult
import org.bmsk.domain.model.UploadImageResult
import org.bmsk.network.service.DeepMediService
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDatasource @Inject constructor(
    private val deepMediService: DeepMediService,
    private val ioDispatcher: CoroutineContext,
    @ApplicationContext private val context: Context
) {
    fun uploadImage(image: Bitmap): Flow<NetworkResult<UploadImageResult>> = flow {
        emit(NetworkResult.Loading)
        val file = image.toFile()
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        try {
            val response = deepMediService.uploadImage(body)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if(responseBody != null) {
                    emit(NetworkResult.Success(responseBody.toDomainModel()))
                } else {
                    emit(NetworkResult.Error("No response body found"))
                }
            } else {
                emit(NetworkResult.Error("Upload failed with HTTP code: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error("Upload failed with exception: ${e.message}"))
        }
    }.flowOn(ioDispatcher)

    fun getMeasuredResult() = flow {
        emit(NetworkResult.Loading)
        try {
            val response = deepMediService.getMeasuredResult()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if(responseBody != null) {
                    emit(NetworkResult.Success(responseBody.toDomainModel()))
                } else {
                    emit(NetworkResult.Error("No response body found"))
                }
            } else {
                emit(NetworkResult.Error("HTTP request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error("HTTP request failed with exception: ${e.message}"))
        }
    }.flowOn(ioDispatcher)

    private fun Bitmap.toFile(): File {
        // 임시 파일 생성
        val file = File.createTempFile("temp_img", null, context.cacheDir)

        FileOutputStream(file).use { out ->
            // 비트맵을 JPEG 포맷으로 압축
            this.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return file
    }
}