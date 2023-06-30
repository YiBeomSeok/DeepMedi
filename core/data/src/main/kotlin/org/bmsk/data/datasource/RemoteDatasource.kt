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
        val requestFile = file.asRequestBody(MEDIA_TYPE.toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData(IMAGE_KEY, file.name, requestFile)

        try {
            val response = deepMediService.uploadImage(body)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(NetworkResult.Success(responseBody.toDomainModel()))
                } else {
                    emit(NetworkResult.Error(NO_RESPONSE_BODY_FOUND))
                }
            } else {
                emit(NetworkResult.Error(message = UPLOAD_FAILED_HTTP_CODE + response.code()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = UPLOAD_FAILED_EXCEPTION + e.message))
        }
    }.flowOn(ioDispatcher)

    fun getMeasuredResult() = flow {
        emit(NetworkResult.Loading)
        try {
            val response = deepMediService.getMeasuredResult()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(NetworkResult.Success(responseBody.toDomainModel()))
                } else {
                    emit(NetworkResult.Error(message = NO_RESPONSE_BODY_FOUND))
                }
            } else {
                emit(NetworkResult.Error(message = HTTP_REQUEST_FAILED_CODE + response.code()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = HTTP_REQUEST_FAILED_EXCEPTION + e.message))
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

    companion object {
        const val IMAGE_KEY = "file"
        const val MEDIA_TYPE = "image/jpeg"
        const val NO_RESPONSE_BODY_FOUND = "No response body found"
        const val UPLOAD_FAILED_HTTP_CODE = "Upload failed with exception: "
        const val UPLOAD_FAILED_EXCEPTION = "Upload failed with exception: "
        const val HTTP_REQUEST_FAILED_CODE = "HTTP request failed with code: "
        const val HTTP_REQUEST_FAILED_EXCEPTION = "HTTP request failed with exception: "
    }
}