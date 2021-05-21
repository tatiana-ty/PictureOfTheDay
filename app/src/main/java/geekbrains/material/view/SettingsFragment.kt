package geekbrains.material.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import geekbrains.material.MainActivity
import geekbrains.material.R
import geekbrains.material.model.Constants
import kotlinx.android.synthetic.main.activity_api_bottom.*

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button = view.findViewById(R.id.change_theme)
        button.setOnClickListener {
            Constants.sPrefs?.storeInt(Constants.TAG_THEME, Constants.PINK_THEME)
            activity!!.recreate()
        }
    }
}
