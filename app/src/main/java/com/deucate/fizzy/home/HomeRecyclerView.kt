package com.deucate.fizzy.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deucate.fizzy.R
import com.deucate.fizzy.model.Mart
import kotlinx.android.synthetic.main.card_home.view.*
import java.util.*

class HomeAdapter(private val data: ArrayList<Mart>) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.card_home, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun checkOpen(startTimeString: String, closeTimeString: String): Boolean {

        //CurrentTime
        val currentTimeInstance = Calendar.getInstance()
        val currentHour = currentTimeInstance.get(Calendar.HOUR) - 12

        val currentTime = SimplifiedTime(currentHour, currentTimeInstance.get(Calendar.MINUTE), currentHour >= 12)
        val startTime = SimplifiedTime("${startTimeString[0]}${startTimeString[1]}".toInt(), "${startTimeString[2]}${startTimeString[3]}".toInt(), startTimeString[4] == 'P')
        val closeTime = SimplifiedTime("${closeTimeString[0]}${closeTimeString[1]}".toInt(), "${closeTimeString[2]}${closeTimeString[3]}".toInt(), closeTimeString[4] == 'P')

        return if (!startTime.isCyclePM) {
            if (currentTime.isCyclePM) {
                return true
            } else {
                when {
                    currentTime.hour > startTime.hour -> return true
                    currentTime.hour == startTime.hour -> return currentTime.minutes > startTime.minutes
                    else -> false
                }
            }

        } else {
            if (!currentTime.isCyclePM) {
                return true
            } else {
                when {
                    currentTime.hour < closeTime.hour -> return true
                    currentTime.hour == closeTime.hour -> return currentTime.minutes < closeTime.minutes
                    else -> false
                }
            }
        }

    }
}

data class SimplifiedTime(
        val hour: Int,
        val minutes: Int,
        val isCyclePM: Boolean
)

class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val cardView = view.homeCardView!!
    val martTitle = view.homeCardMartName!!
    val bannerImage = view.homeCardBannerImage!!
    val status = view.homeCardStatus!!
    val like = view.homeCardLikeImage!!
}