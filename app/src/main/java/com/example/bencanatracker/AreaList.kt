import com.google.android.gms.maps.model.LatLng

class AreaList {
    val ProvinceCode = mapOf(
        "ID-AC" to "Aceh",
        "ID-BA" to "Bali",
        "ID-BB" to "Kep Bangka Belitung",
        "ID-BT" to "Banten",
        "ID-BE" to "Bengkulu",
        "ID-JT" to "Jawa Tengah",
        "ID-KT" to "Kalimantan Tengah",
        "ID-ST" to "Sulawesi Tengah",
        "ID-JI" to "Jawa Timur",
        "ID-KI" to "Kalimantan Timur",
        "ID-NT" to "Nusa Tenggara Timur",
        "ID-GO" to "Gorontalo",
        "ID-JK" to "DKI Jakarta",
        "ID-JA" to "Jambi",
        "ID-LA" to "Lampung",
        "ID-MA" to "Maluku",
        "ID-KU" to "Kalimantan Utara",
        "ID-MU" to "Maluku Utara",
        "ID-SA" to "Sulawesi Utara",
        "ID-SU" to "Sumatera Utara",
        "ID-PA" to "Papua",
        "ID-RI" to "Riau",
        "ID-KR" to "Kepulauan Riau",
        "ID-SG" to "Sulawesi Tenggara",
        "ID-KS" to "Kalimantan Selatan",
        "ID-SN" to "Sulawesi Selatan",
        "ID-SS" to "Sumatera Selatan",
        "ID-YO" to "DIYogyakarta",
        "ID-JB" to "Jawa Barat",
        "ID-KB" to "Kalimantan Barat",
        "ID-NB" to "Nusa Tenggara Barat",
        "ID-PB" to "Papua Barat",
        "ID-SR" to "Sulawesi Barat",
        "ID-SB" to "Sumatera Barat"
    )

    fun getLatLngByProvinceName(provinceName: String): LatLng? {
        return ProvinceLatLong[provinceName]
    }


    val ProvinceLatLong = mapOf(
        "Aceh" to LatLng(4.6951, 96.7494),
        "Bali" to LatLng(-8.4550, 115.2500),
        "Kep Bangka Belitung" to LatLng(-2.7415, 106.4406),
        "Banten" to LatLng(-6.1894, 106.8323),
        "Bengkulu" to LatLng(-3.7916, 102.2561),
        "Jawa Tengah" to LatLng(-7.6145, 110.7123),
        "Kalimantan Tengah" to LatLng(-2.2500, 113.9167),
        "Sulawesi Tengah" to LatLng(-1.4300, 121.4456),
        "Jawa Timur" to LatLng(-7.2575, 112.7521),
        "Kalimantan Timur" to LatLng(-0.9461, 117.4755),
        "Nusa Tenggara Timur" to LatLng(-10.1718, 123.6065),
        "Gorontalo" to LatLng(0.6999, 122.4467),
        "DKI Jakarta" to LatLng(-6.2115, 106.8452),
        "Jambi" to LatLng(-1.4852, 102.4381),
        "Lampung" to LatLng(-5.4513, 105.2621),
        "Maluku" to LatLng(-3.2385, 130.1453),
        "Kalimantan Utara" to LatLng(3.0436, 116.1896),
        "Maluku Utara" to LatLng(2.3019, 128.0894),
        "Sulawesi Utara" to LatLng(1.2086, 124.5360),
        "Sumatera Utara" to LatLng(2.1158, 99.5451),
        "Papua" to LatLng(-2.5489, 140.7210),
        "Riau" to LatLng(0.2933, 101.7068),
        "Kepulauan Riau" to LatLng(3.9456, 108.1429),
        "Sulawesi Tenggara" to LatLng(-5.2088, 120.4783),
        "Kalimantan Selatan" to LatLng(-3.9849, 115.2838),
        "Sulawesi Selatan" to LatLng(-4.6796, 120.3844),
        "Sumatera Selatan" to LatLng(-3.3194, 103.9144),
        "DIYogyakarta" to LatLng(-7.8754, 110.4262),
        "Jawa Barat" to LatLng(-6.9148, 107.6085),
        "Kalimantan Barat" to LatLng(0.2587, 111.4747),
        "Nusa Tenggara Barat" to LatLng(-8.6529, 117.3616),
        "Papua Barat" to LatLng(-1.3361, 133.1747),
        "Sulawesi Barat" to LatLng(-2.8216, 119.1529),
        "Sumatera Barat" to LatLng(-0.9492, 100.3543)
    )
}