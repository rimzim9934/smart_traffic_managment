package com.example.smarttrafficapp.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrafficapp.R
import com.example.smarttrafficapp.databinding.ItemLaneBinding
import com.example.smarttrafficapp.domain.model.CongestionLevel
import com.example.smarttrafficapp.domain.model.Lane
import com.example.smarttrafficapp.domain.model.SignalState

class LaneAdapter(
    private val onEmergencyClick: (Lane) -> Unit,
    private val onForceGreenClick: (Lane) -> Unit
) : RecyclerView.Adapter<LaneAdapter.LaneViewHolder>() {

    private var lanes: List<Lane> = emptyList()

    fun submitList(newList: List<Lane>) {
        lanes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaneViewHolder {
        val binding = ItemLaneBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LaneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaneViewHolder, position: Int) {
        holder.bind(lanes[position], onEmergencyClick, onForceGreenClick)
    }

    override fun getItemCount(): Int = lanes.size

    class LaneViewHolder(private val binding: ItemLaneBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            lane: Lane,
            onEmergencyClick: (Lane) -> Unit,
            onForceGreenClick: (Lane) -> Unit
        ) {
            binding.tvLaneLabel.text = "Lane ${lane.laneNumber}"
            binding.tvLaneStatus.text = "${lane.vehicleCount} vehicles · ${getPhaseText(lane.signalState)}"
            binding.tvTimer.text = "${lane.timeRemaining}s"

            // Signal Light and Theme Color
            val colorRes = when (lane.signalState) {
                SignalState.GREEN -> R.color.green_primary
                SignalState.YELLOW -> R.color.yellow_primary
                SignalState.RED -> R.color.red_primary
            }
            val color = ContextCompat.getColor(itemView.context, colorRes)
            
            binding.flSignalContainer.background.setTint(color)
            binding.tvSignalShort.text = getSignalShortText(lane.signalState)
            binding.tvTimer.setTextColor(color)
            binding.viewProgress.setBackgroundColor(color)

            // Congestion Tag
            binding.tvCongestionTag.text = lane.congestionLevel.name.lowercase().replaceFirstChar { it.uppercase() }
            val tagBg = when (lane.congestionLevel) {
                CongestionLevel.LOW -> R.drawable.bg_congestion_tag_low
                CongestionLevel.MEDIUM -> R.drawable.bg_congestion_tag_med
                CongestionLevel.HIGH -> R.drawable.bg_congestion_tag_high
            }
            binding.tvCongestionTag.setBackgroundResource(tagBg)

            // Emergency State
            if (lane.isEmergency) {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red_light_bg))
                binding.btnEmergency.text = "Active"
                binding.btnEmergency.isEnabled = false
            } else {
                binding.root.setCardBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                binding.btnEmergency.text = "Emergency"
                binding.btnEmergency.isEnabled = true
            }

            binding.btnEmergency.setOnClickListener { onEmergencyClick(lane) }
            binding.btnForceGreen.setOnClickListener { onForceGreenClick(lane) }
        }

        private fun getPhaseText(state: SignalState): String = when (state) {
            SignalState.GREEN -> "Green phase"
            SignalState.YELLOW -> "Clearing phase"
            SignalState.RED -> "Waiting"
        }

        private fun getSignalShortText(state: SignalState): String = when (state) {
            SignalState.GREEN -> "GO"
            SignalState.YELLOW -> "SLW"
            SignalState.RED -> "STP"
        }
    }
}
