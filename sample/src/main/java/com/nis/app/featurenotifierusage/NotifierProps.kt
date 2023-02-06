package com.nis.app.featurenotifierusage

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nis.app.featurenotifier.NotifierPropsInterface
import com.nis.app.featurenotifier.model.NotifierData
import com.nis.app.featurenotifier.utils.readJsonAsset
import io.reactivex.Observable

class NotifierProps(private val context: Application) : NotifierPropsInterface {

    override fun getNotifierData(context: Context): Observable<NotifierData?> {
        val jsonToTest =
            readJsonAsset<JsonObject>("notifier_data.json", context.applicationContext)
        val notifierData = Gson().fromJson(jsonToTest!!.get("notifier_data"), NotifierData::class.java);
        return Observable.create { emitter ->
            emitter.onNext(notifierData!!)
            emitter.onComplete()
        }
    }

    override fun isNotifierEnabled(): Observable<Boolean?> {
        return Observable.just(true);
    }

    override fun getApplicationContext(): Application {
        return context;
    }
}