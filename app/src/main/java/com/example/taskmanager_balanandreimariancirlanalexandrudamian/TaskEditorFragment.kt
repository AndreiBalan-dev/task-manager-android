package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager_balanandreimariancirlanalexandrudamian.databinding.FragmentTaskEditorBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskEditorFragment : Fragment(R.layout.fragment_task_editor) {

    private var _binding: FragmentTaskEditorBinding? = null
    private val binding get() = _binding!!

    private var taskId: Long = -1L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTaskEditorBinding.bind(view)

        taskId = arguments?.getLong("taskId", -1L) ?: -1L

        requireActivity().title =
            if (taskId == -1L) getString(R.string.title_add_task) else getString(R.string.title_edit_task)

        binding.saveButton.isEnabled = false

        binding.titleInput.addTextChangedListener { text ->
            binding.saveButton.isEnabled = !text.isNullOrBlank()
        }

        val dao = AppDatabase.getInstance(requireContext()).taskDao()

        if (taskId != -1L) {
            CoroutineScope(Dispatchers.IO).launch {
                val entity = dao.getById(taskId)
                withContext(Dispatchers.Main) {
                    if (entity != null) {
                        binding.titleInput.setText(entity.title)
                        binding.descInput.setText(entity.description)
                        binding.saveButton.isEnabled = entity.title.isNotBlank()
                    }
                }
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleInput.text?.toString()?.trim().orEmpty()
            val desc = binding.descInput.text?.toString()?.trim().orEmpty()

            if (title.isBlank()) {
                Toast.makeText(requireContext(), getString(R.string.error_title_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                if (taskId == -1L) {
                    dao.insert(TaskEntity(title = title, description = desc))
                } else {
                    dao.update(TaskEntity(id = taskId, title = title, description = desc))
                }

                withContext(Dispatchers.Main) {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
