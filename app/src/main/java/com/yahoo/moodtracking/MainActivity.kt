package com.yahoo.moodtracking

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.helper.StaticLabelsFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rate = findViewById<View>(R.id.rating_bar) as RatingBar
        val graphButton = findViewById<View>(R.id.graph_button) as Button
        val moodGraph = findViewById<GraphView>(R.id.graph) as GraphView
        val sunSaveButton = findViewById<View>(R.id.sun_save_button) as Button
        val monSaveButton = findViewById<View>(R.id.mon_save_button) as Button
        val tuesSaveButton = findViewById<View>(R.id.tues_save_button) as Button
        val wedSaveButton = findViewById<View>(R.id.wed_save_button) as Button
        val thursSaveButton = findViewById<View>(R.id.thurs_save_button) as Button
        val friSaveButton = findViewById<View>(R.id.fri_save_button) as Button
        val satSaveButton = findViewById<View>(R.id.sat_save_button) as Button

        val switchSun = findViewById<View>(R.id.switch1) as Switch
        val switchMon = findViewById<View>(R.id.switch2) as Switch
        val switchTues = findViewById<View>(R.id.switch3) as Switch
        val switchWed = findViewById<View>(R.id.switch4) as Switch
        val switchThurs = findViewById<View>(R.id.switch5) as Switch
        val switchFri = findViewById<View>(R.id.switch6) as Switch
        val switchSat = findViewById<View>(R.id.switch7) as Switch
        val sunText = findViewById<View>(R.id.sun_mood_text) as TextView
        val monText = findViewById<View>(R.id.mon_mood_text) as TextView
        val tuesText = findViewById<View>(R.id.tues_mood_text) as TextView
        val wedText = findViewById<View>(R.id.wed_mood_text) as TextView
        val thursText = findViewById<View>(R.id.thurs_mood_text) as TextView
        val friText = findViewById<View>(R.id.fri_mood_text) as TextView
        val satText = findViewById<View>(R.id.sat_mood_text) as TextView

        loadDataSun()
        loadDataMon()
        loadDataTues()
        loadDataWed()
        loadDataThurs()
        loadDataFri()
        loadDataSat()

        switchSun.visibility = View.INVISIBLE
        switchMon.visibility = View.INVISIBLE
        switchTues.visibility = View.INVISIBLE
        switchWed.visibility = View.INVISIBLE
        switchThurs.visibility = View.INVISIBLE
        switchFri.visibility = View.INVISIBLE
        switchSat.visibility = View.INVISIBLE


        sunSaveButton.setOnClickListener {
            saveDataSun(ratingBar = rating_bar)
        }
        monSaveButton.setOnClickListener {
            saveDataMon(ratingBar = rating_bar)
        }
        tuesSaveButton.setOnClickListener {
            saveDataTues(ratingBar = rating_bar)
        }
        wedSaveButton.setOnClickListener {
            saveDataWed(ratingBar = rating_bar)
        }
        thursSaveButton.setOnClickListener {
            saveDataThurs(ratingBar = rating_bar)
        }
        friSaveButton.setOnClickListener {
            saveDataFri(ratingBar = rating_bar)
        }
        satSaveButton.setOnClickListener {
            saveDataSat(ratingBar = rating_bar)
        }

        moodGraph.viewport.setMinX(0.0)
        moodGraph.viewport.setMaxX(6.0)
        moodGraph.viewport.setMinY(1.0)
        moodGraph.viewport.setMaxY(5.0)
        moodGraph.viewport.isYAxisBoundsManual = true
        moodGraph.viewport.isXAxisBoundsManual = true
        moodGraph.viewport.isScalable = true
        moodGraph.viewport.isScrollable = true;
        moodGraph.title = "Graph of Mood"
        moodGraph.titleTextSize = 60F;

        val gridLabel: GridLabelRenderer = moodGraph.gridLabelRenderer
        gridLabel.horizontalAxisTitle = "Days in a Week"
        gridLabel.verticalAxisTitle = "Mood Rating (Stars)"
        moodGraph.gridLabelRenderer.numHorizontalLabels = 7;
        moodGraph.gridLabelRenderer.textSize = 35F;
        moodGraph.gridLabelRenderer.setHumanRounding(false);
        gridLabel.setHorizontalLabelsAngle(90)

        val staticLabelsFormatter = StaticLabelsFormatter(moodGraph)
        staticLabelsFormatter.setHorizontalLabels(
            arrayOf(
                "Sun",
                "Mon",
                "Tues",
                "Wed",
                "Thur",
                "Fri",
                "Sat"
            )
        )
        staticLabelsFormatter.setVerticalLabels(
            arrayOf(
                "1",
                "2",
                "3",
                "4",
                "5"
            )
        )

        moodGraph.gridLabelRenderer.labelFormatter = staticLabelsFormatter;

        rate.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, rating, _ ->
            if (rating < 1.0f) ratingBar.rating = 1.0f
        }

        graphButton.setOnClickListener(View.OnClickListener {
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val sundayDP: Double = sharedPreferences.getString("STRING_KEY1", "")!!.toDouble()
            val mondayDP: Double = sharedPreferences.getString("STRING_KEY2", "")!!.toDouble()
            val tuesdayDP: Double = sharedPreferences.getString("STRING_KEY3", "")!!.toDouble()
            val wednesdayDP: Double = sharedPreferences.getString("STRING_KEY4", "")!!.toDouble()
            val thursdayDP: Double = sharedPreferences.getString("STRING_KEY5", "")!!.toDouble()
            val fridayDP: Double = sharedPreferences.getString("STRING_KEY6", "")!!.toDouble()
            val saturdayDP: Double = sharedPreferences.getString("STRING_KEY7", "")!!.toDouble()
            val series =
                LineGraphSeries(
                    arrayOf<DataPoint>(
                        DataPoint(0.0, sundayDP),
                        DataPoint(1.0, mondayDP),
                        DataPoint(2.0, tuesdayDP),
                        DataPoint(3.0, wednesdayDP),
                        DataPoint(4.0, thursdayDP),
                        DataPoint(5.0, fridayDP),
                        DataPoint(6.0, saturdayDP)
                    )
                )
            graph.addSeries(series)
        })
    }

    private fun saveDataSun(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        sun_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY1", insertedText)
            putBoolean("BOOLEAN_KEY1", switch1.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Sunday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataMon(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        mon_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY2", insertedText)
            putBoolean("BOOLEAN_KEY2", switch2.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Monday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataTues(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        tues_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY3", insertedText)
            putBoolean("BOOLEAN_KEY3", switch3.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Tuesday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataWed(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        wed_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY4", insertedText)
            putBoolean("BOOLEAN_KEY4", switch4.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Wednesday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataThurs(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        thurs_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY5", insertedText)
            putBoolean("BOOLEAN_KEY5", switch5.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Thursday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataFri(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        fri_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY6", insertedText)
            putBoolean("BOOLEAN_KEY6", switch6.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Friday", Toast.LENGTH_SHORT).show()
    }

    private fun saveDataSat(ratingBar: RatingBar) {
        val insertedText = ratingBar.rating.toString()
        sat_mood_text.text = insertedText

        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY7", insertedText)
            putBoolean("BOOLEAN_KEY7", switch7.isChecked)
        }.apply()

        Toast.makeText(this, "Data saved for Saturday", Toast.LENGTH_SHORT).show()
    }
    private fun loadDataSun() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY1", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY1", false)

        sun_mood_text.text = savedString
        switch1.isChecked = savedBoolean
    }

    private fun loadDataMon() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY2", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY2", false)

        mon_mood_text.text = savedString
        switch2.isChecked = savedBoolean
    }

    private fun loadDataTues() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY3", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY3", false)

        tues_mood_text.text = savedString
        switch3.isChecked = savedBoolean
    }

    private fun loadDataWed() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY4", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY4", false)

        wed_mood_text.text = savedString
        switch4.isChecked = savedBoolean
    }

    private fun loadDataThurs() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY5", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY5", false)

        thurs_mood_text.text = savedString
        switch5.isChecked = savedBoolean
    }

    private fun loadDataFri() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY6", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY6", false)

        fri_mood_text.text = savedString
        switch6.isChecked = savedBoolean
    }

    private fun loadDataSat() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY7", null)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY7", false)

        sat_mood_text.text = savedString
        switch7.isChecked = savedBoolean
    }
}
