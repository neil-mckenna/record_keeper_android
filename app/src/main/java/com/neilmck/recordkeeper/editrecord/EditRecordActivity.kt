package com.neilmck.recordkeeper.editrecord

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.neilmck.recordkeeper.databinding.ActivityEditRecordBinding
import java.io.Serializable


class EditRecordActivity : AppCompatActivity() {

    private lateinit var arb: ActivityEditRecordBinding
    private val screenData: ScreenData by lazy {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getSerializable(SCREEN_DATA, ScreenData::class.java) as ScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.extras?.getSerializable(SCREEN_DATA) as ScreenData
        }
    }
    private val recordPreferences by lazy { getSharedPreferences(screenData.sharedPrefsName, Context.MODE_PRIVATE) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        arb = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(arb.root)

        setupUI()
        displayRecord()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        title = "Title: ${screenData.record}"
        arb.textInputRecord.hint = screenData.recordFieldHint
        // debug toast
        /*Toast.makeText(this,
            "${screenData.record} / ${screenData.recordFieldHint} / ${screenData.sharedPrefsName}",
            Toast.LENGTH_LONG).show()
        */

        // added this feature for better user experience, running get a time, cycling get a a text field
        when(screenData.sharedPrefsName) {
            "cycling" -> { arb.editTextRecord.setInputType(InputType.TYPE_CLASS_TEXT) }
            else -> { arb.editTextRecord.setInputType(InputType.TYPE_CLASS_DATETIME)}
        }

        arb.buttonSave.setOnClickListener {
            saveRecord()
            finish()
        }

        arb.buttonDelete.setOnClickListener {
            clearRecord()
            finish()
        }
    }



    private fun displayRecord()
    {
        arb.editTextRecord.setText(recordPreferences.getString("${screenData.record} $SHARED_PREFERENCE_RECORD_KEY", ""))
        arb.editTextDate.setText(recordPreferences.getString("${screenData.record} $SHARED_PREFERENCE_DATE_KEY", ""))
    }

    private fun saveRecord()
    {
        val record = arb.editTextRecord.text.toString()
        val date = arb.editTextDate.text.toString()

        recordPreferences.edit {
            putString("${this@EditRecordActivity.screenData.record} $SHARED_PREFERENCE_RECORD_KEY", record)
            putString("${this@EditRecordActivity.screenData.record} $SHARED_PREFERENCE_DATE_KEY", date)
        }
    }

    private fun clearRecord()
    {
        recordPreferences.edit {
            remove("${screenData.record} $SHARED_PREFERENCE_RECORD_KEY")
            remove("${screenData.record} $SHARED_PREFERENCE_DATE_KEY")
        }
    }




    data class ScreenData(
        val record: String,
        val sharedPrefsName: String,
        val recordFieldHint: String
    ) : Serializable


    companion object {
        const val SCREEN_DATA: String = "screen_data"
        const val SHARED_PREFERENCE_RECORD_KEY  = "record"
        const val SHARED_PREFERENCE_DATE_KEY  = "date"
    }

}