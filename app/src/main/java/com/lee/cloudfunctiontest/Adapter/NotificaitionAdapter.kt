package com.lee.cloudfunctiontest.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lee.cloudfunctiontest.DTOs.NotificationDTO
import com.lee.cloudfunctiontest.R

class NotificationsAdapter(private val dataSet: ArrayList<NotificationDTO>) :
    RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val dateView: TextView

        init {
            textView = view.findViewById(R.id.notification_text)
            imageView = view.findViewById(R.id.notification_icon)
            dateView = view.findViewById(R.id.notification_time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.notification_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].notice_content
        viewHolder.dateView.text = dataSet[position].time
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}