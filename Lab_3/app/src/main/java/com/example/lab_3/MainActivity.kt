package com.example.lab_3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.coroutines.await
import com.example.lab_3.databinding.ActivityMainBinding
import com.github.FetchTrendingQuery
import com.github.apolloClient

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val langSelector: Spinner = findViewById(R.id.langSelector)
        ArrayAdapter.createFromResource(this, R.array.programming_languages, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            langSelector.adapter = adapter
        }
        langSelector.onItemSelectedListener = this

        viewManager = LinearLayoutManager(this)
        viewAdapter = GithubProjectsAdapter()

        recyclerView = binding.mainRecycler.apply {
            setHasFixedSize(true);
            layoutManager = viewManager;
            adapter = viewAdapter
        }

        lifecycleScope.launchWhenResumed {
            val response = apolloClient(context = applicationContext).query(FetchTrendingQuery()).await()

            Log.d("QUERY", "Success ${response?.data}")
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("DEBUG", "Selected language ${p0?.getItemAtPosition(p2)}")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
