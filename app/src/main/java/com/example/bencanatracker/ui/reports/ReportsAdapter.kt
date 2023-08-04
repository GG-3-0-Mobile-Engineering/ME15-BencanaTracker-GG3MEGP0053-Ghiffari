import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bencanatracker.R
import com.example.bencanatracker.response.Geometry
import AreaList

class ReportsAdapter(private var reportsList: List<Geometry>, private val areaList: AreaList) :
    RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    fun updateData(newReportsList: List<Geometry>) {
        reportsList = newReportsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reports, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reportsList[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return reportsList.size
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textReportType: TextView = itemView.findViewById(R.id.text_report_type)
        private val txtdate: TextView = itemView.findViewById(R.id.txt_createdate)
        private val txtLocation: TextView = itemView.findViewById(R.id.txt_location)
        private val imageView: ImageView = itemView.findViewById(R.id.imgReport)

        fun bind(geometry: Geometry) {
            textReportType.text = geometry.properties.reportData.reportType ?: "Unknown Report Type"
            txtdate.text = geometry.properties.createdAt ?: "Unknown date"

            val provinceName = geometry.properties.tags.instanceRegionCode?.let {
                areaList.ProvinceCode[it] ?: "Unknown Province"
            } ?: "Unknown Province"

            txtLocation.text = provinceName

            Glide.with(itemView)
                .load(geometry.properties.imageUrl)
                .placeholder(R.drawable.ic_defaultimage_background)
                .error(R.drawable.ic_defaultimage_background)
                .into(imageView)
        }
    }
}