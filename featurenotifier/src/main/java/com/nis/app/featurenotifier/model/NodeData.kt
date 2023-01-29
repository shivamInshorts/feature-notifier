package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class NodeData(
    @SerializedName("count"          ) var count        : Int          = 0,
    @SerializedName("path"           ) var path         : String,
    @SerializedName("is_feature"    ) var isFeature   : Boolean,
    @SerializedName("view_data" ) var viewData     : HashMap<String, ViewData>
)