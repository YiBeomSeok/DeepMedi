package org.bmsk.data.util

import org.bmsk.domain.model.AnalyzedInfo
import org.bmsk.domain.model.UploadImageResult
import org.bmsk.network.model.MeasuredResultRes
import org.bmsk.network.model.UploadImageRes

fun UploadImageRes.toDomainModel(): UploadImageResult {
    return UploadImageResult(
        isSuccess = code == 200,
        message = message
    )
}

fun MeasuredResultRes.toDomainModel() = AnalyzedInfo(
    name = name,
    profile = profile,
    cumulantMinusPoint = cumulantMinusPoint,
    bpm = bpm,
    sys = sys,
    dia = dia,
    resp = resp,
    fatigue = fatigue,
    stress = stress,
    temp = temp,
    alcohol = alcohol,
    spo2 = spo2,
)
