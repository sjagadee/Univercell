package easing.interpolator;

/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

import android.view.animation.Interpolator;
import easing.interpolator.EasingType.Type;


public class SineInterpolator implements Interpolator {

	private Type type;

	public SineInterpolator(Type type) {
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
		return (float) (-Math.cos(t * (Math.PI/2)) + 1);
	}
	private float out(float t) {
		return (float) Math.sin(t * (Math.PI/2));
	}
	private float inout(float t) {
		return (float) (-0.5f * (Math.cos(Math.PI*t) - 1));
	}
}
