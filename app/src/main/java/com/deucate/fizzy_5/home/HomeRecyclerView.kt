package com.deucate.fizzy_5.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy_5.R
import com.deucate.fizzy_5.model.Mart
import kotlinx.android.synthetic.main.card_home.view.*
import java.util.*

class HomeAdapter(private val data: ArrayList<Mart>, private val listener: OnHomeCardClickListener) : RecyclerView.Adapter<HomeViewHolder>() {

    interface OnHomeCardClickListener {
        fun onClickCard(position: Int)
        fun onClickLike(position: Int, viewHolder: HomeViewHolder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.card_home, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val mart = data[position]

        holder.status.text = checkOpen(mart.startTime, mart.closeTime)
        holder.martTitle.text = mart.name

        holder.cardView.setOnClickListener {
            listener.onClickCard(position)
        }

        holder.like.setOnClickListener {
            listener.onClickLike(position, holder)
        }
    }

    private fun checkOpen(startTimeString: String, closeTimeString: String): String {

        val currentTimeInstance = Calendar.getInstance()
        val currentHour = currentTimeInstance.get(Calendar.HOUR)

        val currentTime = SimplifiedTime(currentHour, currentTimeInstance.get(Calendar.MINUTE))

        var startTimeHour = "${startTimeString[0]}${startTimeString[1]}".toInt()
        if (startTimeString[4] == 'P') {
            startTimeHour += 12
        }
        var closeTimeHour = "${closeTimeString[0]}${closeTimeString[1]}".toInt()
        if (closeTimeString[4] == 'P') {
            closeTimeHour += 12
        }
        val startTime = SimplifiedTime(startTimeHour, "${startTimeString[2]}${startTimeString[3]}".toInt())
        val closeTime = SimplifiedTime("${closeTimeString[0]}${closeTimeString[1]}".toInt(), "${closeTimeString[2]}${closeTimeString[3]}".toInt())


        val afterOpen = startTime.hour > currentTime.hour
        val afterClose = closeTime.hour < currentTime.hour

        val status = if (!(afterClose && afterOpen)) {
            "Open"
        } else {
            "Close"
        }

        return status
    }
}

data class SimplifiedTime(
        val hour: Int,
        val minutes: Int
)

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cardView = view.homeCardView!!
    val martTitle = view.homeCardMartName!!
    val bannerImage = view.homeCardBannerImage!!
    val status = view.homeCardStatus!!
    val like = view.homeCardLikeImage!!
}