package com.example.bencanatracker.ui.map

import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bencanatracker.databinding.FragmentMapBinding
import com.example.bencanatracker.databinding.LayoutLoadingBinding
import com.example.bencanatracker.response.Geometry
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MapFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var sharedViewModel: SharedViewModel

    @RelaxedMockK
    lateinit var mapViewModel: MapViewModel

    @RelaxedMockK
    lateinit var binding: FragmentMapBinding

    @RelaxedMockK
    lateinit var supportMapFragment: SupportMapFragment

    @MockK
    lateinit var googleMap: GoogleMap

    @RelaxedMockK
    lateinit var marker: Marker

    private lateinit var mapFragment: MapFragment

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapFragment = MapFragment()

        mapFragment.sharedViewModel = sharedViewModel
        mapFragment.mapViewModel = mapViewModel
        mapFragment._binding = binding
        val context = InstrumentationRegistry.getInstrumentation().context
        val resourceId = context.resources.getIdentifier("someTextView", "id", context.packageName)
        val textViewMock: TextView = mockk(relaxed = true)
        every { textViewMock.text } returns "Mocked Text"
        every { binding.root.findViewById<TextView>(resourceId) } returns textViewMock
        every { supportMapFragment.getMapAsync(mapFragment) } just Runs
        every { binding.filterButton.setOnClickListener(any()) } just Runs
        every { binding.resetButton.setOnClickListener(any()) } just Runs
        every { googleMap.addMarker(any()) } returns marker

        // Mock the behavior of LiveData in SharedViewModel
        every { sharedViewModel.selectedProvince } returns MutableLiveData("")
        every { sharedViewModel.searchQuery } returns MutableLiveData("")
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test onMapReady`() {
        // Arrange
        mapFragment.mGoogleMap = googleMap
        val loadingLayoutCaptureSlot = slot<Int>()
        val loadingLayoutBinding: LayoutLoadingBinding = mockk(relaxed = true)

        every { binding.loadingLayout1 } returns loadingLayoutBinding
        every { loadingLayoutBinding.root.setVisibility(capture(loadingLayoutCaptureSlot)) } just Runs

        // Act
        mapFragment.onMapReady(googleMap)

        // Assert
        verify(exactly = 1) { mapViewModel.fetchReportsAndAddMarkers() }
        verify(exactly = 1) { googleMap.uiSettings.isZoomControlsEnabled = true }
        verify(exactly = 1) { loadingLayoutBinding.root.setVisibility(View.VISIBLE) }
        assertEquals(View.VISIBLE, loadingLayoutCaptureSlot.captured)
    }





    @Test
    fun `test applyFilter`() {
        // Arrange
        every { mapViewModel.fetchReportsAndAddMarkers(any(), any(), any()) } just Runs

        // Act
        mapFragment.applyFilter()

        // Assert
        verify(exactly = 1) { mapViewModel.fetchReportsAndAddMarkers(any(), any(), true) }
    }

    @Test
    fun `test setupAutocompleteSearch`() {
        // Arrange
        val adapter: ArrayAdapter<String> = mockk(relaxed = true)
        every { binding.searchBar.threshold } returns 1
        every { binding.searchBar.setAdapter(adapter) } just Runs
        every { binding.searchBar.setOnItemClickListener(any()) } just Runs

        // Act
        mapFragment.setupAutocompleteSearch()

        // Assert
        verify(exactly = 1) { binding.searchBar.threshold = 1 }
        verify(exactly = 1) { binding.searchBar.setAdapter(adapter) }
    }

    @Test
    fun `test zoomToLocation`() {
        // Arrange
        val location = LatLng(0.0, 0.0)
        val cameraUpdate: CameraUpdate = mockk(relaxed = true)
        every { CameraUpdateFactory.newLatLngZoom(any(), any()) } returns cameraUpdate
        mapFragment.mGoogleMap = googleMap

        // Act
        mapFragment.zoomToLocation(location)

        // Assert
        verify(exactly = 1) { googleMap.moveCamera(cameraUpdate) }
    }

    @Test
    fun `test addMarkersFromApi`() {
        // Arrange
        val reportsList: List<Geometry> = mockk()
        val boundsBuilder: LatLngBounds.Builder = mockk(relaxed = true)
        val bounds: LatLngBounds = mockk(relaxed = true)
        every { LatLngBounds.Builder() } returns boundsBuilder
        every { boundsBuilder.include(any()) } returns boundsBuilder
        every { boundsBuilder.build() } returns bounds
        every { mapViewModel.fetchReportsAndAddMarkers() } just Runs
        every { googleMap.clear() } just Runs
        every { googleMap.addMarker(any()) } returns marker

        // Act
        mapFragment.addMarkersFromApi(reportsList)

        // Assert
        verify(exactly = 1) { mapViewModel.fetchReportsAndAddMarkers() }
        verify(exactly = 1) { googleMap.clear() }
        verify(exactly = 1) { boundsBuilder.include(marker.position) }
        verify(exactly = 1) { googleMap.moveCamera(any()) }
    }

    @Test
    fun `test resetMap`() {
        // Arrange
        every { mapViewModel.fetchReportsAndAddMarkers() } just Runs
        every { sharedViewModel.setSearchQuery(any()) } just Runs
        every { sharedViewModel.setSelectedProvince(any()) } just Runs

        // Act
        mapFragment.resetMap()

        // Assert
        verify(exactly = 1) { mapViewModel.fetchReportsAndAddMarkers() }
        verify(exactly = 1) { sharedViewModel.setSearchQuery("") }
        verify(exactly = 1) { sharedViewModel.setSelectedProvince("") }
    }
}
