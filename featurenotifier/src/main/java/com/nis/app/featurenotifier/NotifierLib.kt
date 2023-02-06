package com.nis.app.featurenotifier

import android.content.Context

// Rewrite class implementing builder pattern. Singleton response
class NotifierLib {
    private lateinit var props: NotifierPropsInterface
    private var notifierCore: NotifierCore? = null

    fun build(props: NotifierPropsInterface): NotifierLib {
        this.props = props
        if (notifierCore == null)
            notifierCore = NotifierCore()
        return this
    }

    fun getProperties() = props

    fun getNotifierCore() = notifierCore
    
    companion object {
        // https://medium.com/swlh/android-library-kotlin-creation-access-deploy-problems-fixes-everything-you-want-to-c1a1701f0e8f
        private var INSTANCE: NotifierLib? = null
        private val LOCK = NotifierLib::class.java

        fun getInstance(): NotifierLib {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = NotifierLib()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}