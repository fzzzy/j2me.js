package com.nokia.mid.ui.frameanimator;

public class FrameAnimator {

    public static final int FRAME_ANIMATOR_MAX_CONCURRENT = 5;
    public static final int FRAME_ANIMATOR_VERTICAL = 0;
    public static final int FRAME_ANIMATOR_HORIZONTAL = 1;
    public static final int FRAME_ANIMATOR_FREE_ANGLE = 2;
    public static final int FRAME_ANIMATOR_FRICTION_LOW = 0;
    public static final int FRAME_ANIMATOR_FRICTION_MEDIUM = 1;
    public static final int FRAME_ANIMATOR_FRICTION_HIGH = 2;

    protected int actionType;
    protected int actionID;

    protected static int _numRegistered;

    protected FrameAnimatorListener _registered;

    public FrameAnimator() {
        _registered = null;
    }

    public boolean register(int x, int y, short maxFps, short maxPps, FrameAnimatorListener listener) {
        _registered = listener;
        _numRegistered++;
        return true;
    }

    public void unregister() {
        _registered = null;
        _numRegistered--;
    }

    public void drag(int x, int y) {
        _registered.animate(this, x, y, (short)0, (short)0, (short)0, true);
    }

    public void kineticScroll(int speed, int direction, int friction, float angle) {
        _registered.animate(this, 0, 0, (short)0, (short)0, (short)0, true);
    }

    public void limitedKineticScroll(int speed, int direction, int friction, float angle, int limitUp, int limitDown) {
        _registered.animate(this, 0, 0, (short)0, (short)0, (short)0, true);
    }

    public void stop() {
        // Do nothing; all animations in this implementation are currently synchronous
    }

    public boolean isRegistered() {
        return _registered != null;
    }

    public static int getNumRegisteredFrameAnimators() {
        return _numRegistered;
    }

}
