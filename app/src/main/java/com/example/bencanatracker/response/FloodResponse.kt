package com.example.bencanatracker.response

import com.google.gson.annotations.SerializedName

data class FloodResponse(
    @SerializedName("statusCode")
    val statusCode: Int,

    @SerializedName("result")
    val result: Results
)

data class Results(
    @SerializedName("type")
    val type: String,

    @SerializedName("objects")
    val objects: Objectss,

    @SerializedName("arcs")
    val arcs: List<List<List<Double>>>,

    @SerializedName("transform")
    val transform: Transform,

    @SerializedName("bbox")
    val bbox: List<Double>
)

data class Objectss(
    @SerializedName("output")
    val output: Outputs
)

data class Outputs(
    @SerializedName("type")
    val type: String,

    @SerializedName("geometries")
    val geometries: List<Geometrys>
)

data class Geometrys(
    @SerializedName("type")
    val type: String,

    @SerializedName("properties")
    val properties: Properties,

    @SerializedName("arcs")
    val arcs: List<List<Int>>
)

data class Properties(
    @SerializedName("area_id")
    val areaId: String,

    @SerializedName("geom_id")
    val geomId: String,

    @SerializedName("area_name")
    val areaName: String,

    @SerializedName("parent_name")
    val parentName: String,

    @SerializedName("city_name")
    val cityName: String,

    @SerializedName("state")
    val state: Int,

    @SerializedName("last_updated")
    val lastUpdated: String
)

data class Transform(
    @SerializedName("scale")
    val scale: List<Double>,

    @SerializedName("translate")
    val translate: List<Double>
)
