package com.atomicbrawlers.customjoystick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Matt on 7/31/2017.
 */

public class JoystickView extends View /*implements Runnable*/{

    /* ATTRIBUTES
     * layoutSize - The size of the view/layout; must be a square; value will be used to change
     *      layout_width and layout_height to the same value
     * canvasPadding - The size of the padding around the painted object; the edge of the
     *      layout/view will be this far away from the edge of the painted object
     * backgroundColor - The color of the background
     * borderColor - The color of the border of the background
     * borderSize - The size/amount that the border sticks out over the background
     * thumbstickRadius - The radius of the thumbstick; the object that the user moves around
     * autoRecenterThumbstick - Boolean; The option to have the thumbstick automatically recenter itself
     *      after the user lets go of it.
     * autoRecenterDelay - The option to delay the recentering of the thumbstick; will only work if
     *      autoRecenterThumbstick is true.
     * physicalThreshold - A value from 0-100 representing the percentage the thumbstick must be
     *      pressed before the value output from the joystick will affect the object being
     *      controlled. The purpose of this is to eliminate the need to account for the error that a
     *      real-life thumbstick has in the form of "bounce-back." For example, if you are using the
     *      joystick to run a motor, and the motor doesn't start to turn until 30%, you would set
     *      the threshold to 30 and when the joystick moves it will start at a value of 30.
     * giveThreshold - A value from 0-100 representing the percentage the thumbstick must be pressed
     *      before the joystick values increases above 0. Think of it as the amount you have to
     *      "inch up" with your finger before the object being controlled starts to move.
     * enabled - PROBABLY DON'T NEED BECAUSE ANDROID COMES WITH THIS; JUST CHECK THE STATE OF THE
     *      DEFAULT OPTION FOR THIS [MAYBE]
     * doubleTapToPress - Boolean; Option to double tap the joystick to act a button press
     * doubleTapDelay - The amount of time, in milliseconds, the user has to tap a second time in
     *      order to register a double tap
     * doubleTapError - The amount of time, in milliseconds, the program will wait until looking for
     *      a second tap. MUST BE LESS THAN doubleTapDelay or will default to 10 less than, or a
     *      minimum overall value of [to be determined]
     * refreshRate - the rate at which the view/layout updates in milliseconds; I think this uses
     *      thread.sleep(); do more research
     */

    /* VARIABLES
     * mLayoutSize
     * mCanvasPadding
     * mBackgroundColor
     * mBorderColor
     * mBorderSize
     * mThumbstickRadius
     * mAutoRecenterThumbstick
     * mAutoRecenterDelay
     * mPhysicalThreshold
     * mGiveThreshold
     * mEnabled - MAYBE
     * mDoubleTapToPress
     * mDoubleTapDelay
     * mDoubleTapError
     * mRefreshRate
     *
     * DEFAULT_LAYOUT_SIZE
     * DEFAULT_CANVAS_PADDING
     * DEFAULT_BACKGROUND_COLOR
     * DEFAULT_BORDER_COLOR
     * DEFAULT_BORDER_SIZE
     * DEFAULT_THUMBSTICK_RADIUS
     * DEFAULT_AUTO_RECENTER_THUMBSTICK
     * DEFAULT_AUTO_RECENTER_DELAY
     * DEFAULT_PHYSICAL_THRESHOLD
     * DEFAULT_GIVE_THRESHOLD
     * DEFAULT_ENABLED
     * DEFAULT_DOUBLE_TAP_TO_PRESS
     * DEFAULT_DOUBLE_TAP_DELAY
     * DEFAULT_DOUBLE_TAP_ERROR
     * DEFAULT_REFRESH_RATE
     * CLOCKWISE = true;
     *
     * xPos
     * yPos
     * distanceFromCenter - acts as "strength" in a sense; useful for diagonal movements
     * angle
     */

    /* ADDITIONAL CONSIDERATIONS
     * Default starting pos option - would require getter and multiple position setters
     */

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /* GETTERS
     * TODO: Determine whether getters for attribute values are needed or if there is a built-in way
     *
     * getXPos
     * getYPos
     * getDistanceFromCenter
     * getAngle
     * getAngle(boolean clockwise) - option to make counter-clockwise the positive direction
     */

    /* SETTERS
     * TODO: Determine whether setters for attribute values are need or if there is a built-in way
     */
}
