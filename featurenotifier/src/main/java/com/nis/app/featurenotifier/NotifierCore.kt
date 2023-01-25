package com.nis.app.featurenotifier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nis.app.featurenotifier.model.NotifierConfig
import com.nis.app.featurenotifier.views.BannerNotifierView
import com.nis.app.featurenotifier.views.DotNotifierView
import com.nis.app.featurenotifier.views.NewNotifierView
import com.nis.app.featurenotifier.views.NumberNotifierView

class NotifierCore() {

    // map storing tagName(String) to node data(NodeData)
    private val tagToNodeData: NotifierConfig?
    private val notifierProps: NotifierProps? = NotifierLib.getProperties()

    // constructor and its params
    init {
        tagToNodeData = notifierProps?.getNotifierData()
    }

    // methods to be used by client
    fun canShowNotifierHere(tagName: String): LiveData<Boolean> {
        return MutableLiveData(false)
    }

    fun notifierShown(tagName: String) {

    }

    fun getNotifierViewCount(tagName: String): LiveData<Int> {
        return MutableLiveData(0)
    }

    fun getDotNotifierForTag(tagName: String): DotNotifierView? {
        return null
    }

    fun getBannerNotifierForTag(tagName: String): BannerNotifierView? {
        return null
    }

    fun getNewNotifierForTag(tagName: String): NewNotifierView? {
        return null
    }

    fun getNumberNotifierForTag(tagName: String): NumberNotifierView? {
        return null
    }

    fun viewTypesForTag(tagName: String): ViewType {
        return ViewType.DOT
    }
    
    // private method of implementation

}