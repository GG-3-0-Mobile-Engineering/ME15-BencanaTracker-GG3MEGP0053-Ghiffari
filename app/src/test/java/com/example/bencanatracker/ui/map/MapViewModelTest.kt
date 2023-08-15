package com.example.bencanatracker.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.bencanatracker.network.ApiService
import com.example.bencanatracker.response.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MapViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MapViewModel
    private lateinit var apiService: ApiService
    private lateinit var dispatcher: TestCoroutineDispatcher

    @Before
    fun setup() {
        apiService = mockk()
        dispatcher = TestCoroutineDispatcher()
        viewModel = MapViewModel(apiService, dispatcher)
    }

    @Test
    fun `fetchReportsAndAddMarkers with filters should update reportsList`() = runBlockingTest {
        // Arrange
        val selectedDisasterType = "flood"
        val selectedProvince = "ExampleProvince"
        val fakeReports = listOf(
            Geometry(
                type = "Point",
                properties = ReportProperties(
                    pkey = "322104",
                    disasterType = "flood",
                    tags = Tags(instanceRegionCode = "ID-JB", districtId = null, regionCode = "", localAreaId = ""),
                    reportData = ReportData(reportType = "flood", floodDepth = 134),
                    createdAt = "2023-08-15T18:07:59.507Z",
                    source = "grasp",
                    status = "confirmed",
                    url = "0e19ab5d-da28-4dec-9c08-c5b90d57b467",
                    imageUrl = "https://images.petabencana.id/0e19ab5d-da28-4dec-9c08-c5b90d57b467.jpg",
                    text = "# trainer indah ratna rosalena\nneina-banjir menenggelamkan seluruh sekolah di sekitar masjid",
                    partnerCode = null, // Set to null
                    partnerIcon = null, // Set to null
                    title = null // Set to null
                ),
                coordinates = listOf(106.8534199927, -6.5428980676)
            )
        )

        coEvery { apiService.getReports(disasterType = selectedDisasterType) } returns ReportsResponse(
            statusCode = 200,
            result = Result(
                type = "Topology",
                objects = Objects(
                    output = Output(
                        type = "GeometryCollection",
                        geometries = fakeReports
                    )
                )
            )
        )

        // Act
        viewModel.fetchReportsAndAddMarkers(selectedProvince, selectedDisasterType, true)

        // Assert
        val reportsList = viewModel.reportsList.getOrAwaitValue()
        assert(reportsList.size == 1) // Only one report with the selected disaster type
        assert(reportsList[0].properties.disasterType == selectedDisasterType)
    }
}
