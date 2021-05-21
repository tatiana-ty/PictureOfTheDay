package geekbrains.material.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import geekbrains.material.R
import geekbrains.material.model.PeopleInSpaceData
import geekbrains.material.viewmodel.PeopleInSpaceViewModel


class PeopleInSpaceFragment : Fragment() {

    private lateinit var peopleNumber: TextView
    private lateinit var peopleList: RecyclerView

    private val viewModel: PeopleInSpaceViewModel by lazy {
        ViewModelProviders.of(this).get(PeopleInSpaceViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, Observer<PeopleInSpaceData> { renderData(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.people_in_space_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peopleNumber = view.findViewById(R.id.people_in_space_number)
        peopleList = view.findViewById(R.id.people_in_space_list)
    }

    private fun renderData(data: PeopleInSpaceData) {
        when (data) {
            is PeopleInSpaceData.Success -> {
                val serverResponseData = data.serverResponseData
                peopleNumber.text = serverResponseData.number.toString()
                //showSuccess()
                var list = serverResponseData.people
                val namesList = mutableListOf<String>()
                if (list != null) {
                    for (item in list) {
                        namesList.add(item.name!!)
                    }
                }
                println(namesList)
                val adapter = PeopleInSpaceAdapter(namesList)
                peopleList.layoutManager = LinearLayoutManager(activity!!.baseContext)
                peopleList.adapter = adapter
            }
            is PeopleInSpaceData.Loading -> {
                //showLoading()
            }
            is PeopleInSpaceData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}