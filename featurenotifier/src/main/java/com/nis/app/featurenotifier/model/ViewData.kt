package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class ViewData (
    @SerializedName("color" ) var color : String? = null,
    @SerializedName("text") var text: String? = null,
    @SerializedName("max_width") var maxWidth: Int? = 0,
    @SerializedName("overlay") var overlay: Boolean? = false,
    @SerializedName("duration") var duration: Long? = 0,
    @SerializedName("show_arrow") var showArrow: Boolean? = false,
    @SerializedName("follow_anchor") var followAnchor: Boolean? = false,
    @SerializedName("activate_delay") var activateDelay: Long? = 0,
    @SerializedName("close_policy") var closePolicy: Int? = 0
)