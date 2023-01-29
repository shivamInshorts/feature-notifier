package com.nis.app.featurenotifier.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class NotifierData(
    @SerializedName("default_view_type") var defaultViewType: String? = null,
    @SerializedName("nodes") var nodes: HashMap<String, NodeData?> = HashMap()
) {
    companion object {
        private const val TAG = "NotifierData"

        @JvmStatic
        fun toJson(notifierData: NotifierData?): String? {
            return runCatching {
                notifierData?.let {
                    Gson().toJson(notifierData)
                }
            }.getOrElse {
                null
            }
        }

        @JvmStatic
        fun fromJson(json: String?): NotifierData? {
            return runCatching {
                return Gson().fromJson(json, NotifierData::class.java)
            }.getOrElse {
                null
            }
        }
    }
}
