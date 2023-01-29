package com.nis.app.featurenotifier

import androidx.lifecycle.LiveData
import com.nis.app.featurenotifier.model.NotifierData
import io.reactivex.Observable

interface NotifierPropsInterface {
    fun getNotifierData(): Observable<NotifierData?>

    fun isNotifierEnabled(): Observable<Boolean?>

}