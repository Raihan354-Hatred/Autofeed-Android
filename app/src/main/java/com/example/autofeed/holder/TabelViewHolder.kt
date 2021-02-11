package com.example.autofeed.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autofeed.R
import com.example.autofeed.models.dataTabel
import kotlinx.android.synthetic.main.activity_tabel.view.*

class TabelViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.data_tabel, parent, false)){

    private var hari: TextView? = null
    private var umur: TextView? = null
    private var bobot: TextView? = null
    private var kelangsunganHidupTabel: TextView? = null
    private var ikanHidup: TextView? = null
    private var biomas: TextView? = null
    private var feedingRate: TextView? = null
    private var pakanHarian: TextView? = null
    private var pakanKumulatif: TextView? = null

    init {
        hari = itemView.findViewById(R.id.tv_hariTabel)
        umur = itemView.findViewById(R.id.tv_umurPemeliharaanTabel)
        bobot = itemView.findViewById(R.id.tv_bobotTabel)
        kelangsunganHidupTabel = itemView.findViewById(R.id.tv_kelangsunganHidupTabel)
        ikanHidup = itemView.findViewById(R.id.tv_ikanHidupTabel)
        biomas = itemView.findViewById(R.id.tv_prediksiBiomasTabel)
        feedingRate = itemView.findViewById(R.id.tv_FRTabel)
        pakanHarian = itemView.findViewById(R.id.tv_pakanHarianTabel)
        pakanKumulatif = itemView.findViewById(R.id.tv_pakanKumulatifTabel)
    }

    fun bind(data: dataTabel){
        hari?.text = data.hari.toString()
        umur?.text = data.umur.toString()
        bobot?.text = data.bobot.toString()
        kelangsunganHidupTabel?.text = data.kelangsunganHidupTabel.toString()
        ikanHidup?.text = data.ikanHidup.toString()
        biomas?.text = data.biomas.toString()
        feedingRate?.text = data.feedingRate.toString()
        pakanHarian?.text = data.pakanHarian.toString()
        pakanKumulatif?.text = data.pakanKumulatif.toString()

    }
}