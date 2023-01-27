package com.nis.app.featurenotifierusage

import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.nis.app.featurenotifier.NotifierPropsInterface
import com.nis.app.featurenotifier.model.NotifierConfig
import com.nis.app.featurenotifier.model.NotifierData
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.util.*

class NotifierProps : NotifierPropsInterface {

    private val jsonToTest = "{\n" +
            "    \"notifier_enabled\": true,\n" +
            "    \"notifier_data\": {\n" +
            "        \"default_view_type\": \"dot\",\n" +
            "        \"nodes\": {\n" +
            "            \"discover\": {\n" +
            "                \"count\": 2,\n" +
            "                \"view_data\": {\n" +
            "                    \"dot\": {\n" +
            "                        \"color\": \"red\"\n" +
            "                    },\n" +
            "                    \"dialog\": {}\n" +
            "                }\n" +
            "            },\n" +
            "            \"settings\": {\n" +
            "                \"count\": 1,\n" +
            "                \"view_data\": {\n" +
            "                    \"dot\": {\n" +
            "                        \"color\": \"red\"\n" +
            "                    },\n" +
            "                    \"dialog\": {}\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}";

    val handler = Handler()
    private val TAG = "NotifierProps"
    override fun getNotifierData(): Observable<NotifierConfig?> {
        val response = Gson().fromJson(jsonToTest, NotifierConfig::class.java)
        return Observable.create { emitter  ->
            emitter.onNext(response)
            emitter.onComplete()
        }
    }
}