package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.taskRecyclerView)
        emptyText = view.findViewById(R.id.emptyText)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        view.findViewById<View>(R.id.addTaskFab).setOnClickListener {
            findNavController().navigate(R.id.taskEditorFragment)
        }

        loadTasks()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        val dao = AppDatabase.getInstance(requireContext()).taskDao()
        val prefs = AppPrefs(requireContext())

        CoroutineScope(Dispatchers.IO).launch {
            val tasks = if (prefs.sortMode == "title") {
                dao.getAllByTitle()
            } else {
                dao.getAll()
            }

            val uiTasks = tasks.map {
                Task(it.id, it.title, it.description)
            }

            withContext(Dispatchers.Main) {
                if (uiTasks.isEmpty()) {
                    emptyText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    emptyText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                recyclerView.adapter = TaskAdapter(
                    uiTasks,
                    onClick = { task ->
                        val bundle = Bundle().apply {
                            putLong("taskId", task.id)
                        }
                        findNavController().navigate(R.id.taskEditorFragment, bundle)
                    },
                    onLongClick = { task ->
                        showDeleteDialog(task.id)
                    }
                )
            }
        }
    }

    private fun showDeleteDialog(taskId: Long) {
        AlertDialog.Builder(requireContext())
            .setTitle("Ștergere task")
            .setMessage("Ești sigur că vrei să ștergi acest task?")
            .setPositiveButton("Șterge") { _, _ ->
                val dao = AppDatabase.getInstance(requireContext()).taskDao()

                CoroutineScope(Dispatchers.IO).launch {
                    val entity = dao.getById(taskId) ?: return@launch
                    dao.delete(entity)

                    withContext(Dispatchers.Main) {
                        loadTasks()
                    }
                }
            }
            .setNegativeButton("Anulează", null)
            .show()
    }
}
