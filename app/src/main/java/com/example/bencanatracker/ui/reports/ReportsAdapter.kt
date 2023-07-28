import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bencanatracker.R


class ReportsAdapter(private var reportsList: List<ReportsResponse>) :
    RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    fun updateData(newReportsList: List<ReportsResponse>) {
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
        private val imageView: ImageView = itemView.findViewById(R.id.imgReport)

        fun bind(report: ReportsResponse) {
            if (report.reportProperties != null) {
                textReportType.text = report.reportProperties.reportData.reportType

                if (report.reportProperties.imageUrl != null) {
                    // Load the image from the provided image_url
                    Glide.with(itemView)
                        .load(report.reportProperties.imageUrl)
                        .placeholder(R.drawable.ic_defaultimage_background)
                        .error(R.drawable.ic_defaultimage_background)
                        .into(imageView)
                } else {
                    // Use the default local image if image_url is null
                    imageView.setImageResource(R.drawable.ic_defaultimage_background)
                }
            } else {
                // Handle the case where properties is null (optional)
                // For example, you could set a default text or image in this case.
                textReportType.text = "Unknown Report Type"
                imageView.setImageResource(R.drawable.ic_defaultimage_background)
            }
        }
    }

}
