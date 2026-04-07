package com.example.smarttrafficapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.smarttrafficapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var laneAdapter: LaneAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        laneAdapter = LaneAdapter(
            onEmergencyClick = { lane -> viewModel.triggerEmergency(lane) },
            onForceGreenClick = { lane -> viewModel.forceGreen(lane) }
        )
        binding.rvLanes.adapter = laneAdapter

        // Setup Stat Cards
        binding.cardLanes.tvStatLabel.text = "Lanes"
        binding.cardVehicles.tvStatLabel.text = "Vehicles"
        binding.cardCongestion.tvStatLabel.text = "Congestion"

        // Get junctionId passed from map
        val junctionId = arguments?.getInt("junctionId") ?: 1
        val junctionName = arguments?.getString("junctionName") ?: "Junction"

        binding.tvJunctionName.text = junctionName
        viewModel.loadJunction(junctionId)

        // Observe lanes
        viewModel.lanes.observe(viewLifecycleOwner) { lanes ->
            laneAdapter.submitList(lanes)
            
            // Update Stats
            binding.cardLanes.tvStatValue.text = lanes.size.toString()
            binding.cardVehicles.tvStatValue.text = lanes.sumOf { it.vehicleCount }.toString()
            
            val totalVehicles = lanes.sumOf { it.vehicleCount }
            val avgCongestion = if (totalVehicles < 20) "LOW" else if (totalVehicles < 50) "MED" else "HIGH"
            binding.cardCongestion.tvStatValue.text = avgCongestion
        }
        
        binding.btnToggleSimulation.setOnClickListener {
            if (viewModel.isSimulating.value == true) {
                viewModel.stopSimulation()
                binding.btnToggleSimulation.text = "Start Simulation"
            } else {
                viewModel.startSimulation()
                binding.btnToggleSimulation.text = "Stop Simulation"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
