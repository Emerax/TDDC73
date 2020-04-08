package com.example.lab_3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class GithubProjectsAdapter() : RecyclerView.Adapter<GithubProjectHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubProjectHolder {
        val projectCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_card, parent, false) as CardView
        return GithubProjectHolder(projectCardView)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: GithubProjectHolder, position: Int) {
    }
}