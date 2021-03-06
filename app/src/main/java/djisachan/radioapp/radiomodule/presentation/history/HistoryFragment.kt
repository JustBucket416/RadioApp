package djisachan.radioapp.radiomodule.presentation.history

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import djisachan.radioapp.MainSingleActivity
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.presentation.OnRadioClickListener
import djisachan.radioapp.radiomodule.presentation.radiolist.RadioListAdapter

/**
 * Фрагмент истории выбранных радиостанций
 * @author Markova Ekaterina on 10.08.2020
 */
@AndroidEntryPoint
class HistoryFragment : Fragment(),
    OnRadioClickListener {

    private val radioListAdapter =
        RadioListAdapter(
            this
        )
    private val historyViewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_radio_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = radioListAdapter
        historyViewModel.radioListData.observe(viewLifecycleOwner, Observer {
            radioListAdapter.setRadioListData(it)
        })
        historyViewModel.errorData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        historyViewModel.getHistoryList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_history -> {
                historyViewModel.clearHistory()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(radio: RadioModel) {
        (requireActivity() as MainSingleActivity).updateRadioPlayerFragment(radio)
    }

    private fun initToolbar() {
        with(requireActivity() as AppCompatActivity) {
            title = "История"
            setHasOptionsMenu(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}