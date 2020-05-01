package com.e.szerepjatek

import android.content.Intent
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import com.e.automatizmus.UIUpdateRunnable
import com.e.datalayer.Music
import com.e.jatekter.JatekElem
import com.e.jatekter.JatekTer
import com.e.keret.*
import com.e.szabalyok.Jatekos
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val PALYA_MERET_X: Int = 13  //15
    private val PALYA_MERET_Y: Int = 19 //11
    private val KINCSEK_SZAMA: Int = 10
    private val TEXT_REQUEST = 1
    private val INT_REQUEST = 2

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
    val fightCommand = FightCommand(this)
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
        commandProcessor.SetCommand(CommandId.Fight, fightCommand)
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
        viewModelMain = ViewModelMain(6, 9, PALYA_MERET_X, PALYA_MERET_Y,this, table, commandProcessor, ter)

        audioPlayer = AudioPlayer(this)

        keret.Futtatas()

        refreshTask = RefreshTask(viewModelMain)

        refreshTask.setCurrentThread(Thread.currentThread())

        uiUpdateRunnable = UIUpdateRunnable(refreshTask, keret)

        uiUpdateRunnable.run()
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

    fun Fight(jatekosTamado: JatekElem, jatekosVedo: JatekElem){
        if (jatekosTamado !is Jatekos && jatekosVedo !is Jatekos){
            return
        }

        val intent = Intent(this, HarcActivity2::class.java)

        val j = jatekosTamado as Jatekos

        intent.putExtra("eletero1", j.eletero.toString())
        intent.putExtra("ero1", j.ero.toString())
        intent.putExtra("allokepesseg1", j.allokepesseg.toString())
        intent.putExtra("gyorsasag1", j.gyorsasag.toString())
        intent.putExtra("ugyesseg1", j.ugyesseg.toString())
        intent.putExtra("szepseg1", j.szepseg.toString())
        intent.putExtra("egeszseg1", j.egeszseg.toString())
        intent.putExtra("akaratero1", j.akaratero.toString())
        intent.putExtra("asztral1", j.asztral.toString())
        intent.putExtra("intelligencia1", j.intelligencia.toString())
        intent.putExtra("muveltseg1", j.muveltseg.toString())

        val v = jatekosVedo as Jatekos

        intent.putExtra("eletero2", v.eletero.toString())
        intent.putExtra("ero2", v.ero.toString())
        intent.putExtra("allokepesseg2", v.allokepesseg.toString())
        intent.putExtra("gyorsasag2", v.gyorsasag.toString())
        intent.putExtra("ugyesseg2", v.ugyesseg.toString())
        intent.putExtra("szepseg2", v.szepseg.toString())
        intent.putExtra("egeszseg2", v.egeszseg.toString())
        intent.putExtra("akaratero2", v.akaratero.toString())
        intent.putExtra("asztral2", v.asztral.toString())
        intent.putExtra("intelligencia2", v.intelligencia.toString())
        intent.putExtra("muveltseg2", v.muveltseg.toString())

        startActivityForResult(intent, INT_REQUEST)
    }

//    override fun onActivityResult(
//        requestCode: Int, resultCode: Int, data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == INT_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                val reply = data!!.getIntExtra(HarcActivity2.EXTRA_REPLY, 0)
//                Toast.makeText(this@MainActivity, reply, Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}
