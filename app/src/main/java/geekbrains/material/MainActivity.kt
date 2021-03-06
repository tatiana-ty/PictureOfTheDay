package geekbrains.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import geekbrains.material.model.Constants
import geekbrains.material.view.NotesFragment
import geekbrains.material.view.PeopleInSpaceFragment
import geekbrains.material.view.PictureOfTheDayFragment
import geekbrains.material.view.SettingsFragment
import kotlinx.android.synthetic.main.activity_api_bottom.*

class MainActivity : AppCompatActivity() {

//    companion object theme {
//        var themeId: Int = R.style.AppTheme
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(
            when (Constants.sPrefs?.retrieveInt(Constants.TAG_THEME, Constants.APP_THEME)) {
                0 -> R.style.AppTheme
                1 -> R.style.PinkTheme
                else -> R.style.AppTheme
            }
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_bottom)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_pod -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out
                        )
                        .replace(R.id.activity_api_bottom_container, PictureOfTheDayFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_people -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out
                        )
                        .replace(R.id.activity_api_bottom_container, PeopleInSpaceFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_notes -> {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in,
                                    R.anim.fade_out
                            )
                            .replace(R.id.activity_api_bottom_container, NotesFragment())
                            .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_settings -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out
                        )
                        .replace(R.id.activity_api_bottom_container, SettingsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out
                        )
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
                R.id.bottom_view_notes -> {
                    //Item tapped
                }
                R.id.bottom_view_settings -> {
                    //Item tapped
                }
            }
        }
    }

//    fun changeTheme() {
//        bottom_navigation_view.selectedItemId = R.id.bottom_view_pod
//        when (themeId) {
//            R.style.AppTheme -> themeId = R.style.PinkTheme
//            R.style.PinkTheme -> themeId = R.style.AppTheme
//        }
//        recreate()
//    }
}
