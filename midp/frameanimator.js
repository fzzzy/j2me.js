/* -*- Mode: Java; tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- */
/* vim: set shiftwidth=2 tabstop=2 autoindent cindent expandtab: */

'use strict';

var FrameAnimator = function() {};

FrameAnimator.numRegistered = 0;

FrameAnimator.prototype._isRegistered = false;

FrameAnimator.prototype.register = function(x, y, maxFps, maxPps, listener) {
  this.x = x;
  this.y = y;
  this.maxFps = maxFps;
  this.maxPps = maxPps;
  this.listener = listener;
  this._isRegistered = true;
  ++FrameAnimator.numRegistered;
};

FrameAnimator.prototype.unregister = function() {
  this.x = null;
  this.y = null;
  this.maxFps = null;
  this.maxPps = null;
  this.listener = null;
  this._isRegistered = false;
  --FrameAnimator.numRegistered;
};

FrameAnimator.prototype.isRegistered = function() {
  return this._isRegistered;
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.init.()V"] = function(ctx, stack) {
  var _this = stack.pop();
  _this.nativeObject = new FrameAnimator();
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.register.(IISSLcom/nokia/mid/ui/frameanimator/FrameAnimatorListener;)Z"] = function(ctx, stack) {
  var listener = stack.pop(), maxPps = stack.pop(), maxFps = stack.pop(), y = stack.pop(), x = stack.pop(), _this = stack.pop();

  if (_this.nativeObject.isRegistered()) {
    ctx.raiseExceptionAndYield("java/lang/IllegalStateException", "FrameAnimator already registered");
  }

  if (!listener) {
    ctx.raiseExceptionAndYield("java/lang/NullPointerException", "listener is null");
  }

  if (x < -65535 || x > 65535 || y < -65535 || y > 65535) {
    ctx.raiseExceptionAndYield("java/lang/IllegalArgumentException", "coordinate out of bounds");
  }

  // XXX return false if FrameAnimator.numRegistered >= FRAME_ANIMATOR_MAX_CONCURRENT

  _this.nativeObject.register(x, y, maxFps, maxPps, listener);
  stack.push(1);
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.unregister.()V"] = function(ctx, stack) {
  var _this = stack.pop();

  if (!_this.nativeObject.isRegistered()) {
    ctx.raiseExceptionAndYield("java/lang/IllegalStateException", "FrameAnimator not registered");
  }

  _this.nativeObject.unregister();
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.drag.(II)V"] = function(ctx, stack) {
  var y = stack.pop(), x = stack.pop(), _this = stack.pop();
  console.warn("FrameAnimator.drag(II)V not implemented (" + x + ", " + y + ")");
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.kineticScroll.(IIIF)V"] = function(ctx, stack) {
  var angle = stack.pop(), friction = stack.pop(), direction = stack.pop(), speed = stack.pop(), _this = stack.pop();
  console.warn("FrameAnimator.kineticScroll(IIIF)V not implemented (" +
               speed + ", " + direction + ", " + friction + ", " + angle + ")");
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.limitedKineticScroll.(IIIFII)V"] = function(ctx, stack) {
  var limitDown = stack.pop(), limitUp = stack.pop(), angle = stack.pop(), friction = stack.pop(),
      direction = stack.pop(), speed = stack.pop(), _this = stack.pop();
  console.warn("FrameAnimator.limitedKineticScroll(IIIFII)V not implemented (" +
               speed + ", " + direction + ", " + friction + ", " + angle + ", " + limitUp + ", " + limitDown + ")");
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.stop.()V"] = function(ctx, stack) {
  var _this = stack.pop();
  console.warn("FrameAnimator.stop()V not implemented");
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.isRegistered.()Z"] = function(ctx, stack) {
  var _this = stack.pop();
  stack.push(_this.nativeObject.isRegistered() ? 1 : 0);
};

Native["com/nokia/mid/ui/frameanimator/FrameAnimator.getNumRegisteredFrameAnimators.()I"] = function(ctx, stack) {
  stack.push(FrameAnimator.numRegistered);
};
