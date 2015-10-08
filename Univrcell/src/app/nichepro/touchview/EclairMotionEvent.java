/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.touchview;

import android.view.MotionEvent;

public class EclairMotionEvent extends WrapMotionEvent {

    protected EclairMotionEvent(MotionEvent event) {
        super(event);
    }

    public float getX(int pointerIndex) {
        return event.getX(pointerIndex);
    }

    public float getY(int pointerIndex) {
        return event.getY(pointerIndex);
    }

    public int getPointerCount() {
        return event.getPointerCount();
    }

    public int getPointerId(int pointerIndex) {
        return event.getPointerId(pointerIndex);
    }
}