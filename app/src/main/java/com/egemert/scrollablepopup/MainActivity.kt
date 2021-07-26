package com.egemert.scrollablepopup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.egemert.scrollablepopup.infodialog.InfoDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    private fun initFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_view, InfoDialogFragment(), null)
        fragmentTransaction.commit()
    }
}