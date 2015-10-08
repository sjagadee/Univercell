package easing.interpolator;

/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

import android.view.animation.Interpolator;
import easing.interpolator.EasingType.Type;

public class BackInterpolator implements Interpolator {

	private Type type;
	private float overshot;

	public BackInterpolator(Type type, float overshot) {
		this.type = type;
		this.overshot = overshot;
	}

	public float getInterpolation(float t) {
		if (type == Type.IN) {
			return in(t, overshot);
		} else
		if (type == Type.OUT) {
			return out(t, overshot);
		} else
		if (type == Type.INOUT) {
			return inout(t, overshot);
		}
		return 0;
	}

	private float in(float t, float o) {
		if (o == 0) {
			o = 1.70158f;
		}
		return t*t*((o+1)*t - o);
	}

	private float out(float t, float o) {
		if (o == 0) {
			o = 1.70158f;
		}
		return ((t-=1)*t*((o+1)*t + o) + 1);
	}
	
	private float inout(float t, float o) {
		if (o == 0) {
			o = 1.70158f;
		}
		t *= 2;
		if (t < 1) {
			return 0.5f*(t*t*(((o*=(1.525))+1)*t - o));
		} else {
			return 0.5f*((t-=2)*t*(((o*=(1.525))+1)*t + o) + 2);
		}
	}
}
