package com.erkan.socketexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val BASE_URL: String = "****"
    private val socket = IO.socket("$BASE_URL:3000/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        socket.on("chat message", object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                runOnUiThread {
                    textViewMessagePanel.append(args?.get(0).toString() + "\n")
                }
            }

        })
        socket.connect()

        buttonSendMessage.setOnClickListener {
            sendMessage(editTextMessage.text.toString())
        }
    }

    fun sendMessage(message: String) {
        editTextMessage.text?.clear()
        socket.emit("chat message", message)
    }


}
