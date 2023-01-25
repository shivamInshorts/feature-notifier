package com.nis.app.featurenotifier

import com.nis.app.featurenotifier.model.NotifierConfig

interface NotifierProps {

    fun isNotifierEnabled(): Boolean? = false

    fun defaultViewType(): String? = ViewType.DOT.string()

    fun getNotifierData(): NotifierConfig? = null

}