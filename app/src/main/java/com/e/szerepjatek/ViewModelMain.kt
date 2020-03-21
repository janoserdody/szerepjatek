package com.e.szerepjatek

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import com.e.datalayer.Mezo
import android.widget.ImageView
import androidx.core.view.marginTop

class ViewModelMain (var mezokX: Int, var mezokY: Int, var context: Context, var table: TableLayout){
    private val mezoFalArany = 4
    private val mezoFalAranySzorzo = 4 + 1
    private val mezoPixelX = 1080 / mezokX
    private val mezoPixelY = 1920 / mezokY
    private val maxFalX = mezokX + 1;
    private val maxFalY = mezokY + 1;
    private val maxX = mezoFalArany * (maxFalX) + 1
    private val maxY = mezoFalArany * (maxFalY + 1) + 1

    private var mezokReszecske = arrayOf<Array<Mezo>>()
    private var mezokKarakter = arrayOf<Array<Mezo>>()
    private var mezokFal = arrayOf<Array<Mezo>>()

    init{
        for (i in 0..maxX) {
            var array = arrayOf<Mezo>()
            for (j in 0..maxY){
                array += Mezo.Ures
            }
            mezokReszecske += array
        }

        for (i in 0..mezokX) {
            var array = arrayOf<Mezo>()
            for (j in 0..mezokY){
                array += Mezo.Ures
            }
            mezokKarakter += array
        }

        for (i in 0..maxFalX) {
            var array = arrayOf<Mezo>()
            for (j in 0..maxFalY){
                array += Mezo.Ures
            }
            mezokFal += array
        }

        MakeTableLayout()
    }

    fun MakeTableLayout() {
        var width = 1080 / mezokX
        var heigth = 1800 / mezokY

        for (i in 0 until mezokY) {
            val tr = TableRow(context)
            tr.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT))

            //tr.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT))
            tr.setPadding(0,0,0,0)
            // padding példa
            //tr.setPadding(5, 5, 5, 5)
            for (j in 0 until mezokX) {
                val view = ImageView(context)
                view.scaleType = ImageView.ScaleType.CENTER_INSIDE
                view.setImageResource(R.drawable.monster)
                view.maxWidth = width
                view.setPadding(0,0,0,0)
                // view.minimumWidth = 15
                view.maxHeight = heigth
                // view.minimumHeight = 45
                tr.addView(view, TableRow.LayoutParams(width, heigth))
            }
            table.addView(tr)
            table.invalidate()
        }
    }



    fun SetFal(x: Int, y: Int){
        if(x >= maxFalX || y >= maxFalY){
            throw Exception("Kívül esik a játéktéren")
        }

        mezokFal[x][y] = Mezo.Fal

        SetMezoReszecske(x * mezoFalAranySzorzo, y * mezoFalAranySzorzo)
    }

    fun SetMezo(x: Int, y: Int, mezo: Mezo){
        if(x >= mezokX || y >= mezokY){
            throw Exception("Kívül esik a játéktéren")
        }

        mezokKarakter[x][y] = mezo

    }

    private fun SetMezoReszecske(x: Int, y: Int) {
        if(x >= maxX || y >= maxY){
            throw Exception("Kívül esik a játéktéren")
        }

        mezokReszecske[x][y]
    }

}