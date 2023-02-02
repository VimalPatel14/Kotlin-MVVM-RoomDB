package com.vimal.mvvmroomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimal.mvvmroomdb.databinding.EventRvItemBinding
import com.vimal.mvvmroomdb.interfaces.EventClickInterface
import com.vimal.mvvmroomdb.interfaces.EventDeleteIconClickInterface
import com.vimal.mvvmroomdb.model.Event

class EventAdapter(
    val eventDeleteIconClickInterface: EventDeleteIconClickInterface,
    val eventClickInterface: EventClickInterface
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val allEvents = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventRvItemBinding.inflate(inflater, parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        // sets data to item of recycler view.
        holder.binding.tvTitle.text = allEvents.get(position).title
        holder.binding.tvDescription.text = allEvents.get(position).description

        // adding click listener to our delete image view icon.
        holder.binding.imgDelete.setOnClickListener {
            // call eventDeleteIconClickInterface.onEventDeleteIconClick() and pass position to it.
            eventDeleteIconClickInterface.onEventDeleteIconClick(allEvents.get(position))
        }

        // adding click listener to our recycler view item.
        holder.itemView.setOnClickListener {
            // call eventClickInterface.onEventClick() and pass position to it.
            eventClickInterface.onEventClick(allEvents.get(position))
        }
    }

    override fun getItemCount(): Int {
        // return list size.
        return allEvents.size
    }

    // update the list of events.
    fun updateList(newList: List<Event>) {
        // clear the allEvents array list
        allEvents.clear()
        // adds a new list to our allEvents list.
        allEvents.addAll(newList)
        // call notifyDataSetChanged() to notify our adapter.
        notifyDataSetChanged()
    }

    class EventViewHolder(val binding: EventRvItemBinding) : RecyclerView.ViewHolder(binding.root) {}
}

