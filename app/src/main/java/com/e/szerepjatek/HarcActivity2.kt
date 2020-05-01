package com.e.szerepjatek

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harc2)
        viewPlayer = findViewById<View>(R.id.viewPlayer) as ImageView
        viewMonster = findViewById<View>(R.id.viewMonster) as ImageView
        textPlayer = findViewById<View>(R.id.textPlayer) as TextView
        textMonster = findViewById<View>(R.id.textMonster) as TextView
        imageViewMonster =
            findViewById<View>(R.id.imageViewMonster) as ImageView
        imageViewPlayer =
            findViewById<View>(R.id.imageViewPlayer) as ImageView
        r = Random()

        viewPlayer!!.setOnClickListener {
            val monsterProba = r!!.nextInt(6) + 1
            val playerProba = r!!.nextInt(6) + 1
            setImageMonster(monsterProba)
            setImagePlayer(playerProba)
            if (playerProba > monsterProba) {
                val i = playerProba - monsterProba
                monsterPoints -= i
            }
            if (monsterProba > playerProba) {
                val i = monsterProba - playerProba
                playerPoints -= i
            }
            textMonster!!.text = "Monster: $monsterPoints"
            textPlayer!!.text = "Player: $playerPoints"
            val rotate =
                AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
            viewMonster!!.startAnimation(rotate)
            viewPlayer!!.startAnimation(rotate)
            if (monsterPoints <= 0) {
                imageViewMonster!!.setImageResource(R.drawable.halottmonster2)
                imageViewMonster!!.startAnimation(rotate)
                Toast.makeText(
                    this@HarcActivity2,
                    "Vége a játéknak: Nyetrél",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (playerPoints <= 0) {
                imageViewPlayer!!.setImageResource(R.drawable.halottfighter1)
                imageViewPlayer!!.startAnimation(rotate)
                Toast.makeText(
                    this@HarcActivity2,
                    "Vége a játéknak: Vesztettél",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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
}