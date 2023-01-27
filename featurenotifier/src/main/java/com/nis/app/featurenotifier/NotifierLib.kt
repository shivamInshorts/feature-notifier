package com.nis.app.featurenotifier

import android.content.Context

class NotifierLib {
    private var context: Context? = null
    private var props: NotifierPropsInterface? = null
    private var notifierCore: NotifierCore? = null

    fun build(context: Context, props: NotifierPropsInterface): NotifierLib {
        this.context = context
        if (this.props == null)
            this.props = props
        if (notifierCore == null)
            notifierCore = NotifierCore()
        return this
    }

    fun getProperties() = props

    fun getContext() = context

    fun getNotifierCore() = notifierCore
    
    companion object {
        private var INSTANCE: NotifierLib? = null
        private val LOCK = NotifierLib::class.java

        fun getInstance(): NotifierLib? {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = NotifierLib()
                    }
                }
            }
            return INSTANCE
        }
    }
}