package se.magictechnology.piaxapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okio.IOException

class Student (
    var name: String? = null,
    var address: String? = null) {
}

class Disneychar {
    var _id : Int? = null
    var name : String? = null
}

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val json1 = """
        { 
            "name": "Mark", 
            "address": "London"
         }"""

        val student = Gson().fromJson(json1, Student::class.java)

        Log.i("PIAXDEBUG", student.name!!)

        run()




    }


    fun run() {
        val request = Request.Builder()
            .url("https://api.disneyapi.dev/characters/37")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("PIAXDEBUG", "FAILURE")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("PIAXDEBUG", "RESPONSE")
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        Log.i("PIAXDEBUG","$name: $value")
                    }

                    val responstext = response.body!!.string()

                    Log.i("PIAXDEBUG", responstext)

                    val abu = Gson().fromJson(responstext, Disneychar::class.java)

                    Log.i("PIAXDEBUG", abu.name!!)

                }
            }
        })
    }

}