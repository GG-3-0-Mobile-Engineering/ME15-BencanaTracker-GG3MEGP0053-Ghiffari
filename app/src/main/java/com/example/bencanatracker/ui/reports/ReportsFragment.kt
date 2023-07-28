package com.example.bencanatracker.ui.reports

import ReportsAdapter
import ReportsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bencanatracker.databinding.FragmentReportsBinding

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!

    private lateinit var reportsAdapter: ReportsAdapter
    private lateinit var reportsViewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        reportsViewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)
        setupRecyclerView()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reportsViewModel.reportsLiveData.observe(viewLifecycleOwner) { reports ->
            // Create a list with the single ReportsResponse object
            val reportsList = listOf(reports)
            reportsAdapter.updateData(reportsList)
        }

        // Fetch reports data only if it hasn't been fetched already
        if (reportsViewModel.reportsLiveData.value == null) {
            reportsViewModel.fetchReportsData()
        }
    }


    private fun setupRecyclerView() {
        reportsAdapter = ReportsAdapter(emptyList())
        binding.recyclerViewReports.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
