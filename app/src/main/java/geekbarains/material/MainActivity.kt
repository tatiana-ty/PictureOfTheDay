package geekbarains.material

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import geekbarains.material.view.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    companion object theme {
        var themeId: Int = R.style.AppTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themeId)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

     fun changeTheme(){
        when (themeId) {
            R.style.AppTheme -> themeId = R.style.PinkTheme
            R.style.PinkTheme -> themeId = R.style.AppTheme
        }
        recreate()
    }
}
