package com.e.keret

import java.util.*

open class ObservableKotlin {
    private var changed = false
    private val obs: Vector<ObserverKotlin>

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param   o   an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
    @Synchronized
    fun addObserver(o: ObserverKotlin?) {
        if (o == null) throw NullPointerException()
        if (!obs.contains(o)) {
            obs.addElement(o)
        }
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     * @param   o   the observer to be deleted.
     */
    @Synchronized
    fun deleteObserver(o: ObserverKotlin?) {
        obs.removeElement(o)
    }
    /**
     * If this object has changed, as indicated by the
     * `hasChanged` method, then notify all of its observers
     * and then call the `clearChanged` method to indicate
     * that this object has no longer changed.
     *
     *
     * Each observer has its `update` method called with two
     * arguments: this observable object and the `arg` argument.
     *
     * @param   arg   any object.
     * @see java.util.Observable.clearChanged
     * @see java.util.Observable.hasChanged
     * @see java.util.Observer.update
     */
    /**
     * If this object has changed, as indicated by the
     * `hasChanged` method, then notify all of its observers
     * and then call the `clearChanged` method to
     * indicate that this object has no longer changed.
     *
     *
     * Each observer has its `update` method called with two
     * arguments: this observable object and `null`. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see java.util.Observable.clearChanged
     * @see java.util.Observable.hasChanged
     * @see java.util.Observer.update
     */
    @JvmOverloads
    fun notifyObservers(arg: Any? = null) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        var arrLocal: Array<Any>
  //      synchronized(this) {

            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            // Android-changed: Call out to hasChanged() to figure out if something changes.
            // Upstream code avoids calling the nonfinal hasChanged() from the synchronized block,
            // but that would break compatibility for apps that override that method.
            // if (!changed)
      //      if (!hasChanged()) return
            arrLocal = obs.toTypedArray()
            clearChanged()
   //     }
        for (i in arrLocal.indices.reversed()) (arrLocal[i] as ObserverKotlin).update(this, arg)
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Synchronized
    fun deleteObservers() {
        obs.removeAllElements()
    }

    /**
     * Marks this <tt>Observable</tt> object as having been changed; the
     * <tt>hasChanged</tt> method will now return <tt>true</tt>.
     */
    @Synchronized
    protected fun setChanged() {
        changed = true
    }

    /**
     * Indicates that this object has no longer changed, or that it has
     * already notified all of its observers of its most recent change,
     * so that the <tt>hasChanged</tt> method will now return <tt>false</tt>.
     * This method is called automatically by the
     * `notifyObservers` methods.
     *
     * @see java.util.Observable.notifyObservers
     * @see java.util.Observable.notifyObservers
     */
    @Synchronized
    protected fun clearChanged() {
        changed = false
    }

    /**
     * Tests if this object has changed.
     *
     * @return  `true` if and only if the `setChanged`
     * method has been called more recently than the
     * `clearChanged` method on this object;
     * `false` otherwise.
     * @see java.util.Observable.clearChanged
     * @see java.util.Observable.setChanged
     */
    @Synchronized
    fun hasChanged(): Boolean {
        return changed
    }

    /**
     * Returns the number of observers of this <tt>Observable</tt> object.
     *
     * @return  the number of observers of this object.
     */
    @Synchronized
    fun countObservers(): Int {
        return obs.size
    }

    /** Construct an Observable with zero Observers.  */
    init {
        obs = Vector()
    }
}