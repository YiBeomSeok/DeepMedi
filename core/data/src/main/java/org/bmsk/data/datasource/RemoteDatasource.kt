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
    fun uploadImage(image: Bitmap): Flow<UploadImageResult> = flow {
        val file = image.toFile()
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        try {
            val response = deepMediService.uploadImage(body)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it.toDomainModel())
                } ?: emit(UploadImageResult(false, "Upload failed"))
            } else {
                emit(UploadImageResult(false, "Upload failed"))
            }
        } catch (e: Exception) {
            emit(UploadImageResult(false, "Upload failed: ${e.message}"))
        }
    }.flowOn(ioDispatcher)

    fun getMeasuredResult() = flow {
        try {
            val response = deepMediService.getMeasuredResult()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it.toDomainModel())
                } ?: emit(null)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
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