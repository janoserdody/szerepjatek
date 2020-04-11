package com.e.szerepjatek

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.e.automatizmus.UIUpdateRunnable
import com.e.datalayer.Music
import com.e.jatekter.JatekTer
import com.e.keret.*
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val PALYA_MERET_X: Int = 13  //15
    private val PALYA_MERET_Y: Int = 19 //11
    private val KINCSEK_SZAMA: Int = 10

    val commandProcessor = CommandProcessor()
    val ter = JatekTer(PALYA_MERET_X, PALYA_MERET_Y)
    private val keret = Keret(ter, KINCSEK_SZAMA, commandProcessor, this)
    lateinit private var timer: Timer
    lateinit private var refreshTask: RefreshTask
    lateinit private var uiUpdateRunnable: UIUpdateRunnable

    val kattintCommand = KattintCommand(keret)
    val kincsfelvetelCommand = KincsfelvetelCommand(keret)
    val jatekosValtozasCommand = JatekosValtozasCommand(keret)
    val playBeepCommand = PlayBeepCommand(this)
    val exitCommand = ExitCommand(this)
    lateinit var dialog: AlertDialog
    lateinit var builder: Builder

    lateinit var audioPlayer: AudioPlayer

    private lateinit var viewModelMain: ViewModelMain

    init {
        commandProcessor.SetCommand(CommandId.Kattint, kattintCommand)
        commandProcessor.SetCommand(CommandId.KincsFelvetel, kincsfelvetelCommand)
        commandProcessor.SetCommand(CommandId.JatekosValtozas, jatekosValtozasCommand)
        commandProcessor.SetCommand(CommandId.PlayBeep, playBeepCommand)
        commandProcessor.SetCommand(CommandId.Exit, exitCommand)
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
        //keret.addObserver(viewModelMain)
        //keret.addObserver(this)

        audioPlayer = AudioPlayer(this)
        //createAlertWindow()

        keret.Futtatas()

        refreshTask = RefreshTask(viewModelMain)

        refreshTask.setCurrentThread(Thread.currentThread())

        uiUpdateRunnable = UIUpdateRunnable(refreshTask, keret)

        uiUpdateRunnable.run()

    }

    override fun onStart() {
        super.onStart()



        //timer = Timer(false)

        //timer.scheduleAtFixedRate(uiUpdateRunnable, 1000, 1000);
    }

    fun update() {
        val textView1 = findViewById<TextView>(R.id.textView1)
        textView1?.setText("életerő: " + keret.eletero.toString())
        val textView2 = findViewById<TextView>(R.id.textView2)
        textView2?.setText("Tapasztalati pontok: " + keret.XP.toString())
        textView1?.invalidate()
        textView2?.invalidate()
        //audioPlayer.play(Music.Beep2)
    }

    fun PlayBeep(music: Music) {
        audioPlayer.play(music)
    }

    fun Exit() {
        createAlertWindow()
        dialog.show()
    }

    fun createAlertWindow(){
        builder = Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage("A játék véget ért")
        builder.setPositiveButton("Kilépés") { dialog, which ->
            Toast.makeText(
                applicationContext,
                "Game over",
                Toast.LENGTH_SHORT
            ).show()
            exitProcess(1)
        }
        dialog = builder.create()
    }
}
