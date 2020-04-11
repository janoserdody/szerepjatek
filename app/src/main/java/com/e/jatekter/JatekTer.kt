package com.e.jatekter

import android.R.attr.resource
import com.e.megjelenites.IKirajzolhato
import com.e.megjelenites.IMegjelenitheto
import com.e.szabalyok.Fal
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.math.sqrt


// konstruktorban a var jelenti a publikus property-t
// típust a kettőspont után adjuk meg
class JatekTer(val meretX: Int, val meretY: Int): IMegjelenitheto, Runnable {
    val MAX_ELEMSZAM = 1000
    private var elemN = 0
    private val elemek =
        ArrayList<JatekElem>(MAX_ELEMSZAM) //JatekElem[MAX_ELEMSZAM];
    private val lock: Lock = ReentrantLock()

    var terkep = arrayOf<Array<MutableList<JatekElem>>>()


    init{
        for (x in 0 until meretX + 1) {
            var array = arrayOf<MutableList<JatekElem>>()
            for (y in 0 until meretY + 1) {
                array += mutableListOf<JatekElem>()
            }
            terkep += array
        }
    }

    override fun run() {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                //resource.doSomething()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            //release lock
            lock.unlock()
        }
        //resource.doLogging()
    }

    fun terkepRemove(x: Int, y: Int, jatekElem: JatekElem) {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                terkep[x][y].remove(jatekElem)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            //release lock
            lock.unlock()
        }
    }

    fun terkepAdd(x: Int, y: Int, jatekElem: MozgoJatekElem){
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                terkep[x][y].add(jatekElem)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            //release lock
            lock.unlock()
        }
    }

        fun terkepRead(x: Int, y: Int): MutableList<JatekElem> {
            var result= mutableListOf<JatekElem>()

            try {
                if (lock.tryLock(3, TimeUnit.SECONDS)) {
                    result.addAll(terkep[x][y])
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                //release lock
                lock.unlock()
            }
            return result
        }

    fun Felvetel(jatekElem: JatekElem) {
        elemek.add(jatekElem)

        elemN++

        terkep[jatekElem.x][jatekElem.y].add(jatekElem)
    }

    fun Torles(jatekElem: JatekElem?) {
        if (jatekElem == null){
            return
        }
        var x = jatekElem.x
        var y = jatekElem.y

        elemek.remove(jatekElem)
        elemN--

        terkepRemove(x, y, jatekElem)
    }

    fun MegadottHelyenLevok(x: Int, y: Int, tavolsag: Int = 0): ArrayList<JatekElem> {
        val jatekElemek = ArrayList<JatekElem>()
        for (jatekElem in elemek) {
            var mertTavolsag =
                sqrt((jatekElem.x - x) * (jatekElem.x - x) + (jatekElem.y - y) * (jatekElem.y - y).toDouble())
            if (tavolsag >= mertTavolsag) {
                jatekElemek.add(jatekElem)
            }
        }
        return jatekElemek
    }

    fun MegadottHelyenFal(x: Int, y: Int): Boolean {
        for (elem in terkep[x][y]){
            if (elem is Fal){
                return true
            }
        }
        return false
    }

    override val megjelenitendoMeret: Array<Int>
        get() {
            return arrayOf(meretX, meretY)
        }

    override fun MegjelenitendoElemek(): ArrayList<IKirajzolhato> {
        var count: Int = 0
        val vissza = ArrayList<IKirajzolhato>()
        for (elem in elemek){
            if (elem is IKirajzolhato){
                count++
                vissza.add(elem)
            }
        }
        return vissza
    }
}