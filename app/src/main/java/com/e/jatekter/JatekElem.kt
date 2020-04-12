package com.e.jatekter

// konstruktorban a var = publikus property
abstract class JatekElem(var x: Int, var y: Int, ter: JatekTer) {
    protected var ter: JatekTer

    abstract val meret: Double
    abstract fun utkozes(jatekElem: JatekElem)

    init {
        this.ter = ter
        ter.Felvetel(this)
    }
}