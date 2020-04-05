package com.e.szerepjatek

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.datalayer.Music
import com.e.jatekter.JatekTer
import com.e.keret.*


class MainActivity : AppCompatActivity(), ObserverKotlin {
    private val PALYA_MERET_X: Int = 13  //15
    private val PALYA_MERET_Y: Int = 19 //11
    private val KINCSEK_SZAMA: Int = 10

    val commandProcessor = CommandProcessor()
    val ter = JatekTer(PALYA_MERET_X, PALYA_MERET_Y)
    private val keret = Keret(ter, KINCSEK_SZAMA, commandProcessor, this)

    val kattintCommand = KattintCommand(keret)
    val kincsfelvetelCommand = KincsfelvetelCommand(keret)
    val jatekosValtozasCommand = JatekosValtozasCommand(keret)
    val playBeepCommand = PlayBeepCommand(this)

    lateinit var audioPlayer: AudioPlayer

    private lateinit var viewModelMain: ViewModelMain

    init {
        commandProcessor.SetCommand(CommandId.Kattint, kattintCommand)
        commandProcessor.SetCommand(CommandId.KincsFelvetel, kincsfelvetelCommand)
        commandProcessor.SetCommand(CommandId.JatekosValtozas, jatekosValtozasCommand)
        commandProcessor.SetCommand(CommandId.PlayBeep, playBeepCommand)
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
        viewModelMain = ViewModelMain(6, 9, this, table, commandProcessor, ter)
        keret.addObserver(viewModelMain)
        keret.addObserver(this)

        audioPlayer = AudioPlayer(this)

        keret.Futtatas()
    }

    override fun update(o: ObservableKotlin?, arg: Any?) {
        var params = arg as ArrayList<Int>
        if (params == null){
            return
        }
        val x = params[0]
        val y = params[1]

        val textView1 = findViewById<TextView>(R.id.textView1)
        textView1?.setText("életerő: " + keret.eletero.toString())
        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2?.setText("koordináták: " + x + " " + y)
        textView1?.invalidate()
        textView2?.invalidate()
        //audioPlayer.play(Music.Beep2)
    }

    fun PlayBeep(){
        audioPlayer.play(Music.Beep2)
    }
}
