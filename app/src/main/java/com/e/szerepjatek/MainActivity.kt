package com.e.szerepjatek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.szerepjatek.ViewModelMain
import com.e.szerepjatek.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelMain: ViewModelMain
    private var jatekTer = JatekTer(100, 100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val button = findViewById<Button>(R.id.button1)
        //button?.setOnClickListener()
        //  {
        //val imageMonsterView = findViewById<ImageView>(R.id.imageView1)
        //imageMonsterView.setImageResource(R.drawable.monster2);
        //imageMonsterView.invalidate()
        //  }

        var table = findViewById<TableLayout>(R.id.tableLayout1)





        //  6, 9 paraméterrel jól működik
        viewModelMain = ViewModelMain(6, 9, this, table)

    }
}
