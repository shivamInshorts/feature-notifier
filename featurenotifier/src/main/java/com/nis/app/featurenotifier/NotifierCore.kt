package com.nis.app.featurenotifier

import android.util.AttributeSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nis.app.featurenotifier.model.NodeData
import com.nis.app.featurenotifier.views.BannerNotifierView
import com.nis.app.featurenotifier.views.DotNotifierView
import com.nis.app.featurenotifier.views.NewNotifierView
import com.nis.app.featurenotifier.views.NumberNotifierView

class NotifierCore() {

    // map storing tagName(String) to node data(NodeData)
    private val tagToNodeDataMap: MutableLiveData<HashMap<String, NodeData?>> = MutableLiveData();
    private val tagNameToBooleanMap: HashMap<String, MutableLiveData<Boolean>> = hashMapOf();

    private lateinit var properties: NotifierPropsInterface

    init {
        NotifierLib.getInstance()?.getProperties()?.let { properties = it }
        observeConfigData()
    }

    private fun observeConfigData() {
        properties.getNotifierData()
            .subscribe {
                updateData(it?.data!!.nodes);
            }.dispose()
    }

    fun canShowNotifierHere(tagName: String): LiveData<Boolean>? {
        return tagNameToBooleanMap[tagName];
    }

    fun notifierShown(tagName: String) {
        val nodesData = tagToNodeDataMap.value ?: throw Exception("No config data available")

        val nodeDataCount = nodesData[tagName]?.count!!
        nodesData[tagName]?.count = nodeDataCount - 1

        // TODO decrease count of its path also
        updateData(nodesData)
    }

    private fun updateData(nodesData: HashMap<String, NodeData?>) {
        tagToNodeDataMap.value = nodesData;
        for (tag in nodesData.keys) {
            if (tagNameToBooleanMap.containsKey(tag)) {
                tagNameToBooleanMap[tag]!!.postValue(nodesData[tag]?.count!! > 0)
            } else
                tagNameToBooleanMap[tag] = MutableLiveData(nodesData[tag]?.count!! > 0)
        }
    }

    private fun getNotifierViewCount(tagName: String): LiveData<Int> {
        TODO("Not yet implemented")
    }

    fun getDotNotifierForTag(tagName: String): DotNotifierView? {
        // check if dot notifier is available in data else return null
        return if (viewTypeForTag(tagName, ViewType.DOT.string()) == ViewType.DOT) {
//            val attrs = AttributeSet;
            DotNotifierView(NotifierLib.getInstance()?.getContext()!!)
        } else
            null
    }

    private fun getBannerNotifierForTag(tagName: String): BannerNotifierView? {
        TODO("Not yet implemented")
    }

    private fun getNewNotifierForTag(tagName: String): NewNotifierView? {
        TODO("Not yet implemented")
    }

    private fun getNumberNotifierForTag(tagName: String): NumberNotifierView? {
        TODO("Not yet implemented")
    }

    // A single view type for every node
    private fun viewTypeForTag(tagName: String, viewType: String): ViewType? {
        return if (tagToNodeDataMap.value?.get(tagName)?.viewData?.containsKey(viewType) == true)
            ViewType.fromString(viewType)
        else
            null;
    }

    // private method of implementation
    companion object {
        private const val TAG = "NotifierCore"
    }

}