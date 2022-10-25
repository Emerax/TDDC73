package com.example.lab_3

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GithubProjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.ProjectTitle)
    val url: TextView = itemView.findViewById(R.id.ProjectURL)
    val description: TextView = itemView.findViewById(R.id.ProjectDescription)
    val stars: TextView = itemView.findViewById(R.id.ProjectStars)
    val forks: TextView = itemView.findViewById(R.id.ProjectForks)

}