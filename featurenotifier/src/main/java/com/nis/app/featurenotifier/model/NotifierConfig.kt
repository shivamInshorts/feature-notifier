package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class NotifierConfig (
    @SerializedName("notifier_enabled" ) var notifierEnabled : Boolean? = null,
    @SerializedName("notifier_data"             ) var data            : NotifierData?    = NotifierData()
)