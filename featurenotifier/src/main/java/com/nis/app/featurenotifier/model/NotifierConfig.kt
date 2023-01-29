package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

private data class NotifierConfig (
    @SerializedName("notifier_enabled") var notifierEnabled : Boolean = false,
    @SerializedName("notifier_data"             ) var data            : NotifierData    = NotifierData()
)