package com.neilmck.recordkeeper.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neilmck.recordkeeper.databinding.FragmentCyclingBinding
import com.neilmck.recordkeeper.editrecord.EditRecordActivity

class CyclingFragment : Fragment() {

    private lateinit var fcb: FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fcb = FragmentCyclingBinding.inflate(inflater, container, false)

        return fcb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun setupClickListeners() {
        fcb.containerLongestRide.setOnClickListener{ launchRunningRecordScreen("Longest Ride", "Distance") }
        fcb.containerBiggestClimb.setOnClickListener{ launchRunningRecordScreen("Biggest Climb", "Height") }
        fcb.containerBestAverageSpd.setOnClickListener { launchRunningRecordScreen("Best Average Speed", "Average Speed") }
    }

    private fun displayRecords()
    {
        val cyclingPrefs = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        fcb.textViewLongestRideValue.text = cyclingPrefs.getString("Longest Ride ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        fcb.textViewLongestRideDate.text = cyclingPrefs.getString("Longest Ride ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

        fcb.textViewBiggestClimbValue.text = cyclingPrefs.getString("Biggest Climb ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        fcb.textViewBiggestClimbDate.text = cyclingPrefs.getString("Biggest Climb ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

        fcb.textViewBestAverageSpdValue.text = cyclingPrefs.getString("Best Average Speed ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        fcb.textViewBestAverageSpdDate.text = cyclingPrefs.getString("Best Average Speed ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

    }

    private fun launchRunningRecordScreen(title: String, recordField: String) {
        val intent = Intent(context, EditRecordActivity::class.java)

        intent.putExtra(
            SCREEN_DATA, EditRecordActivity.ScreenData(
            title,
            FILENAME,
            recordField
        ))

        startActivity(intent)
    }

    companion object {

        const val FILENAME: String = "cycling"
        const val SCREEN_DATA: String = "screen_data"

    }


}