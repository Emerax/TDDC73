package com.example.lab_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.FetchTrendingQuery
import org.w3c.dom.Text

class ProjectDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)

        val name = intent.getStringExtra("project_name")
        val url = intent.getStringExtra("project_url")
        val desc = intent.getStringExtra("project_description")
        val forkCount = intent.getIntExtra("project_forks", -1)
        val starCount = intent.getIntExtra("project_stargazers", -1)
        val license = intent.getStringExtra("project_license")

        findViewById<TextView>(R.id.details_title).apply {
            text = name
        }
        findViewById<TextView>(R.id.details_url_text).apply {
            text = url
        }
        findViewById<TextView>(R.id.details_description).apply {
            text = desc
        }
        findViewById<TextView>(R.id.details_license_text).apply {
            text = license
        }
        findViewById<TextView>(R.id.details_forks_text).apply {
            text = forkCount.toString()
        }
        findViewById<TextView>(R.id.details_stars_text).apply {
            text = starCount.toString()
        }
    }
}