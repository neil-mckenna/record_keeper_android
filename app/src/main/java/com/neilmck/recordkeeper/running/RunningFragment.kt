package com.neilmck.recordkeeper.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neilmck.recordkeeper.databinding.FragmentRunningBinding
import com.neilmck.recordkeeper.editrecord.EditRecordActivity


class RunningFragment : Fragment() {

    private lateinit var rfb: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rfb = FragmentRunningBinding.inflate(inflater, container, false)
        return rfb.root

    }

    // once it is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }



    private fun setupClickListeners() {
        rfb.container5km.setOnClickListener { launchRunningRecordScreen("5km") }
        rfb.container10km.setOnClickListener { launchRunningRecordScreen("10km") }
        rfb.containerHalfMarathon.setOnClickListener { launchRunningRecordScreen("Half Marathon") }
        rfb.containerFullMarathon.setOnClickListener { launchRunningRecordScreen("Full Marathon") }

    }

    private fun displayRecords()
    {
        val runningPrefs = requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        rfb.textView5kmValue.text = runningPrefs.getString("5km ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        rfb.textView5kmDate.text = runningPrefs.getString("5km ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

        rfb.textView10kmValue.text = runningPrefs.getString("10km ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        rfb.textView10kmDate.text = runningPrefs.getString("10km ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

        rfb.textViewHalfMarathonValue.text = runningPrefs.getString("Half Marathon ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        rfb.textViewHalfMarathonDate.text = runningPrefs.getString("Half Marathon ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");

        rfb.textViewFullMarathonValue.text = runningPrefs.getString("Full Marathon ${EditRecordActivity.SHARED_PREFERENCE_RECORD_KEY}", "");
        rfb.textViewFullMarathonDate.text = runningPrefs.getString("Full Marathon ${EditRecordActivity.SHARED_PREFERENCE_DATE_KEY}", "");


    }

    private fun launchRunningRecordScreen(title: String) {
        val intent = Intent(context, EditRecordActivity::class.java)

        intent.putExtra(
            SCREEN_DATA, EditRecordActivity.ScreenData(
            title,
            FILENAME,
            "Time: "
        ))

        startActivity(intent)

    }

    companion object {

        const val FILENAME: String = "running"
        const val SCREEN_DATA: String = "screen_data"

    }


}