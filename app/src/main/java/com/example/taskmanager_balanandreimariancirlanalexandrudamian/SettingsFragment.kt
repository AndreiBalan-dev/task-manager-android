package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskmanager_balanandreimariancirlanalexandrudamian.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val prefs = AppPrefs(requireContext())

        binding.nameInput.setText(prefs.username)

        when (prefs.sortMode) {
            "title" -> binding.sortTitle.isChecked = true
            else -> binding.sortNewest.isChecked = true
        }

        binding.saveSettingsButton.setOnClickListener {
            val name = binding.nameInput.text?.toString()?.trim().orEmpty()
            prefs.username = if (name.isBlank()) "Student" else name

            prefs.sortMode = if (binding.sortTitle.isChecked) "title" else "newest"

            Toast.makeText(requireContext(), "Setările au fost salvate!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
