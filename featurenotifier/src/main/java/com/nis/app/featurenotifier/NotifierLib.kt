package com.nis.app.featurenotifier

import android.app.Application
import android.content.Context
import android.util.Log

class NotifierLib {
    private var props: NotifierPropsInterface? = null
    private var notifierCore: NotifierCore? = null

    class Builder {
        private var propsInterface: NotifierPropsInterface? = null

        fun setNotifierProp(props: NotifierPropsInterface): Builder {
            this.propsInterface = props;
            return this
        }

        fun create(app: Application?): NotifierLib {
            val lib = NotifierLib();
            try {
                lib.props = this.propsInterface;
                lib.notifierCore = NotifierCore(lib);
            } catch (e: Exception) {
                Log.e("NotifierLib", "create: " + e.message)
            }
            return lib;
        }
    }

    fun getProperties() = props!!
    
}