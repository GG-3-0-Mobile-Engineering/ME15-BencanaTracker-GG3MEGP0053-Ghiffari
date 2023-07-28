package com.example.bencanatracker.ui.flood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bencanatracker.databinding.FragmentFloodBinding
import com.google.android.libraries.places.widget.AutocompleteSupportFragment

class FloodFragment : Fragment() {

    private var _binding: FragmentFloodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val FloodViewModel = ViewModelProvider(this).get(FloodViewModel::class.java)

        _binding = FragmentFloodBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFlood
        FloodViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}