package com.e.szerepjatek

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import com.e.datalayer.Mezo
import android.widget.ImageView

class ViewModelMain (var mezokX: Int, var mezokY: Int, var context: Context, var table: TableLayout){
    private val mezoFalArany = 4
    private val mezoFalAranySzorzo = 4 + 1
    private val maxFalX = mezokX + 1;
    private val maxFalY = mezokY + 1;
    private val maxX = mezoFalArany * (maxFalX) + 1
    private val maxY = mezoFalArany * (maxFalY + 1) + 1
    private val mezoPixelX = 1080 / maxX
    private val mezoPixelY = 1920 / maxY

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
        var mezoMaxX = mezoPixelX * mezoFalArany
        var mezoMaxY = mezoPixelY * mezoFalArany
        for (i in 0 until mezokY) {
            val tr = TableRow(context)
            tr.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT))
            tr.setPadding(0,0,0,0)
            tr.top = 0
            tr.bottom = 0

            var fal = GetFalVertikalis(mezoPixelX, mezoMaxY)
            tr.addView(fal, TableRow.LayoutParams(mezoPixelX, mezoMaxY))
            for (j in 0 until mezokX) {
                var mezo = GetMezo(mezoMaxX, mezoMaxY)
                tr.addView(mezo, TableRow.LayoutParams(mezoMaxX, mezoMaxY))
                var fal = GetFalVertikalis(mezoPixelX, mezoMaxY)
                tr.addView(fal, TableRow.LayoutParams(mezoPixelX, mezoMaxY))
            }
            table.addView(tr)
        }
        table.invalidate()
    }

    private fun GetMezo(mezoMaxX: Int, mezoMaxY: Int): ImageView {
        val view = ImageView(context)
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        view.setImageResource(R.drawable.monster)
        view.maxWidth = mezoMaxX
        view.setPadding(0, 0, 0, 0)
        view.maxHeight = mezoMaxY
        view.bottom = 0;
        view.top = 0;
        return view
    }

    private fun GetFalVertikalis(mezoMaxX: Int, mezoMaxY: Int): ImageView {
        val view = ImageView(context)
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        view.setImageResource(R.drawable.wall_v)
        view.maxWidth = mezoMaxX
        view.setPadding(0, 0, 0, 0)
        view.maxHeight = mezoMaxY
        view.bottom = 0;
        view.top = 0;
        return view
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