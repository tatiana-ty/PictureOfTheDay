package geekbrains.material.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import geekbrains.material.R
import geekbrains.material.model.PictureOfTheDayData
import geekbrains.material.viewmodel.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.picture_of_the_day_fragment.*

class PictureOfTheDayFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var day1Chip: Chip
    private lateinit var day2Chip: Chip
    private lateinit var day3Chip: Chip
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var youtubePlayer: WebView
    private var day = 1
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData(day)
                .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.picture_of_the_day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.bottom_sheet_description_header)
        description = view.findViewById(R.id.bottom_sheet_description)
        youtubePlayer = view.findViewById(R.id.youtube_player)
        day1Chip = view.findViewById(R.id.day_1)
        day1Chip.setOnClickListener({
            day = 1
            viewModel.getData(day)
        })
        day2Chip = view.findViewById(R.id.day_2)
        day2Chip.setOnClickListener({
            day = 2
            viewModel.getData(day)
        })
        day3Chip = view.findViewById(R.id.day_3)
        day3Chip.setOnClickListener({
            day = 3
            viewModel.getData(day)
        })
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    title.text = serverResponseData.title
                    description.text = serverResponseData.explanation
                    if (serverResponseData.mediaType.equals("video")) {
                        image_view.visibility = View.INVISIBLE
                        youtubePlayer.visibility = View.VISIBLE
                        youtubePlayer.settings.javaScriptEnabled = true
                        println(serverResponseData.url)
                        youtubePlayer.loadUrl(serverResponseData.url)
                    } else {
                        image_view.visibility = View.VISIBLE
                        youtubePlayer.visibility = View.GONE
                        image_view.load(url) {
                            lifecycle(this@PictureOfTheDayFragment)
                            error(R.drawable.ic_load_error_vector)
                            placeholder(R.drawable.ic_no_photo_vector)
                        }
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}
