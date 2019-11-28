package com.example.redenvelopes.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.redenvelopes.R
import com.example.redenvelopes.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private lateinit var list: Array<String>
    private lateinit var cIntent: Intent
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = resources.getStringArray(R.array.list)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        listview.adapter = adapter
        listview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                cIntent = Intent()
                when (position) {
                    0 -> {
                        startActivity(
                            cIntent.setClass(
                                this@MainActivity,
                                WechatEnvelopeActivity::class.java
                            )
                        )
                    }
                    1 -> {
                        startActivity(
                            cIntent.setClass(
                                this@MainActivity, GithubIssuesActivity::class.java
                            )
                        )

                    }
                }
            }

    }

}
