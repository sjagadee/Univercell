/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    private HashMap<String, WeakReference<Context>> contextObjects = new HashMap<String, WeakReference<Context>>();

    public synchronized Context getActiveContext(String className) {
        WeakReference<Context> ref = contextObjects.get(className);
        if (ref == null) {
            return null;
        }

        final Context c = ref.get();
        if (c == null) // If the WeakReference is no longer valid, ensure it is removed.
            contextObjects.remove(className);

        return c;
    }

    public synchronized void setActiveContext(String className, Context context) {
        WeakReference<Context> ref = new WeakReference<Context>(context);
        this.contextObjects.put(className, ref);
    }

    public synchronized void resetActiveContext(String className) {
        contextObjects.remove(className);
    }

    /**
     * <p>
     * Invoked if the application is about to close. Application close is being defined as the
     * transition of the last running Activity of the current application to the Android home screen
     * using the BACK button. You can leverage this method to perform cleanup logic such as freeing
     * resources whenever your user "exits" your app using the back button.
     * </p>
     * <p>
     * Note that you must not rely on this callback as a general purpose "exit" handler, since
     * Android does not give any guarantees as to when exactly the process hosting an application is
     * being terminated. In other words, your application can be terminated at any point in time, in
     * which case this method will NOT be invoked.
     * </p>
     */
    public void onClose() {
        // NO-OP by default
    }
}

