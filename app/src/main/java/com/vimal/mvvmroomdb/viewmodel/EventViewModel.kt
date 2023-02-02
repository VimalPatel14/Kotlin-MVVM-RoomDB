package com.vimal.mvvmroomdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.vimal.mvvmroomdb.db.EventDatabase
import com.vimal.mvvmroomdb.model.Event
import com.vimal.mvvmroomdb.repo.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(
    application: Application
) : AndroidViewModel(application) {

    val allEvents: LiveData<List<Event>>
    val repository: EventRepository

    // initialize dao, repository and all events
    init {
        val dao = EventDatabase.getDatabase(application).getEventsDao()
        repository = EventRepository(dao)
        allEvents = repository.getAllEvents()
    }

    fun insertEvent(event: Event) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertEvent(event) }

    fun updateEvent(event: Event) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateEvent(event) }

    fun deleteEvent(event: Event) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteEvent(event) }

     fun deleteEventById(id: Int) =
         viewModelScope.launch(Dispatchers.IO) { repository.deleteEventById(id) }

    fun clearEvent() =
        viewModelScope.launch(Dispatchers.IO) { repository.clearEvent() }
}