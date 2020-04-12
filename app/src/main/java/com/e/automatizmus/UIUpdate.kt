package com.e.automatizmus

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import com.e.keret.Keret
import com.e.keret.RefreshTask
import com.e.szabalyok.GepiJatekos
import com.e.szabalyok.MozgasHalalMiattNemSikerultKivetel
import com.e.szerepjatek.ViewModelMain
import java.lang.ref.WeakReference

class UIUpdate() {

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        var firstRun = 0

        override fun handleMessage(inputMessage: Message) {

            if (firstRun < 20){
                firstRun++
                return
            }
            var refreshTask = inputMessage.obj as RefreshTask
            if (refreshTask == null){return}
            var mViewModelMainWeakRef: WeakReference<ViewModelMain>? = refreshTask.getViewModelMain()
            var view = mViewModelMainWeakRef?.get()

            view?.RefreshLayout()
            }
    }

    fun handleState(refreshTask: RefreshTask, state: Int) {

        handler.obtainMessage(state, refreshTask)?.apply {
            sendToTarget()
        }
    }

    companion object {
        private var sInstance: UIUpdate = UIUpdate()

        fun getInstance(): UIUpdate {
            if (sInstance == null){
                sInstance = UIUpdate()
            }
            return sInstance
        }
    }
}

class UIUpdateRunnable(private val refreshTask: RefreshTask,
                       private val keret: Keret) : Runnable {
    private val mHandler = Handler()
    private var szamlalo = 0

    override fun run() {
        refreshTask.handleUIState(1)

        if (szamlalo++ >= 15){
            szamlalo = 0
            mozgas()
        }

        var now = SystemClock.uptimeMillis();

        var next = now + 100;

        mHandler.postAtTime(this, next);
    }

    private fun mozgas(){
            var gepiJatekosok = keret.getGepiJatekosok()
            for (jatekos in gepiJatekosok){
                try {
                    (jatekos as GepiJatekos)?.mozgas()
                }
                catch (e: MozgasHalalMiattNemSikerultKivetel){
                    if (jatekos != null) {
                        keret.jatekosRemove(jatekos)
                    }
                }
            }
    }
}