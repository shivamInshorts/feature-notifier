package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class ViewTypeData (
    @SerializedName("dot"    ) var dot    : Dot?    = Dot(),
    @SerializedName("dialog" ) var dialog : Dialog? = Dialog()
)