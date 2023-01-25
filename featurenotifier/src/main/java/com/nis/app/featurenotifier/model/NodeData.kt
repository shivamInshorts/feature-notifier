package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class NodeData(
    @SerializedName("count"          ) var count        : Int?          = null,
    @SerializedName("view_type_data" ) var viewTypeData : ViewTypeData? = ViewTypeData()
)