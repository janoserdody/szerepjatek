package com.e.szerepjatek

import android.content.Context
import android.media.Image
import android.widget.TableLayout
import android.widget.TableRow
import com.e.datalayer.Mezo
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.e.keret.*
import java.util.*
import kotlin.collections.ArrayList

class ViewModelMain (
    var mezokX: Int,
    var mezokY: Int,
    var context: Context,
    var table: TableLayout,
    val commandProcessor: CommandProcessor): ObserverKotlin {

    private val mezoFalArany = 4
    private val mezoFalAranySzorzo = 4 + 1
    private val maxFalX = mezokX + 1;
    private val maxFalY = mezokY + 1;
    private val maxX = mezoFalArany * (maxFalX) + 1
    private val maxY = mezoFalArany * (maxFalY + 1) + 1
    private val mezoPixelX = 1080 / maxX
    private val mezoPixelY = 1800 / (mezoFalArany * (mezokY + 2 + 1 + 1) + 1)

    //private var mezokReszecske = arrayOf<Array<Mezo>>()
    private var mezokKarakter = arrayOf<Array<ImageView?>>()
    private var mezokFal = arrayOf<Array<ImageView?>>()

    init{
      //  for (i in 0..maxX) {
      //      var array = arrayOf<Mezo>()
      //      for (j in 0..maxY){
     //           array += Mezo.Ures
      //      }
      //      mezokReszecske += array
      //////  }

        var imageView = ImageView(context)
        for (i in 0..mezokX) {
            var array = arrayOf<ImageView?>()
            for (j in 0..mezokY){
                array += imageView
            }
            mezokKarakter += array
        }

        for (i in 0 until (maxFalX * 3)) {
            var array = arrayOf<ImageView?>()
            for (j in 0 until (maxFalY * 3)){
                array += imageView
            }
            mezokFal += array
        }

        MakeTableLayout()
    }



    private fun MakeTableLayout() {
        var mezoMaxX = mezoPixelX * mezoFalArany
        var mezoMaxY = mezoPixelY * mezoFalArany

        AddFalRow(mezoMaxX, mezoMaxY)

        for (i in 0 until mezokY) {
            val tr = GetTableRow()

            var fal2 = GetFalVertikalis(mezoPixelX, mezoMaxY)
           mezokFal[0][mezokY * 2 + 1 - i ] = fal2
            tr.addView(fal2, TableRow.LayoutParams(mezoPixelX, mezoMaxY))

            for (j in 0 until mezokX) {
                var mezo = GetMezo(mezoMaxX, mezoMaxY, j, mezokY - 1 - i)
                mezokKarakter[j][mezokY - 1 - i] = mezo
                tr.addView(mezo, TableRow.LayoutParams(mezoMaxX, mezoMaxY))
                var fal = GetFalVertikalis(mezoPixelX, mezoMaxY)
                mezokFal[j][mezokY * 2 + 1 - i] = fal2
                tr.addView(fal, TableRow.LayoutParams(mezoPixelX, mezoMaxY))
            }
            table.addView(tr)

            AddFalRow(mezoMaxX, mezoMaxY)
        }
        table.invalidate()
    }

    private fun AddFalRow(mezoMaxX: Int, mezoMaxY: Int) {
        var trFal = GetTableRow()
        var falHorizontalisRovid2 = GetFalNegyzet(mezoPixelX , mezoPixelY)
        trFal.addView(falHorizontalisRovid2, TableRow.LayoutParams(mezoPixelX , mezoPixelY))

        for (j in 0 until mezokX) {
            var falHorizontalis = GetFalHorizontalis(mezoMaxX , mezoPixelY)
            trFal.addView(falHorizontalis, TableRow.LayoutParams(mezoMaxX , mezoPixelY))
            var falHorizontalisRovid = GetFalNegyzet(mezoPixelX , mezoPixelY)
            trFal.addView(falHorizontalisRovid, TableRow.LayoutParams(mezoPixelX , mezoPixelY))
        }
        table.addView(trFal)
    }

    private fun GetTableRow(): TableRow {
        var tr = TableRow(context)
        tr.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT))
        tr.setPadding(0,0,0,0)
        tr.top = 0
        tr.bottom = 0
        return tr
    }

    private fun GetMezo(mezoMaxX: Int, mezoMaxY: Int, x: Int, y: Int): ImageView {
        var mezo = GetMezoAltalanos(R.drawable.monster, mezoMaxX, mezoMaxY)

           // mezo.setOnClickListener(){
           //     mezo.setImageResource(R.drawable.monster2)
          //      mezo.invalidate()
           // }

         mezo.setOnClickListener(){
             var args = ArrayList<Any>(2)
             args.add(x)
             args.add(y)
             commandProcessor.OnKattint(CommandId.Kattint, args)
         }
        return mezo
    }

    private fun GetFalVertikalis(mezoMaxX: Int, mezoMaxY: Int): ImageView {
        return GetMezoAltalanos(R.drawable.wall_v, mezoMaxX, mezoMaxY)
    }

    private fun GetFalHorizontalis(mezoMaxX: Int, mezoMaxY: Int): ImageView {
        return GetMezoAltalanos(R.drawable.wall_h, mezoMaxX, mezoMaxY)
    }

    private fun GetFalNegyzet(mezoMaxX: Int, mezoMaxY: Int): ImageView {
        return GetMezoAltalanos(R.drawable.wall_negyzet, mezoMaxX, mezoMaxY)
    }

    private fun GetMezoAltalanos(kepId: Int, mezoMaxX: Int, mezoMaxY: Int): ImageView {
        val view = ImageView(context)
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        view.setImageResource(kepId)
        view.maxWidth = mezoMaxX
        view.setPadding(0, 0, 0, 0)
        view.maxHeight = mezoMaxY
        view.bottom = 0;
        view.top = 0;
        return view
    }

    override fun update(o: ObservableKotlin?, arg: Any?) {
        var params = arg as ArrayList<Int>
        if (params == null){
            return
        }
        val x = params[0]
        val y = params[1]
        var keret = o as Keret
        if (keret == null){
            return
        }

        mezokKarakter[x][y]?.setImageResource(R.drawable.monster2)
    }
}