package org.bmsk.network.service

import okhttp3.MultipartBody
import org.bmsk.network.model.MeasuredResultRes
import org.bmsk.network.model.UploadImageRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DeepMediService {

    @Multipart
    @POST("/deepmedi-test-first")
    suspend fun uploadImage(@Part body: MultipartBody.Part): Response<UploadImageRes>

    @GET("/deepmedi-test-second")
    suspend fun getMeasuredResult(): Response<MeasuredResultRes>
}