package com.vimal.mvvmroomdb.interfaces

import com.vimal.mvvmroomdb.model.Event

interface EventDeleteIconClickInterface {
    // creating a method for click
    // action on delete image view.
    fun onEventDeleteIconClick(event: Event)
}