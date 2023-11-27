package com.example.sensorapp

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.io.IOException

class MainActivity : AppCompatActivity(), SensorEventListener{
    var sensor: Sensor? = null
    var sensorManager: SensorManager? = null
    lateinit var image: ImageView
    lateinit var background:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        image = findViewById(R.id.imageV)
        background = findViewById(R.id.back)
        image.visibility= View.INVISIBLE

        sensorManager=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //important fun
        try {

            if (event != null) {
                Log.d(TAG,"onSensorChanged:"+event.values[0])
            }
                if(event!!.values[0] <30){
                    //less than 30
                    image.visibility=View.INVISIBLE
                    background.setBackgroundColor(resources.getColor(R.color.black))
                }
                else{
                    //yellow screen
                    image.visibility= View.VISIBLE
                    background.setBackgroundColor(resources.getColor(R.color.white))
                }

        } catch (e:IOException){
            Log.d(TAG,"onSensorChanged: +${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}