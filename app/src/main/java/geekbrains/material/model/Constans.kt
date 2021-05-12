package geekbrains.material.model

import android.app.Activity
import android.content.SharedPreferences

class Constants {
    companion object {
        var sPrefs: SharedPreferencesManager? = null
        var TAG_THEME: String = "TAG_THEME"
        var APP_THEME: Int = 0
        var PINK_THEME: Int = 1
    }
}

class SharedPreferencesManager(activity: Activity) {

    private val sPreferences: SharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE)
    private var sEditor: SharedPreferences.Editor? = null
    private val editor: SharedPreferences.Editor
        get() = sPreferences.edit()

    fun retrieveInt(tag: String?, defValue: Int): Int {
        return sPreferences.getInt(tag, defValue)
    }

    fun storeInt(tag: String?, defValue: Int) {
        sEditor = editor
        sEditor!!.putInt(tag, defValue)
        sEditor!!.commit()
    }

}