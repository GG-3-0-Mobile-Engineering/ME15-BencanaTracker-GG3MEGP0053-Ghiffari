package com.example.bencanatracker.response

import com.google.gson.annotations.SerializedName

data class ReportsResponse(
    val statusCode: Int, // Assuming this field is also part of the response
    val result: Result
)

data class Result(
    val type: String,
    val objects: Objects
)

data class Objects(
    val output: Output
)

data class Output(
    val type: String,
    val geometries: List<Geometry>
)

data class Geometry(
    val type: String,
    val properties: ReportProperties,
    val coordinates: List<Double>
)

data class ReportProperties(
    val pkey: String,
    @SerializedName("created_at") val createdAt: String,
    val source: String,
    val status: String,
    val url: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("disaster_type") val disasterType: String,
    @SerializedName("report_data") val reportData: ReportData,
    val tags: Tags,
    val title: String?,
    val text: String?,
    @SerializedName("partner_code") val partnerCode: String?,
    @SerializedName("partner_icon") val partnerIcon: String?
)

data class ReportData(
    @SerializedName("report_type") val reportType: String,
    @SerializedName("flood_depth") val floodDepth: Int
)

data class Tags(
    @SerializedName("district_id") val districtId: String?,
    @SerializedName("region_code") val regionCode: String,
    @SerializedName("local_area_id") val localAreaId: String?,
    @SerializedName("instance_region_code") val instanceRegionCode: String
)
