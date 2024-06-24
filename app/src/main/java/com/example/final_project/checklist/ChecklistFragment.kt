package com.example.final_project.checklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.databinding.FragmentChecklistBinding

class ChecklistFragment : Fragment(R.layout.fragment_checklist) {

    private var _binding: FragmentChecklistBinding? = null
    private val binding get() = _binding!!
    private var checklistItems: List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getStringArrayList(ARG_CHECKLIST_ITEMS)?.let {
            checklistItems = it
        }
        displayChecklistItems()
        binding.goToMakeChecklistButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MakeChecklistFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun displayChecklistItems() {
        checklistItems?.forEach { item ->
            val checklistItemView = LayoutInflater.from(context).inflate(R.layout.item_checklist, binding.checklistContainer, false)
            val editText = checklistItemView.findViewById<EditText>(R.id.checklistItemEditText)
            editText.setText(item)
            binding.checklistContainer.addView(checklistItemView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CHECKLIST_ITEMS = "checklist_items"

        fun newInstance(checklistItems: List<String>): ChecklistFragment {
            val fragment = ChecklistFragment()
            val args = Bundle()
            args.putStringArrayList(ARG_CHECKLIST_ITEMS, ArrayList(checklistItems))
            fragment.arguments = args
            return fragment
        }
    }
}
