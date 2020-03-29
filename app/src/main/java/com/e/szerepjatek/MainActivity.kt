package com.e.szerepjatek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import com.e.jatekter.JatekTer
import com.e.jatekter.MozgoJatekElem
import com.e.keret.*
import com.e.szerepjatek.ViewModelMain
import com.e.szerepjatek.R

class MainActivity : AppCompatActivity(), ObserverKotlin {
    private val PALYA_MERET_X: Int = 21
    private val PALYA_MERET_Y: Int = 11
    private val KINCSEK_SZAMA: Int = 10

    val commandProcessor = CommandProcessor()
    val ter = JatekTer(PALYA_MERET_X, PALYA_MERET_Y)
    private val keret = Keret(ter, KINCSEK_SZAMA)

    val kattintCommand = KattintCommand(keret)

    private lateinit var viewModelMain: ViewModelMain

    init {
        commandProcessor.SetCommand(CommandId.Kattint, kattintCommand)
    }

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
        viewModelMain = ViewModelMain(6, 9, this, table, commandProcessor)
        keret.addObserver(viewModelMain)
        keret.addObserver(this)

    }

    override fun update(o: ObservableKotlin?, arg: Any?) {
        val textView = findViewById<TextView>(R.id.textView1)
        textView?.setText("életerő: " + keret.eletero.toString())
        textView?.invalidate()
    }
}
