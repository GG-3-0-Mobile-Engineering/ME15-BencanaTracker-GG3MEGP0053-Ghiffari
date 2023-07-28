package com.example.bencanatracker.response

data class FloodGaugesResponse(
        val type: String,
        val properties: FloodGaugeProperties,
        val coordinates: List<Double>
    )

    data class FloodGaugeProperties(
        val gaugeid: String,
        val gaugenameid: String,
        val observations: List<Observation>
    )

    data class Observation(
        val f1: String,
        val f2: Int,
        val f3: Int,
        val f4: String
    )