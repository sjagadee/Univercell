package easing.interpolator;

/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

import android.view.animation.Interpolator;
import easing.interpolator.EasingType.Type;


public class CubicInterpolator implements Interpolator {

	private Type type;

	public CubicInterpolator(Type type) {
		this.type = type;
	}

	public float getInterpolation(float t) {
		if (type == Type.IN) {
			return in(t);
		} else
		if (type == Type.OUT) {
			return out(t);
		} else
		if (type == Type.INOUT) {
			return inout(t);
		}
		return 0;
	}

	private float in(float t) {
		return t*t*t;
	}
	private float out(float t) {
		return (t-=1)*t*t + 1;
	}
	private float inout(float t) {
		t *= 2;
		if (t < 1) {
			return 0.5f*t*t*t;
		} else {
			return 0.5f*((t-=2)*t*t + 2);
		}
	}
}