package com.e.szerepjatek

import android.content.Context
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.ImageView
import com.e.jatekter.JatekTer
import com.e.keret.*
import com.e.szabalyok.Jatekos
import com.e.szabalyok.Kincs
import kotlin.collections.ArrayList

class ViewModelMain (
    val mezokX: Int,
    val mezokY: Int,
    val palyameretX: Int,
    val palyameretY: Int,
    var context: Context,
    var table: TableLayout,
    val commandProcessor: CommandProcessor,
    val ter: JatekTer) {

    private val mezoFalArany = 4
    private val mezoFalAranySzorzo = 4 + 1
    private val maxFalX = mezokX + 1;
    private val maxFalY = mezokY + 1;
    private val maxX = mezoFalArany * (maxFalX) + 1
    private val maxY = mezoFalArany * (maxFalY + 1) + 1
    private val mezoPixelX = 1080 / maxX
    private val mezoPixelY = 1800 / (mezoFalArany * (mezokY + 2 + 1 + 1) + 1)

    private var offsetX = 0
    private var offsetY = 0

    private var mezokKarakter = arrayOf<Array<ImageView?>>()

    init{

        var imageView = ImageView(context)
        for (i in 0..mezokX * 4) {
            var array = arrayOf<ImageView?>()
            for (j in 0..mezokY * 4){
                array += imageView
            }
            mezokKarakter += array
        }

        makeTableLayout()
    }

    fun refreshLayout(){

        var mezoMaxX = mezoPixelX * mezoFalArany
        var mezoMaxY = mezoPixelY * mezoFalArany
        var palyaX = mezokX * 2 + 1
        var palyaY = mezokY * 2 + 1

        var alak = R.drawable.background_1

        for (x in 1 until palyaX step 2){
            for(y in 1 until palyaY step 2){
                alak = R.drawable.background_1
                var jatekElemek = ter.terkepRead(x, y)
                for (elem in jatekElemek)
                {
                    if (elem is Jatekos){
                        alak = elem.alak
                        break
                    }
                    else if (elem is Kincs){
                        alak = elem.alak
                    }
                }
                mezokKarakter[x][y]?.setImageResource(alak)
            }
        }

        (context as MainActivity).update()
    }

    private fun makeTableLayout() {
        var mezoMaxX = mezoPixelX * mezoFalArany
        var mezoMaxY = mezoPixelY * mezoFalArany
        var terkepX = palyameretX - 1
        var terkepY = palyameretY - 1

        addFalRow(mezoMaxX, mezoMaxY, terkepX, terkepY)
        terkepY--

        for (i in 0 until mezokY) {
            val tr = getTableRow()

            var fal2 = getFalVertikalis(mezoPixelX, mezoMaxY, terkepX--, terkepY)
           mezokKarakter[terkepX][terkepY] = fal2
            tr.addView(fal2, TableRow.LayoutParams(mezoPixelX, mezoMaxY))

            for (j in 0 until mezokX) {
                var mezo = getMezo(mezoMaxX, mezoMaxY, terkepX, terkepY)
                mezokKarakter[terkepX--][terkepY] = mezo
                tr.addView(mezo, TableRow.LayoutParams(mezoMaxX, mezoMaxY))
                var fal = getFalVertikalis(mezoPixelX, mezoMaxY, terkepX, terkepY)
                mezokKarakter[terkepX--][terkepY] = fal2
                tr.addView(fal, TableRow.LayoutParams(mezoPixelX, mezoMaxY))
            }
            table.addView(tr)

            terkepX = palyameretX - 1

            addFalRow(mezoMaxX, mezoMaxY, terkepX, --terkepY)
            terkepY--
        }
        table.invalidate()
    }

    private fun addFalRow(mezoMaxX: Int, mezoMaxY: Int, _terkepX: Int, terkepY: Int) {
        var terkepX = _terkepX

        var trFal = getTableRow()

        var falHorizontalisRovid2 = getFalNegyzet(mezoPixelX , mezoPixelY, terkepX, terkepY)
        mezokKarakter[terkepX--][terkepY] = falHorizontalisRovid2

        trFal.addView(falHorizontalisRovid2, TableRow.LayoutParams(mezoPixelX , mezoPixelY))

        for (j in 0 until mezokX) {
            var falHorizontalis = getFalHorizontalis(mezoMaxX , mezoPixelY, terkepX, terkepY)
            mezokKarakter[terkepX--][terkepY] = falHorizontalis
            trFal.addView(falHorizontalis, TableRow.LayoutParams(mezoMaxX , mezoPixelY))
            var falHorizontalisRovid = getFalNegyzet(mezoPixelX , mezoPixelY, terkepX, terkepY)
            mezokKarakter[terkepX--][terkepY] = falHorizontalisRovid
            trFal.addView(falHorizontalisRovid, TableRow.LayoutParams(mezoPixelX , mezoPixelY))
        }
        table.addView(trFal)
    }

    private fun getTableRow(): TableRow {
        var tr = TableRow(context)
        tr.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT))
        tr.setPadding(0,0,0,0)
        tr.top = 0
        tr.bottom = 0
        return tr
    }

    private fun getMezo(mezoMaxX: Int, mezoMaxY: Int, terkepX: Int, terkepY: Int)
            : ImageView {
        var alak = R.drawable.background_1
        for (elem in ter.megadottHelyenLevok(terkepX, terkepY)){
            if (elem is Jatekos){
                alak = elem.alak
                break
            }
            else if (elem is Kincs){
                alak = elem.alak
            }
        }

        var mezo = getMezoAltalanos(alak, mezoMaxX, mezoMaxY)

         mezo.setOnClickListener(){
             var args = ArrayList<Any>(2)
             args.add(terkepX)
             args.add(terkepY)
             commandProcessor.execute(CommandId.Kattint, args)
         }
        return mezo
    }

    private fun getFalVertikalis(mezoMaxX: Int, mezoMaxY: Int, terkepX: Int, terkepY: Int): ImageView {
        if (!ter.megadottHelyenFal(terkepX, terkepY)) {
            return getMezoAltalanos(R.drawable.background_v, mezoMaxX, mezoMaxY)
        }
        return getMezoAltalanos(R.drawable.wall_v, mezoMaxX, mezoMaxY)
    }

    private fun getFalHorizontalis(mezoMaxX: Int, mezoMaxY: Int, terkepX: Int, terkepY: Int): ImageView {
        if (!ter.megadottHelyenFal(terkepX, terkepY)){
            return getMezoAltalanos(R.drawable.background_h, mezoMaxX, mezoMaxY)
        }
        return getMezoAltalanos(R.drawable.wall_h, mezoMaxX, mezoMaxY)
    }

    private fun getFalNegyzet(mezoMaxX: Int, mezoMaxY: Int, terkepX: Int, terkepY: Int): ImageView {
        if (!ter.megadottHelyenFal(terkepX, terkepY)){
            return getMezoAltalanos(R.drawable.background_negyzet, mezoMaxX, mezoMaxY)
        }
        return getMezoAltalanos(R.drawable.wall_negyzet, mezoMaxX, mezoMaxY)
    }

    private fun getMezoAltalanos(kepId: Int, mezoMaxX: Int, mezoMaxY: Int): ImageView {
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
}