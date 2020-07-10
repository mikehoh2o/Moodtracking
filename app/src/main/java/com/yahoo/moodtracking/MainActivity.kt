package com.yahoo.moodtracking

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
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
        val submit = findViewById<View>(R.id.submit_button) as Button
        val graphButton = findViewById<View>(R.id.graph_button) as Button
        val moodGraph = findViewById<GraphView>(R.id.graph) as GraphView
        val mutableList:MutableList<Double> = mutableListOf()

        moodGraph.viewport.setMinX(0.0)
        moodGraph.viewport.setMaxX(7.0)
        moodGraph.viewport.setMinY(0.0)
        moodGraph.viewport.setMaxY(4.0)
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
                "Thurs",
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

        submit.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Your Rating: " + rate.rating.toString(), Toast.LENGTH_SHORT)
                .show()
//            val seriesArray =
//            val series =
//                LineGraphSeries(
//                    arrayOf<DataPoint>(
//                        DataPoint(0, rate.rating),
//                        DataPoint(1, rateValue),
//                        DataPoint(2, rateValue),
//                        DataPoint(3, rateValue),
//                        DataPoint(4, rateValue)
//                    )
//                )
//            graph.addSeries(series)
        })
        graphButton.setOnClickListener(View.OnClickListener {

        })
    }
}


