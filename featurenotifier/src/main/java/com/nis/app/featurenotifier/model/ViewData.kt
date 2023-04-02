package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class ViewData (
    @SerializedName("color" ) var color : String,
    @SerializedName("text") var text: String,
    @SerializedName("max_width") var maxWidth: Int,
    @SerializedName("overlay") var overlay: Boolean,
    @SerializedName("duration") var duration: Long,
    @SerializedName("show_arrow") var showArrow: Boolean,
    @SerializedName("follow_anchor") var followAnchor: Boolean,
    @SerializedName("activate_delay") var activateDelay: Long,
    @SerializedName("close_policy") var closePolicy: Int
)