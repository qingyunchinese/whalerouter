package com.android.whale.base.component

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(), AppCompatToolbar.NavigationOnClickListener {

    private val appCompatToolbar: AppCompatToolbar by lazy {
        AppCompatToolbar(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        appCompatToolbar.setupToolBar()
    }

    /**
     * NULL
     * AppCompatToolbar.NAVIGATION_BACK
     * AppCompatToolbar.NAVIGATION_MENU
     * AppCompatToolbar.NAVIGATION_CLOSE
     */
    fun setNavigationIcon(type: String?) {
        appCompatToolbar.setNavigationIcon(type)
    }

    fun setTitle(title: String) {
        appCompatToolbar.setTitle(title)
    }

    override fun onNavigationClick() {
        finish()
    }

    @LayoutRes
    open fun getLayoutId(): Int = -1
}