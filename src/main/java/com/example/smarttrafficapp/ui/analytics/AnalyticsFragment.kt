package com.example.smarttrafficapp.ui.analytics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.smarttrafficapp.databinding.FragmentAnalyticsBinding
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsFragment : Fragment() {

    private var _binding: FragmentAnalyticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AnalyticsViewModel by viewModels()

    // For demo, show junction 1 logs
    private val demoJunctionId = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLogs(demoJunctionId).observe(viewLifecycleOwner) { logs ->
            if (logs.isNullOrEmpty()) {
                showDemoCharts()
                return@observe
            }

            // Bar Chart — vehicle count per log entry
            val barEntries = logs.takeLast(10).mapIndexed { i, log ->
                BarEntry(i.toFloat(), log.vehicleCount.toFloat())
            }
            val barDataSet = BarDataSet(barEntries, "Vehicle Count").apply {
                colors = ColorTemplate.MATERIAL_COLORS.toList()
            }
            binding.barChart.apply {
                data = BarData(barDataSet)
                description.text = "Vehicle Count per Cycle"
                animateY(800)
                invalidate()
            }

            // Line Chart — trend
            val lineEntries = logs.takeLast(20).mapIndexed { i, log ->
                Entry(i.toFloat(), log.vehicleCount.toFloat())
            }
            val lineDataSet = LineDataSet(lineEntries, "Traffic Trend").apply {
                color = Color.parseColor("#2196F3")
                valueTextColor = Color.BLACK
                lineWidth = 2f
                setDrawCircles(true)
                circleRadius = 4f
            }
            binding.lineChart.apply {
                data = LineData(lineDataSet)
                description.text = "Traffic Trend Over Time"
                animateX(800)
                invalidate()
            }
        }
    }

    // Show random demo data if no simulation has run yet
    private fun showDemoCharts() {
        val barEntries = (0..5).map { i ->
            BarEntry(i.toFloat(), (10..50).random().toFloat())
        }
        val barDataSet = BarDataSet(barEntries, "Vehicle Count (Demo)").apply {
            colors = ColorTemplate.COLORFUL_COLORS.toList()
        }
        binding.barChart.apply {
            data = BarData(barDataSet)
            description.text = "Demo Data — Run Simulation to see live data"
            animateY(600)
            invalidate()
        }

        val lineEntries = (0..9).map { i ->
            Entry(i.toFloat(), (5..45).random().toFloat())
        }
        val lineDataSet = LineDataSet(lineEntries, "Traffic Trend (Demo)").apply {
            color = Color.parseColor("#4CAF50")
            lineWidth = 2f
            setDrawCircles(true)
        }
        binding.lineChart.apply {
            data = LineData(lineDataSet)
            description.text = "Demo Trend"
            animateX(600)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
