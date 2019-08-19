package com.android.whale.base.component

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

class AppCompatToolbar(
    private val appCompatActivity: AppCompatActivity,
    private val navigationOnClickListener: NavigationOnClickListener
) {

    companion object {
        const val NAVIGATION_BACK = "back"
        const val NAVIGATION_MENU = "menu"
        const val NAVIGATION_CLOSE = "close"
    }

    private val SUPPORT_NAVIGATION_ICONS = mapOf(
        NAVIGATION_BACK to R.drawable.ic_back_action_name,
        NAVIGATION_MENU to R.drawable.ic_menu_action_name,
        NAVIGATION_CLOSE to R.drawable.ic_close_action_name
    )

    private val toolbar: Toolbar by lazy {
        appCompatActivity.findViewById<Toolbar>(R.id.toolbar)
    }
    private val toolbarTitle: AppCompatTextView? by lazy {
        appCompatActivity.findViewById<AppCompatTextView>(R.id.toolbar_title)
    }

    fun setupToolBar() {
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setNavigationIcon(type: String?) {
        if (SUPPORT_NAVIGATION_ICONS.containsKey(type)) {
            val drawableRes = SUPPORT_NAVIGATION_ICONS[type]
            drawableRes?.let {
                toolbar.setNavigationIcon(drawableRes)
            }
        } else {
            toolbar.navigationIcon = null
        }
        toolbar.setNavigationOnClickListener {
            if (toolbar.navigationIcon != null) {
                navigationOnClickListener.onNavigationClick()
            }
        }
    }

    fun setTitle(title: String) {
        toolbarTitle?.let {
            it.text = title
        }
        toolbar.title=""
    }

    interface NavigationOnClickListener {
        fun onNavigationClick()
    }

}