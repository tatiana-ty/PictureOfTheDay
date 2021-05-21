package geekbrains.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import geekbrains.material.view.PeopleInSpaceFragment
import geekbrains.material.view.PictureOfTheDayFragment
import geekbrains.material.view.SettingsFragment
import kotlinx.android.synthetic.main.activity_api_bottom.*

class MainActivity : AppCompatActivity() {

    companion object theme {
        var themeId: Int = R.style.AppTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themeId)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_pod -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, PictureOfTheDayFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_people -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, PeopleInSpaceFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, SettingsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, PictureOfTheDayFragment())
                        .commitAllowingStateLoss()
                    true
                }
            }
        }
        bottom_navigation_view.selectedItemId = R.id.bottom_view_pod

        bottom_navigation_view.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_pod -> {
                    //Item tapped
                }
                R.id.bottom_view_people -> {
                    //Item tapped
                }
                R.id.bottom_view_settings -> {
                    //Item tapped
                }
            }
        }
    }

    fun changeTheme() {
        bottom_navigation_view.selectedItemId = R.id.bottom_view_pod
        when (themeId) {
            R.style.AppTheme -> themeId = R.style.PinkTheme
            R.style.PinkTheme -> themeId = R.style.AppTheme
        }
        recreate()
    }
}
