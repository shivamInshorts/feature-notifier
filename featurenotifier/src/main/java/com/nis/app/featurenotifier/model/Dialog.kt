package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName


data class Dialog(
    @SerializedName("title") var title: String? = null,
    @SerializedName("button_text") var buttonText: String? = null,
    @SerializedName("background_color") var backgroundColor: String? = null
)
