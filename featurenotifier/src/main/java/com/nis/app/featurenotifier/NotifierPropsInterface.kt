package com.nis.app.featurenotifier

import androidx.lifecycle.LiveData
import com.nis.app.featurenotifier.model.NotifierConfig
import io.reactivex.Observable

interface NotifierPropsInterface {
    fun getNotifierData(): Observable<NotifierConfig?>

}