package com.e.szerepjatek

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class HarcActivity2 : AppCompatActivity() {
    var viewPlayer: ImageView? = null
    var viewMonster: ImageView? = null
    var imageViewMonster: ImageView? = null
    var imageViewPlayer: ImageView? = null
    var textPlayer: TextView? = null
    var textMonster: TextView? = null
    var playerPoints = 30
    var monsterPoints = 15
    var r: Random? = null
    lateinit var nev1: String
    lateinit var nev2: String
    var originalPlayerPoints = 0
    var originalMonsterPoints = 0

    companion object {
        val EXTRA_REPLY1 = "com.example.android.harcactivity.jatekos1"
        val EXTRA_REPLY2 = "com.example.android.harcactivity.jatekos2"
        val EXTRA_REPLY3 = "com.example.android.harcactivity.sebzes1"
        val EXTRA_REPLY4 = "com.example.android.harcactivity.sebzes2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harc2)

        nev1 = intent.getStringExtra("nev1")
        playerPoints = intent.getIntExtra("eletero1", 0)
        val ero1 = intent.getIntExtra("ero1", 0)
        val allokepesseg1 = intent.getIntExtra("allokepesseg1", 0)
        val gyorsasag1 = intent.getIntExtra("gyorsasag1", 0)
        val ugyesseg1 = intent.getIntExtra("ugyesseg1", 0)
        val szepseg1 = intent.getIntExtra("szepseg1",0)
        val egeszseg1 = intent.getIntExtra("egeszseg1",0)
        val akaratero1 = intent.getIntExtra("akaratero1",0)
        val asztral1 = intent.getIntExtra("asztral1",0)
        val intelligencia1 = intent.getIntExtra("intelligencia1",0)
        val muveltseg1 = intent.getIntExtra("muveltseg1",0)

        nev2 = intent.getStringExtra("nev2")
        monsterPoints = intent.getIntExtra("eletero2", 0)
        val ero2 = intent.getIntExtra("ero2",0)
        val allokepesseg2 = intent.getIntExtra("allokepesseg2",0)
        val gyorsasag2 = intent.getIntExtra("gyorsasag2",0)
        val ugyesseg2 = intent.getIntExtra("ugyesseg2",0)
        val szepseg2 = intent.getIntExtra("szepseg2",0)
        val egeszseg2 = intent.getIntExtra("egeszseg2",0)
        val akaratero2 = intent.getIntExtra("akaratero2",0)
        val asztral2 = intent.getIntExtra("asztral2",0)
        val intelligencia2 = intent.getIntExtra("intelligencia2",0)
        val muveltseg2 = intent.getIntExtra("muveltseg2",0)

        originalPlayerPoints = playerPoints
        originalMonsterPoints = monsterPoints

        val kezdemenyezoErtek1 = calculateKezdemenyezoErtek(ero1, gyorsasag1, ugyesseg1, egeszseg1, intelligencia1)
        val kezdemenyezoErtek2 = calculateKezdemenyezoErtek(ero2, gyorsasag2, ugyesseg2, egeszseg2, intelligencia2)

        val tamadoErtek1 = calculateTamadoErtek(ero1, ugyesseg1, egeszseg1, intelligencia1)
        val tamadoErtek2 = calculateTamadoErtek(ero2, ugyesseg2, egeszseg2, intelligencia2)

        val vedoErtek1 = calculateVedoErtek(gyorsasag1, ugyesseg1, egeszseg1, asztral1, intelligencia1)
        val vedoErtek2 = calculateVedoErtek(gyorsasag2, ugyesseg2, egeszseg2, asztral2, intelligencia2)

        val fajdalomTures1 = calculateFajdalomTures(allokepesseg1, ugyesseg1, egeszseg1, akaratero1, intelligencia1)
        val fajdalomTures2 = calculateFajdalomTures(allokepesseg2, ugyesseg2, egeszseg2, akaratero2, intelligencia2)

        viewPlayer = findViewById<View>(R.id.viewPlayer) as ImageView
        viewMonster = findViewById<View>(R.id.viewMonster) as ImageView
        textPlayer = findViewById<View>(R.id.textPlayer) as TextView
        textMonster = findViewById<View>(R.id.textMonster) as TextView
        imageViewMonster =
            findViewById<View>(R.id.imageViewMonster) as ImageView
        imageViewPlayer =
            findViewById<View>(R.id.imageViewPlayer) as ImageView

        textMonster!!.text = "Monster: $monsterPoints"
        textPlayer!!.text = "Player: $playerPoints"

        r = Random()

        viewPlayer!!.setOnClickListener {
            if (monsterPoints <= 0 || playerPoints <= 0){
                return@setOnClickListener
            }
            val rotate =
                AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)

            val monsterProba = r!!.nextInt(6) + 1
            val playerProba = r!!.nextInt(6) + 1
            setImageMonster(monsterProba)
            setImagePlayer(playerProba)

            if (kezdemenyezoErtek1 * playerProba > kezdemenyezoErtek2 * monsterProba){
                val sebzes = tamadas(
                    tamadoErtek1,
                    tamadoErtek2,
                    vedoErtek1,
                    vedoErtek2,
                    fajdalomTures1,
                    fajdalomTures2,
                    playerProba,
                    monsterProba)

                monsterPoints -= sebzes[1]
                if (monsterPoints > 0){
                    playerPoints -= sebzes[0]
                }
            }
            else {
                val sebzes = tamadas(
                    tamadoErtek2,
                    tamadoErtek1,
                    vedoErtek2,
                    vedoErtek1,
                    fajdalomTures2,
                    fajdalomTures1,
                    monsterProba,
                    playerProba)

                playerPoints -= sebzes[1]
                if (playerPoints > 0){
                    monsterPoints -= sebzes[0]
                }
            }

            megjelenites(rotate)

            if (monsterPoints <= 0) {
                imageViewMonster!!.setImageResource(R.drawable.halottmonster2)
                imageViewMonster!!.startAnimation(rotate)
                finish()
            }
            if (playerPoints <= 0) {
                imageViewPlayer!!.setImageResource(R.drawable.halottfighter1)
                imageViewPlayer!!.startAnimation(rotate)
                finish()
            }
        }
    }

    private fun tamadas(
        tamadoErtek1: Int,
        tamadoErtek2: Int,
        vedoErtek1: Int,
        vedoErtek2: Int,
        fajdalomTures1: Int,
        fajdalomTures2: Int,
        proba1: Int,
        proba2: Int
    ): Array<Int> {

        var sebzes = arrayOf(0, 0)

        var tamadas1 = proba1 * tamadoErtek1 / 8
        var tamadas2 = proba2 * tamadoErtek2 / 8
        var vedes1 = proba1 * vedoErtek1 / 12
        var vedes2 = proba2 * vedoErtek2 / 12
        var fajdalom1 = proba1 * fajdalomTures1 / 12
        var fajdalom2 = proba2 * fajdalomTures2 / 12
        // fájdalom csak pozitív egész szám lehet
        if (fajdalom1 < 1 ){ fajdalom1 = 1}
        if (fajdalom2 < 1 ){ fajdalom2 = 1}


        if (tamadas1 > vedes2){
            sebzes[1] = tamadas1 / fajdalom2
        }

        if (tamadas2 > vedes1){
            sebzes[0] = tamadas2 / fajdalom1
        }

        return sebzes
    }

    private fun megjelenites(rotate: Animation) {
        textMonster!!.text = "Monster: $monsterPoints"
        textPlayer!!.text = "Player: $playerPoints"

        viewMonster!!.startAnimation(rotate)
        viewPlayer!!.startAnimation(rotate)
    }

    private fun calculateFajdalomTures(
        allokepesseg: Int,
        ugyesseg: Int,
        egeszseg: Int,
        akaratero: Int,
        intelligencia: Int): Int {

        val allokepessegD = allokepesseg - 10
        val ugyessegD = ugyesseg - 10
        val egeszsegD = egeszseg - 10
        val akarateroD = akaratero - 10
        val intelligenciaD = intelligencia - 10

        return allokepessegD + ugyessegD + egeszsegD + akarateroD + intelligenciaD
    }

    private fun calculateVedoErtek(
        gyorsasag: Int,
        ugyesseg: Int,
        egeszseg: Int,
        asztral: Int,
        intelligencia: Int): Int {

        val gyorsasagD = gyorsasag - 10
        val ugyessegD = ugyesseg - 10
        val egeszsegD = egeszseg - 10
        val asztralD = asztral - 10
        val intelligenciaD = intelligencia - 10

        return gyorsasagD + ugyessegD + egeszsegD + asztralD + intelligenciaD
    }

    private fun calculateTamadoErtek(
        ero: Int,
        ugyesseg: Int,
        egeszseg: Int,
        intelligencia: Int): Int {

        val eroD = ero - 10
        val ugyessegD = ugyesseg - 10
        val egeszsegD = egeszseg - 10
        val intelligenciaD = intelligencia - 10

        return eroD + ugyessegD + egeszsegD + intelligenciaD
    }

    private fun calculateKezdemenyezoErtek(
        ero: Int,
        gyorsasag: Int,
        ugyesseg: Int,
        egeszseg: Int,
        intelligencia: Int): Int {

        val eroD = ero - 10
        val gyorsasagD = gyorsasag - 10
        val ugyessegD = ugyesseg - 10
        val egeszsegD = egeszseg - 10
        val intelligenciaD = intelligencia - 10

        return eroD + gyorsasagD + ugyessegD + egeszsegD + intelligenciaD
    }

    fun setImageMonster(number: Int) {
        when (number) {
            1 -> viewMonster!!.setImageResource(R.drawable.egy)
            2 -> viewMonster!!.setImageResource(R.drawable.ketto)
            3 -> viewMonster!!.setImageResource(R.drawable.harom)
            4 -> viewMonster!!.setImageResource(R.drawable.negy)
            5 -> viewMonster!!.setImageResource(R.drawable.ot)
            6 -> viewMonster!!.setImageResource(R.drawable.hat)
        }
    }

    fun setImagePlayer(number: Int) {
        when (number) {
            1 -> viewPlayer!!.setImageResource(R.drawable.egy)
            2 -> viewPlayer!!.setImageResource(R.drawable.ketto)
            3 -> viewPlayer!!.setImageResource(R.drawable.harom)
            4 -> viewPlayer!!.setImageResource(R.drawable.negy)
            5 -> viewPlayer!!.setImageResource(R.drawable.ot)
            6 -> viewPlayer!!.setImageResource(R.drawable.hat)
        }
    }

    override fun finish() {
        // Prepare data intent
        val data = Intent()

        val sebzes1 = originalPlayerPoints - playerPoints
        val sebzes2 = originalMonsterPoints - monsterPoints
        data.putExtra(EXTRA_REPLY1, nev1)
        data.putExtra(EXTRA_REPLY2, nev2)
        data.putExtra(EXTRA_REPLY3, sebzes1)
        data.putExtra(EXTRA_REPLY4, sebzes2)
        // Activity finished ok, return the data
        setResult(Activity.RESULT_OK, data)
        super.finish()
    }
}