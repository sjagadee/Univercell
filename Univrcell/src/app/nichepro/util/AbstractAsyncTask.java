/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.util;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;
import app.nichepro.activities.mcommerce.BaseActivity;
import app.nichepro.base.BaseApp;

/**
 * Works in a similar way to AsyncTask but provides extra functionality.
 *
 * 1) It keeps track of the active instance of each Context, ensuring that the
 * correct instance is reported to. This is very useful if your Activity is
 * forced into the background, or the user rotates his device.
 *
 * 2) A progress dialog is automatically shown. See useCustomDialog()
 * disableDialog()
 *
 * 3) If an Exception is thrown from inside doInBackground, this is now handled
 * by the handleError method.
 *
 * 4) You should no longer override onPreExecute(), doInBackground() and
 * onPostExecute(), instead you should use before(), doCheckedInBackground() and
 * after() respectively.
 *
 * These features require that the Application extends BaseApp.
 *
 * @param <ParameterT>
 * @param <ProgressT>
 * @param <ReturnT>
 */
public abstract class AbstractAsyncTask<ParameterT, ProgressT, ReturnT> extends
        AsyncTask<ParameterT, ProgressT, ReturnT> {

    protected final BaseApp appContext;
    private final boolean contextIsBaseActivity;

    private Exception error;

    private boolean isTitleProgressEnabled,
            isTitleProgressIndeterminateEnabled = true;

    private final String callerId;

    private AbstractAsyncTaskCallable<ParameterT, ProgressT, ReturnT> callable;

    private int dialogId = 0;
    
    private static int dialogidcount = 0;
    private static int MAX_COUNT_FORDIALOG = Integer.MAX_VALUE - 10;
    
    private void generateDialogId(){
    	if(dialogidcount > MAX_COUNT_FORDIALOG)
    		dialogidcount = 0;
    	dialogidcount = dialogidcount + 1;
    	dialogId = dialogidcount;
    }
    
    public int getDialogId() {
    	return dialogId;
    }
    
    /**
     * Use a custom resource ID for the progress dialog
     *
     * @param dialogId
     */
    public void useCustomDialog(int dialogId) {
        this.dialogId = dialogId;
    }

    /**
     * Disable the display of a dialog during the execution of this task.
     */
    public void disableDialog() {
        this.dialogId = -1;
    }

    /**
     * Creates a new BaseAsyncTask who displays a progress dialog on the specified Context.
     *
     * @param context
     */
    public AbstractAsyncTask(Context context) {
        if (!(context.getApplicationContext() instanceof BaseApp)) {
            throw new IllegalArgumentException(
                    "context bound to this task must be a BaseApp context (BaseApp)");
        }
        this.appContext = (BaseApp) context.getApplicationContext();
        this.callerId = context.getClass().getCanonicalName();
        this.contextIsBaseActivity = context instanceof BaseActivity;
        generateDialogId();

        appContext.setActiveContext(callerId, context);

        if (contextIsBaseActivity) {
            int windowFeatures = ((BaseActivity) context).getWindowFeatures();
            if (Window.FEATURE_PROGRESS == (Window.FEATURE_PROGRESS & windowFeatures)) {
                this.isTitleProgressEnabled = true;
            } else if (Window.FEATURE_INDETERMINATE_PROGRESS == (Window.FEATURE_INDETERMINATE_PROGRESS & windowFeatures)) {
                this.isTitleProgressIndeterminateEnabled = true;
            }
        }
    }

    /**
     * Gets the most recent instance of this Context.
     * This may not be the Context used to construct this BaseAsyncTask as that Context might have been destroyed
     * when a incoming call was received, or the user rotated the screen.
     *
     * @return The current Context, or null if the current Context has ended, and a new one has not spawned.
     */
    protected Context getCallingContext() {
        try {
            Context caller = (Context) appContext.getActiveContext(callerId);
            if (caller == null || !this.callerId.equals(caller.getClass().getCanonicalName())
                    || (caller instanceof Activity && ((Activity) caller).isFinishing())) {
                // the context that started this task has died and/or was
                // replaced with a different one
                return null;
            }
            return caller;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        Context context = getCallingContext();
        if (context == null) {
            Log.d(AbstractAsyncTask.class.getSimpleName(), "skipping pre-exec handler for task "
                    + hashCode() + " (context is null)");
            cancel(true);
            return;
        }

        if (contextIsBaseActivity) {
            Activity activity = (Activity) context;
            if (dialogId > -1) {
                activity.showDialog(dialogId);
            }
            if (isTitleProgressEnabled) {
                activity.setProgressBarVisibility(true);
            } else if (isTitleProgressIndeterminateEnabled) {
                activity.setProgressBarIndeterminateVisibility(true);
            }
        }
        before(context);
    }

    /**
     * Override to run code in the UI thread before this Task is run.
     *
     * @param context
     */
    protected void before(Context context) {
    }

    @Override
    protected final ReturnT doInBackground(ParameterT... params) {
        ReturnT result = null;
        Context context = getCallingContext();
        try {
            result = doCheckedInBackground(context, params);
        } catch (Exception e) {
            this.error = e;
        }
        return result;
    }

    /**
     * Override to perform computation in a background thread
     *
     * @param context
     * @param params
     * @return
     * @throws Exception
     */
    protected ReturnT doCheckedInBackground(Context context, ParameterT... params) throws Exception {
        if (callable != null) {
            return callable.call(this);
        }
        return null;
    }

    /**
     * Runs in the UI thread if there was an exception throw from doCheckedInBackground
     *
     * @param context The most recent instance of the Context that executed this BaseAsyncTask
     * @param error The thrown exception.
     */
    protected abstract void handleError(Context context, Exception error);

    @Override
    protected final void onPostExecute(ReturnT result) {
        Context context = getCallingContext();
        if (context == null) {
            Log.d(AbstractAsyncTask.class.getSimpleName(), "skipping post-exec handler for task "
                    + hashCode() + " (context is null)");
            return;
        }

        if (contextIsBaseActivity) {
            Activity activity = (Activity) context;
            if (dialogId > -1) {
                //activity.removeDialog(dialogId);
                activity.removeDialog(dialogId);
            }
            if (isTitleProgressEnabled) {
                activity.setProgressBarVisibility(false);
            } else if (isTitleProgressIndeterminateEnabled) {
                activity.setProgressBarIndeterminateVisibility(false);
            }
        }

        if (failed()) {
            handleError(context, error);
        } else {
            after(context, result);
        }
    }

    /**
     * A replacement for onPostExecute. Runs in the UI thread after doCheckedInBackground returns.
     *
     * @param context The most recent instance of the Context that executed this BaseAsyncTask
     * @param result The result returned from doCheckedInBackground
     */
    protected abstract void after(Context context, ReturnT result);

    /**
     * Has an exception been thrown inside doCheckedInBackground()
     * @return
     */
    public boolean failed() {
        return error != null;
    }
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onCancelled() called if the cancel button is
	 * pressed
	 */
	@Override
	protected void onCancelled() {
		super.onCancelled();
		Context context = getCallingContext();
        if (context == null) {
            Log.d(AbstractAsyncTask.class.getSimpleName(), "skipping post-exec handler for task "
                    + hashCode() + " (context is null)");
            return;
        }
        
		if (contextIsBaseActivity) {
            Activity activity = (Activity) UIUtilities.getContextIfChildActivity(context);
            if (dialogId > -1) {
                activity.removeDialog(dialogId);
            }
            if (isTitleProgressEnabled) {
                activity.setProgressBarVisibility(false);
            } else if (isTitleProgressIndeterminateEnabled) {
                activity.setProgressBarIndeterminateVisibility(false);
            }
        }
	}

    /**
     * Use a BaseAsyncTaskCallable instead of overriding doCheckedInBackground()
     *
     * @param callable
     */
    public void setCallable(AbstractAsyncTaskCallable<ParameterT, ProgressT, ReturnT> callable) {
        this.callable = callable;
    }
}
