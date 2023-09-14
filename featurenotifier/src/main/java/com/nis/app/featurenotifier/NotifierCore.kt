package com.nis.app.featurenotifier

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.nis.app.featurenotifier.model.NodeData
import com.nis.app.featurenotifier.views.BannerNotifierView
import com.nis.app.featurenotifier.views.DotNotifierView
import com.nis.app.featurenotifier.views.NewNotifierView
import com.nis.app.featurenotifier.views.NumberNotifierView

class NotifierCore() {

    private var tagToNodeDataMap: HashMap<String, NodeData?> = hashMapOf()
    private val tagNameToBooleanMap: HashMap<String, MutableLiveData<Boolean>> = hashMapOf();

    private lateinit var properties: NotifierPropsInterface
    private var isNotifierEnabled: Boolean = false;

    private val badgeDrawableMap = HashMap<String, BadgeDrawable>()

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
    fun canShowNotifierHere(tagName: String): Boolean? {
        return if (tagNameToBooleanMap.containsKey(tagName)) tagNameToBooleanMap[tagName]!!.value else false
    }

    fun notifierShown(tagName: String) {
        val nodesData = tagToNodeDataMap
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

    fun clearOrHideBatch(tagName: String) {
        badgeDrawableMap[tagName]?.let {
            it.isVisible = false
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

    fun addBadgeNotifierForView(view: View) {
        if (canShowNotifierHere(view.tag.toString()) == false) return
        val badge = BadgeDrawable.create(view.context)
        badge.isVisible = true
        Handler(Looper.getMainLooper()).postDelayed({
            BadgeUtils.attachBadgeDrawable(badge, view)
        }, 200)
        badgeDrawableMap[view.tag.toString()] = badge
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
        return if (tagToNodeDataMap[tagName]?.viewType?.containsKey(viewType) == true)
            ViewType.fromString(viewType)
        else
            null;
    }

    // private method of implementation
    companion object {
        private const val TAG = "NotifierCore"
    }

}