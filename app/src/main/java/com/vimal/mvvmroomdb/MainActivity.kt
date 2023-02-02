package com.vimal.mvvmroomdb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vimal.mvvmroomdb.activity.AddEventActivity
import com.vimal.mvvmroomdb.adapter.EventAdapter
import com.vimal.mvvmroomdb.databinding.ActivityMainBinding
import com.vimal.mvvmroomdb.interfaces.EventClickInterface
import com.vimal.mvvmroomdb.interfaces.EventDeleteIconClickInterface
import com.vimal.mvvmroomdb.model.Event
import com.vimal.mvvmroomdb.viewmodel.EventViewModel

class MainActivity : AppCompatActivity(), EventClickInterface, EventDeleteIconClickInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileName = intent.getStringExtra("Username")

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(EventViewModel::class.java)

        eventAdapter = EventAdapter(this, this)

        initView()
        observeEvents()
    }

    private fun initView() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        binding.eventsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = eventAdapter
        }
    }

    private fun observeEvents() {
        viewModel.allEvents.observe(this, Observer { list ->
            list?.let {
                // updates the list.
                eventAdapter.updateList(it)
            }
        })
    }

    private fun clearEvent() {
        val dialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        dialog.setTitle("Clear Event")
            .setMessage("Are you sure, you want to delete all event?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.clearEvent().also {
                    Toast.makeText(this, "Event Deleted", Toast.LENGTH_LONG).show()
                }
            }.setNegativeButton(android.R.string.cancel, null).create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearEventItem -> clearEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEventDeleteIconClick(event: Event) {
        val dialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
        dialog.setTitle("Delete Event")
            .setMessage("Are you sure, you want to delete this event?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.deleteEvent(event)
                Toast.makeText(this, "Event Deleted", Toast.LENGTH_LONG).show()
            }.setNegativeButton(android.R.string.cancel, null).create().show()


    }

    override fun onEventClick(event: Event) {
        // opening a new intent and passing a data to it.
        val intent = Intent(this@MainActivity, AddEventActivity::class.java)
        intent.putExtra("eventType", "Edit")
        intent.putExtra("eventTitle", event.title)
        intent.putExtra("eventDescription", event.description)
        intent.putExtra("eventId", event.id)
        startActivity(intent)
        this.finish()
    }
}