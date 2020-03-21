package com.e.jatekter

// konstruktorban a val = publikus property
abstract class JatekElem(val x: Int, val y: Int, ter: JatekTer) {
    protected var ter: JatekTer

    abstract val meret: Double
    abstract fun utkozes(jatekElem: JatekElem)

    init {
        this.ter = ter
        ter.Felvetel(this)
    }
}