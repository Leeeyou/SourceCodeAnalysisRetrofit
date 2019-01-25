package com.leeeyou.source.code.analysis.retrofit

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        debugRetrofit()
    }

    private fun debugRetrofit() {
        btn_get.setOnClickListener {
            //step1:创建Retrofit实例
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            //step2:创建网络请求接口的代理对象，这里是gankService
            val gankService = retrofit.create(GankService::class.java)

            //step3:访问接口中的具体业务方法，得到一个适配器对象，这里的categoriesCall是Call<ResponseCategory>类型
            val categoriesCall = gankService.categories()

            //step4:通过categoriesCall发起网络请求，在Callback中解析数据并作相应的处理。
            categoriesCall.enqueue(object : Callback<ResponseCategory> {
                override fun onFailure(call: Call<ResponseCategory>, t: Throwable) {
                    Log.e("MainActivity", "访问失败", t)
                }

                override fun onResponse(call: Call<ResponseCategory>, response: Response<ResponseCategory>) {
                    val body = response.body()
                    body?.takeIf { result -> !result.isError }?.also { category ->
                        Toast.makeText(this@MainActivity, "访问成功 -> " + Gson().toJson(category), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
