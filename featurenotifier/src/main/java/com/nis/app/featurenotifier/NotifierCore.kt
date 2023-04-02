package com.nis.app.featurenotifier

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nis.app.featurenotifier.model.NodeData
import com.nis.app.featurenotifier.views.taptarget.DotNotifierView
import com.nis.app.featurenotifier.views.taptarget.NewNotifierView
import com.nis.app.featurenotifier.views.taptarget.NumberNotifierView
import com.nis.app.featurenotifier.views.tooltip.Tooltip

class NotifierCore {

    private var tagToNodeDataMap: HashMap<String, NodeData?>? = null;
    private val tagNameToBooleanMap: HashMap<String, MutableLiveData<Boolean>> = hashMapOf();

    private lateinit var properties: NotifierPropsInterface
    private var isNotifierEnabled: Boolean = false;

    init {
        NotifierLib.getInstance().getProperties().let { properties = it }
        observeConfigData()
    }

    private fun observeConfigData() {
        // will not work now
        properties.isNotifierEnabled()
            .subscribe {
                it?.let { isNotifierEnabled = it }
            }.dispose()

        properties.getNotifierData(
            NotifierLib.getInstance().getProperties().getApplicationContext()!!
        )
            .subscribe {
                updateData(it!!.nodes);
            }.dispose()
    }

    fun isTagValid(tagName: String): Boolean {
        return tagNameToBooleanMap.containsKey(tagName);
    }

    // TODO make the method return non nullable, create a function isTagNameValid?
    fun canShowNotifierHere(tagName: String): LiveData<Boolean> {
        return if (tagNameToBooleanMap.containsKey(tagName)) tagNameToBooleanMap[tagName]!! else MutableLiveData(
            false
        );
    }

    fun notifierShown(tagName: String) {
        if (tagToNodeDataMap == null) return
        val nodesData = tagToNodeDataMap!!
        val currNode = if (nodesData[tagName] != null) nodesData[tagName]!! else return;

        currNode.count -= 1
        updateData(nodesData)

        if (currNode.isFeature) {
            val parentPath = currNode.path.split("/").filter { it.isNotEmpty() }
            for (parent in parentPath) {
                notifierShown(parent)
            }
        }
    }

    private fun updateData(nodesData: HashMap<String, NodeData?>) {
        tagToNodeDataMap = nodesData;
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
            DotNotifierView(NotifierLib.getInstance().getProperties().getApplicationContext()!!)
        } else
            null
    }

    fun getTooltipNotifierForTag(anchorView: View?, tagName: String): Tooltip? {
        return if (viewTypeForTag(tagName, ViewType.DOT.string()) == ViewType.DOT) {
            if (anchorView != null) {
                getTooltipBuilderForTag(anchorView).create();
            } else
                getTooltipBuilderForTag().create();
        } else
            null
    }

    private fun getTooltipBuilderForTag(view: View? = null): Tooltip.Builder {
        val tooltipBuilder =
            Tooltip.Builder(properties.getApplicationContext()!!.applicationContext);
        if (view != null) {
            tooltipBuilder.anchor(view, 0, 0, false)
        }
//            .text("")
//            .styleId()
//            .typeface(typeface)
//            .maxWidth(metrics.widthPixels / 2)
//            .arrow(arrow)
//            .floatingAnimation(animation)
//            .closePolicy(closePolicy)
//            .showDuration(showDuration)
//            .overlay(overlay)
        return tooltipBuilder;
    }


    private fun getNewNotifierForTag(tagName: String): NewNotifierView? {
        TODO("Not yet implemented")
    }

    private fun getNumberNotifierForTag(tagName: String): NumberNotifierView? {
        TODO("Not yet implemented")
    }

    // A single view type for every node
    private fun viewTypeForTag(tagName: String, viewType: String): ViewType? {
        return if (tagToNodeDataMap?.get(tagName)?.viewType?.containsKey(viewType) == true)
            ViewType.fromString(viewType)
        else
            null;
    }

    // private method of implementation
    companion object {
        private const val TAG = "NotifierCore"
    }

}