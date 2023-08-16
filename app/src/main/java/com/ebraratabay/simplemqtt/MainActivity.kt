package com.ebraratabay.simplemqtt

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.io.UnsupportedEncodingException
import javax.net.ssl.SSLSocketFactory


class MainActivity : AppCompatActivity() {

    val HOSTNAME="ssl://<HOSTNAME>.s1.eu.hivemq.cloud:8883"
    val USERNAME= "<USERNAME>"
    val PASSWORD= "<PASSWORD>"
    val locationtopic= "<TOPIC>"
    private lateinit var client:MqttAndroidClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clientId = MqttClient.generateClientId()
         client = MqttAndroidClient(this.applicationContext,HOSTNAME, clientId )

        val options = MqttConnectOptions()
        options.userName = USERNAME
        options.password = PASSWORD.toCharArray()
     //   options.setSocketFactory(SSLSocketFactory.getDefault());


        try {
            val token = client.connect(options)
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_LONG).show()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(this@MainActivity,"Unsuccess",Toast.LENGTH_LONG).show()

                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }
   public fun publish(view: View){
       val topic = locationtopic
       val message = "HELLO"
       try {
           client.publish(topic, message.toByteArray(),0,false)
       } catch (e: MqttException) {
           e.printStackTrace()
       }
   }
}