package com.example.autofeed.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.autofeed.R
import kotlinx.android.synthetic.main.activity_hasil_kalkulator.*
import kotlinx.android.synthetic.main.activity_hasil_kalkulator.toolbar
import kotlinx.android.synthetic.main.activity_kalkulator.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.math.ln
import kotlin.math.roundToInt

class HasilKalkulatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_kalkulator)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        //GET INTENT & DATA
        val intent = intent
        val jumlahTebar = intent.getIntExtra("Jumlah Tebar", 0) ?: ""
        val sizeBenih = intent.getIntExtra("Size Benih", 0) ?: ""
        val sizePanen = intent.getIntExtra("Size Panen", 0) ?: ""
        val lamaPelihara = intent.getIntExtra("Lama Pelihara", 0) ?: ""
        val tanggalTebar = intent.getSerializableExtra("Tanggal Tebar") ?: ""
        val kelangsunganHidup = intent.getIntExtra("Kelangsungan Hidup", 0) ?: ""
        val FRAwal = intent.getIntExtra("FRAwal", 0) ?: ""
        val FRAkhir = intent.getIntExtra("FRAkhir", 0) ?: ""
        val selisihFR = intent.getIntExtra("Selisih FR", 0) ?: ""
        val rerataAwal = intent.getDoubleExtra("Rerata Awal", 0.0) ?: ""
        val biomasAwal = intent.getIntExtra("Biomas Awal", 0) ?: ""
        val rerataAkhir = intent.getDoubleExtra("Rerata Akhir", 0.0) ?: ""
        val biomasAkhir = intent.getDoubleExtra("Biomas Akhir", 0.0) ?: ""
        val totalKebutuhanPakan = intent.getDoubleExtra("Total Kebutuhan Pakan", 0.0) ?: ""
        val foodConvertionRatio = intent.getDoubleExtra("Food Convertion Ratio", 0.0) ?: ""
        val kematian = intent.getIntExtra("Kematian", 0) ?: ""
        val kematianHarian = intent.getDoubleExtra("Kematian Harian", 0.0) ?: ""
        val lajuPertumbuhan = intent.getDoubleExtra("Laju Pertumbuhan", 0.0) ?: ""

        //PUT INTENT & DATA
        tv_jumlahTebarHasil.text = jumlahTebar.toString()
        tv_sizeBenihHasil.text = sizeBenih.toString()
        tv_sizePanenHasil.text = sizePanen.toString()
        tv_lamaPeliharaHasil.text = lamaPelihara.toString()
        tv_tanggalTebarHasil.text = tanggalTebar.toString()
        tv_kelangsunganHidupHasil.text = kelangsunganHidup.toString()
        tv_FRAwalHasil.text = FRAwal.toString()
        tv_FRAkhirHasil.text = FRAkhir.toString()
        tv_selisihFRHasil.text = selisihFR.toString()
        tv_rerataAwalHasil.text = rerataAwal.toString()
        tv_biomasAwalHasil.text = biomasAwal.toString()
        tv_rerataAkhirHasil.text = rerataAkhir.toString()
        tv_biomasAkhirHasil.text = biomasAkhir.toString()
        tv_kebutuhanPakanHasil.text = totalKebutuhanPakan.toString()
        tv_FCRHasil.text = foodConvertionRatio.toString()
        tv_kematianHasil.text = kematian.toString()
        tv_kematianHarianHasil.text = kematianHarian.toString()
        tv_lajuPertumbuhanHasil.text = lajuPertumbuhan.toString()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back24)
        }
        toolbar.setNavigationOnClickListener{onBackPressed()}
    }

    fun tabel(view: View) {
        //GET DATA
        val jumlahTebar = tv_jumlahTebarHasil.text.toString().toInt()
        val sizeBenih = tv_sizeBenihHasil.text.toString().toInt()
        val sizePanen = tv_sizePanenHasil.text.toString().toInt()
        val lamaPelihara = tv_lamaPeliharaHasil.text.toString().toInt()
        val tanggalTebar = tv_tanggalTebarHasil.text.toString()
        val kelangsunganHidup = tv_kelangsunganHidupHasil.text.toString().toInt()
        val FRAwal = tv_FRAwalHasil.text.toString().toInt()
        val FRAkhir = tv_FRAkhirHasil.text.toString().toInt()
        val selisihFR = tv_selisihFRHasil.text.toString().toInt()
        val rerataAwal = tv_rerataAwalHasil.text.toString().toDouble()
        val biomasAwal = tv_biomasAwalHasil.text.toString().toInt()
        val rerataAkhir = tv_rerataAkhirHasil.text.toString().toDouble()
        val kematian = tv_kematianHasil.text.toString().toInt()
        val kematianHarian = tv_kematianHarianHasil.text.toString().toDouble()
        val lajuPertumbuhan = tv_lajuPertumbuhanHasil.text.toString().toDouble()

        //START INTENT & SEND DATA
        val intentHasil = Intent(this@HasilKalkulatorActivity, TabelActivity::class.java)
        intentHasil.putExtra("Jumlah Tebar", jumlahTebar)
        intentHasil.putExtra("Size Benih", sizeBenih)
        intentHasil.putExtra("Size Panen", sizePanen)
        intentHasil.putExtra("Lama Pelihara", lamaPelihara)
        intentHasil.putExtra("Tanggal Tebar", tanggalTebar)
        intentHasil.putExtra("Kelangsungan Hidup", kelangsunganHidup)
        intentHasil.putExtra("FRAwal", FRAwal)
        intentHasil.putExtra("FRAkhir", FRAkhir)
        intentHasil.putExtra("Selisih FR", selisihFR)
        intentHasil.putExtra("Rerata Awal", rerataAwal)
        intentHasil.putExtra("Biomas Awal", biomasAwal)
        intentHasil.putExtra("Rerata Akhir", rerataAkhir)
        intentHasil.putExtra("Kematian", kematian)
        intentHasil.putExtra("Kematian Harian", kematianHarian)
        intentHasil.putExtra("Laju Pertumbuhan", lajuPertumbuhan)
        startActivity(intentHasil)
    }
}