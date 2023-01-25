package com.nis.app.featurenotifier.model

import com.google.gson.annotations.SerializedName

data class NotifierData(
    @SerializedName("default_view_type") var defaultViewType: String? = null,
    @SerializedName("nodes") var nodes: HashMap<String, NodeData?>? = null
)
