package com.nis.app.featurenotifier

import android.content.Context
import androidx.lifecycle.LiveData
import com.nis.app.featurenotifier.model.NotifierData
import io.reactivex.Observable

interface NotifierPropsInterface {
    fun getNotifierData(context: Context): Observable<NotifierData?>

    fun isNotifierEnabled(): Observable<Boolean?>

}