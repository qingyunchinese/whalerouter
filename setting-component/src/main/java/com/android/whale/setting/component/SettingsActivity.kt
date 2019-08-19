package com.android.whale.setting.component

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.android.whale.base.component.AppCompatToolbar
import com.android.whale.base.component.BaseActivity
import com.whale.android.router.annotation.Router

@Router(path = ["setting"])
class SettingsActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.settings_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(resources.getString(R.string.app_name))
        setNavigationIcon(AppCompatToolbar.NAVIGATION_CLOSE)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}