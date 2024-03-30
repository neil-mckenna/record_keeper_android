package com.neilmck.recordkeeper

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.neilmck.recordkeeper.cycling.CyclingFragment
import com.neilmck.recordkeeper.databinding.ActivityMainBinding
import com.neilmck.recordkeeper.running.RunningFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var bd: ActivityMainBinding
    private lateinit var ft: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        bd.bottomNav.setOnItemSelectedListener(this)

    }

    // create a menu with 3 ...
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val menuClickedHandled = when(item.itemId){
            R.id.reset_running -> {
                showConformationDialog(M_RUNNING_DISPLAY_VALUE)
                true
            }
            R.id.reset_cycling -> {
                showConformationDialog(M_CYCLING_DISPLAY_VALUE)
                true
            }
            R.id.reset_all -> {
                showConformationDialog(M_ALL_DISPLAY_VALUE)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }



        return menuClickedHandled
    }

    private fun showConformationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection Records")
            .setMessage("Are you sure you want to clear the records?")
            .setPositiveButton("Yes") { _, _ ->
                when (selection) {
                    M_ALL_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CyclingFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    M_RUNNING_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, Context.MODE_PRIVATE).edit { clear() }
                    }
                    M_CYCLING_DISPLAY_VALUE -> {
                        getSharedPreferences(CyclingFragment.FILENAME, Context.MODE_PRIVATE).edit { clear() }
                    }

                }

                refreshCurrentFragment()

                showConfirmation()

            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showConfirmation() {
        val snacky =
            Snackbar.make(bd.frameContent, "Records cleared successfully!", Snackbar.LENGTH_LONG)

        snacky.anchorView = bd.bottomNav
        snacky.setAction("Undo") {
            // restore records

        }
        snacky.show()
    }

    private fun refreshCurrentFragment() {
        when (bd.bottomNav.selectedItemId) {
            R.id.nav_running -> onRunningClicked()
            R.id.nav_cycling -> onCyclingClicked()
            else -> {}
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.nav_cycling -> onCyclingClicked()
        R.id.nav_running -> onRunningClicked()
        else -> false
    }

    private fun onCyclingClicked(): Boolean {
        println("$M_CYCLING_DISPLAY_VALUE CLicked")
        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_content, CyclingFragment())
        ft.commit()

        return true
    }

    private fun onRunningClicked(): Boolean {
        println("$M_RUNNING_DISPLAY_VALUE Clicked")
        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_content, RunningFragment())
        ft.commit()

        return true
    }

    companion object {

        const val  M_RUNNING_DISPLAY_VALUE: String = "running"
        const val M_CYCLING_DISPLAY_VALUE: String = "cycling"
        const val M_ALL_DISPLAY_VALUE: String = "all"

    }




}






