package com.rohit.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var recyclerView: RecyclerView
    private lateinit var madapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycleViewBeta)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        madapter = NewsListAdapter(this)
        recyclerView.adapter = madapter
    }

    private fun fetchData() {
        // https://newsapi.org/v2/everything?q=tesla&from=2023-02-25&sortBy=publishedAt&apiKey=a181cdbf7f9040a293314df9465fe432
        val url = "https://newsdata.io/api/1/news?apikey=pub_19450325b7fb9ec473a5f0494017f4f52de75&country=in,ca"
        val jsonObjectRequest = JsonObjectRequest (
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("results")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        //newsJsonObject.getString("author"),
                        newsJsonObject.getString("link"),
                        //newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                madapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "errorrr", Toast.LENGTH_LONG).show()
            }
        )

        MySingleton.getInstance(this).addToRequestQueue((jsonObjectRequest))
    }

    override fun onItemClicked (item: News) {
    }
}
