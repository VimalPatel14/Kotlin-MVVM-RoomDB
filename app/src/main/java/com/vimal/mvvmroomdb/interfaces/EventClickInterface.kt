package com.vimal.mvvmroomdb.interfaces

import com.vimal.mvvmroomdb.model.Event

interface EventClickInterface {
    // creating a method for click action
    // on recycler view item for updating it.
    fun onEventClick(event: Event)
}