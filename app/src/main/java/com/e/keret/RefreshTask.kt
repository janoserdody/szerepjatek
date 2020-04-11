package com.e.keret

import com.e.automatizmus.UIUpdate
import com.e.szerepjatek.ViewModelMain
import java.lang.ref.WeakReference

class RefreshTask {

    private val uiUpdate: UIUpdate = UIUpdate.getInstance()

    private var mViewModelMainWeakRef: WeakReference<ViewModelMain>

    private var mCurrentThread: Thread? = null

    constructor(viewModelMain: ViewModelMain){
        mViewModelMainWeakRef = WeakReference<ViewModelMain>(viewModelMain)
    }

//    fun handleDecodeState(state: Boolean) {
//
//        handleState(state)
//    }

    fun handleUIState(state: Int) {
        /*
         * Passes a handle to this task and the
         * current state to the class that created
         * the thread pools
         */
       uiUpdate.handleState(this, state)
    }

    fun getViewModelMain(): WeakReference<ViewModelMain>?{
        return  mViewModelMainWeakRef}

    fun recycle() {

        // Deletes the weak reference to the imageView
        if (null != mViewModelMainWeakRef) {
            mViewModelMainWeakRef.clear()
        }
    }

    fun getCurrentThread(): Thread? {
        synchronized(uiUpdate) { return mCurrentThread }
    }

    fun setCurrentThread(thread: Thread) {
        synchronized(uiUpdate) { mCurrentThread = thread }
    }
}