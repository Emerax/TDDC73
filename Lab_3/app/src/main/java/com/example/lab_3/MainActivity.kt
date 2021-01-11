package com.example.lab_3

import android.content.Context
import android.content.Intent
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
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.lab_3.databinding.ActivityMainBinding
import com.github.FetchTrendingQuery
import kotlinx.coroutines.channels.Channel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: GithubProjectsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var apolloClient: ApolloClient
    private val channel: Channel<Unit> = Channel(Channel.CONFLATED)
    private val projects = mutableListOf<FetchTrendingQuery.AsRepository>()
    private lateinit var selectedLanguage: String

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

        selectedLanguage = langSelector.selectedItem.toString()

        apolloClient = createApolloClient(context = applicationContext)

        viewManager = LinearLayoutManager(this)
        viewAdapter = GithubProjectsAdapter(projects)

        viewAdapter.onItemClicked = { project ->
            val intent = Intent(this, ProjectDetails::class.java).apply {
                putExtra("project_description", project.description)
                putExtra("project_forks", project.forkCount)
                putExtra("project_name", project.name)
                putExtra("project_url", project.nameWithOwner)
                putExtra("project_stargazers", project.stargazerCount)
                putExtra("project_license", project.licenseInfo?.name)
            }
            startActivity(intent)
        }

        lifecycleScope.launchWhenResumed {
            var cursor: String? = null
            for (item in channel) {
                val searchResultData = try {
                    //FIX: sort:stars sorterar efter stjärnor, ingenstans står detta i Githubs dokumentation dock...
                    apolloClient.query(FetchTrendingQuery("language:${selectedLanguage} sort:stars", cursor = Input.fromNullable(cursor))).await().data
                } catch (e: ApolloException) {
                    Log.d("GITHUB_QUERY", "Failure", e)
                    return@launchWhenResumed
                }
                Log.d("GITHUB_QUERY", "Success $searchResultData")

                val newProjects = (searchResultData as FetchTrendingQuery.Data).search.edges?.mapNotNull { it?.node?.asRepository }
                if (newProjects != null) {
                    projects.addAll(newProjects)
                    viewAdapter.notifyDataSetChanged()
                }

                cursor = searchResultData.search.edges?.last()?.cursor;
                if (cursor == null) {
                    break
                }
            }

            viewAdapter.onEndOfList = null;
            channel.close()
        }

        channel.offer(Unit)
        viewAdapter.onEndOfList = {
            channel.offer(Unit)
        }

        recyclerView = binding.mainRecycler.apply {
            setHasFixedSize(true);
            layoutManager = viewManager;
            adapter = viewAdapter
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        projects.clear()
        selectedLanguage = p0?.getItemAtPosition(p2).toString()
        viewAdapter.notifyDataSetChanged()
        channel.offer(Unit)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun createApolloClient(context: Context): ApolloClient {
        return  ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(context))
                .build()
            )
            .build()
    }

    private class AuthorizationInterceptor(val context: Context): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", context.getString(R.string.github_oauth))
                .build()
            return chain.proceed(request)
        }
    }
}
