package com.example.autofeed.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autofeed.R
import com.example.autofeed.adapter.TabelAdapter
import com.example.autofeed.models.dataTabel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_tabel.*
import kotlin.math.roundToInt

class TabelActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TabelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        init()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    private fun setupActionBar(){
        setSupportActionBar(toolbarTabel)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back24)
        }
        toolbarTabel.setNavigationOnClickListener{onBackPressed()}
    }

    private fun init() {
        val intent = intent
        val jumlahTebar = intent.getIntExtra("Jumlah Tebar", 0)
//        val sizeBenih = intent.getIntExtra("Size Benih", 0)
//        val sizePanen = intent.getIntExtra("Size Panen", 0)
        val lamaPelihara = intent.getIntExtra("Lama Pelihara", 0)
//        val tanggalTebar = intent.getSerializableExtra("Tanggal Tebar")
        val kelangsunganHidup = intent.getIntExtra("Kelangsungan Hidup", 0)
        val FRAwal = intent.getIntExtra("FRAwal", 0)
//        val FRAkhir = intent.getIntExtra("FRAkhir", 0)
        val selisihFR = intent.getIntExtra("Selisih FR", 0)
        val rerataAwal = intent.getDoubleExtra("Rerata Awal", 0.0)
        val biomasAwal = intent.getIntExtra("Biomas Awal", 0)
//        val rerataAkhir = intent.getDoubleExtra("Rerata Akhir", 0.0)
//        val kematian = intent.getIntExtra("Kematian", 0)
        val kematianHarian = intent.getDoubleExtra("Kematian Harian", 0.0)
        val lajuPertumbuhan = intent.getDoubleExtra("Laju Pertumbuhan", 0.0)


        var hari = 1
        var umur = 1
        var bobot = rerataAwal
        var kelangsunganHidupTabel = 100.0
        var ikanHidup = jumlahTebar.toDouble()
        var feedingRate = FRAwal.toDouble()
        var biomas = biomasAwal.toDouble()
        var pakanHarian = 0.0
        var pakanKumulatif = jumlahTebar * rerataAwal * FRAwal / 100000

        val bobot2 = String.format("%.0f", bobot).toDouble()
        val kelangsunganHidupTabel2 = String.format("%.0f", kelangsunganHidupTabel).toDouble()
        val ikanHidup2 = ikanHidup.roundToInt()
        val biomas2 = String.format("%.0f", biomas).toDouble()
        val pakanHarian2 = String.format("%.0f", pakanHarian).toDouble()
        val pakanKumulatif2 = String.format("%.0f", pakanKumulatif).toDouble()

        recyclerView = findViewById(R.id.rv_tabelKalkulator)
        var data = ArrayList<dataTabel>()
        data.add(
            dataTabel(
                hari,
                umur,
                bobot2,
                kelangsunganHidupTabel2,
                ikanHidup2,
                biomas2,
                feedingRate,
                pakanHarian2,
                pakanKumulatif2
            )
        )

        for (i in 2..lamaPelihara) {
            hari += 1
            umur = hari
            bobot += (bobot * lajuPertumbuhan) / 100
            val bobot1 = String.format("%.2f", bobot).toDouble()
            kelangsunganHidupTabel -= kematianHarian
            val kelangsunganHidupTabel1 = String.format("%.2f", kelangsunganHidupTabel).toDouble()
            ikanHidup = kelangsunganHidupTabel * jumlahTebar / 100
            val ikanHidup1 = ikanHidup.roundToInt()
            biomas = bobot * ikanHidup / 1000
            val biomas1 = String.format("%.2f", biomas).toDouble()
            feedingRate -= selisihFR / (lamaPelihara - 1).toDouble()
            val feedingRate1 = String.format("%.2f", feedingRate).toDouble()
            pakanHarian = biomas * feedingRate / 100
            val pakanHarian1 = String.format("%.2f", pakanHarian).toDouble()
            pakanKumulatif = (pakanKumulatif + pakanHarian)
            val pakanKumulatif1 = String.format("%.2f", pakanKumulatif).toDouble()
            data.add(
                dataTabel(
                    hari,
                    umur,
                    bobot1,
                    kelangsunganHidupTabel1,
                    ikanHidup1,
                    biomas1,
                    feedingRate1,
                    pakanHarian1,
                    pakanKumulatif1
                )
            )
        }
        adapter = TabelAdapter(data)
        return
    }
}