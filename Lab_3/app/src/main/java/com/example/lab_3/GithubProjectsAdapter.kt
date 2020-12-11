package com.example.lab_3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.FetchTrendingQuery

class GithubProjectsAdapter(val projects: List<FetchTrendingQuery.AsRepository>) : RecyclerView.Adapter<GithubProjectHolder>() {
    var onEndOfList: (() -> Unit)? = null
    var onItemClicked: ((FetchTrendingQuery.AsRepository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubProjectHolder {
        val projectCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_card, parent, false) as CardView
        return GithubProjectHolder(projectCardView)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: GithubProjectHolder, position: Int) {
        val project = projects[position]
        holder.title.text = project.name
        holder.url.text = project.nameWithOwner
        holder.description.text = project.description
        holder.forks.text = "Forks: ${project.forkCount}"
        holder.stars.text = "Stars: ${project.stargazerCount}"

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(project)
        }

        if (position == projects.size - 2) {
            onEndOfList?.invoke()
        }
    }
}