package com.example.final_project.checklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.databinding.FragmentMakeChecklistBinding

class MakeChecklistFragment : Fragment(R.layout.fragment_make_checklist) {

    private var _binding: FragmentMakeChecklistBinding? = null
    private val binding get() = _binding!!
    private val checklistItems = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMakeChecklistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addChecklistButton.setOnClickListener {
            addNewChecklistItem()
        }

        binding.saveButton.setOnClickListener {
            saveChecklist()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ChecklistFragment.newInstance(checklistItems))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun addNewChecklistItem() {
        val newItem = LayoutInflater.from(context).inflate(R.layout.item_checklist, binding.checklistContainer, false)
        binding.checklistContainer.addView(newItem)
    }

    private fun saveChecklist() {
        checklistItems.clear()
        for (i in 0 until binding.checklistContainer.childCount) {
            val checklistView = binding.checklistContainer.getChildAt(i)
            val editText = checklistView.findViewById<EditText>(R.id.checklistItemEditText)
            checklistItems.add(editText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
