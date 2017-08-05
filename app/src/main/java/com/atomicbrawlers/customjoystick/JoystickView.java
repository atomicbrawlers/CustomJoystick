package com.atomicbrawlers.customjoystick;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.R.attr.direction;
import static android.R.attr.radius;
import static android.R.attr.x;
import static android.os.Build.VERSION_CODES.M;
import static android.provider.ContactsContract.CommonDataKinds.StructuredPostal.REGION;

/**
 * Created by Matt on 7/31/2017.
 */

/* JAVADOC EXAMPLE
 * Returns an Image object that can then be painted on the screen.
 * The url argument must specify an absolute {@link URL}. The name
 * argument is a specifier that is relative to the url argument.
 * <p>
 * This method always returns immediately, whether or not the
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives
 * that draw the image will incrementally paint on the screen.
 *
 * @param  url  an absolute URL giving the base location of the image
 * @param  name the location of the image, relative to the url argument
 * @return      the image at the specified URL
 * @see         Image
 */

/**
 * Custom Joystick View
 * @author Matthew Jordan
 */

//TODO: Figure out another way to avoid the thumbstick clipping other than android:clipChildren="false" in the constraint layout

public class JoystickView extends View implements Runnable{

    /* ATTRIBUTES
     * layoutSize - The size of the view/layout; must be a square; value will be used to change
     *      layout_width and layout_height to the same value
     * canvasPadding - The size of the padding around the painted object; the edge of the
     *      layout/view will be this far away from the edge of the painted object
     * joystickColor - The color of the background; TODO: Check if I can use the default android implementation of this
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

    /* ADDITIONAL CONSIDERATIONS
     * Default starting pos option - would require getter and multiple position setters
     */

    /*
     CONSTANTS
     */

    //TODO: Determine what the default values will be

    /**
     * Default layout size
     */
    private final int DEFAULT_LAYOUT_SIZE = 200;

    /**
     * Default canvas padding size
     */
    private final int DEFAULT_CANVAS_PADDING = 10;

    /**
     * Default background color
     */
    private final int DEFAULT_JOYSTICK_COLOR = 0;

    /**
     * Default border color
     */
    private final int DEFAULT_BORDER_COLOR = 0;

    /**
     * Default border size
     */
    private final int DEFAULT_BORDER_SIZE = 10;

    //TODO: Javadoc this
    private final int DEFUALT_THUMBSTICK_COLOR = 0;

    /**
     * Default thumbstick radius
     */
    private final int DEFAULT_THUMBSTICK_RADIUS = 30;

    /**
     * Default state for automatically recentering thumbstick
     */
    private final boolean DEFAULT_AUTO_RECENTER_THUMBSTICK = true;

    /**
     * Default auto recenter delay time
     */
    private final int DEFAULT_AUTO_RECENTER_DELAY = 10;

    /**
     * Default physical threshold value
     */
    private final int DEFAULT_PHYSICAL_THRESHOLD = 20;

    /**
     * Default give threshold value
     */
    private final int DEFAULT_GIVE_THRESHOLD = 5;

    /**
     * Default enabled state
     */
    private final boolean DEFAULT_ENABLED = true;

    /**
     * Default state for double tap option
     */
    private final boolean DEFAULT_DOUBLE_TAP_TO_PRESS = false;

    /**
     * Default double tap delay time
     */
    private final int DEFAULT_DOUBLE_TAP_DELAY = 200; //milliseconds

    /**
     * Default double tap error time
     */
    private final int DEFAULT_DOUBLE_TAP_ERROR = 50; //milliseconds

    /**
     * Default refresh rate time
     */
    private final int DEFAULT_REFRESH_RATE = 20; //milliseconds

    /*
    SETTINGS VARIABLES
     */

    /**
     * Size of layout.<!-- --> Layout is square so this represents both the width and height
     */
    private int mLayoutSize;

    /**
     * Size in pixels of how far apart the edge of the painted object and layout will be
     */
    private int mCanvasPadding;

    /**
     * Color of the joystick background
     */
    private int mJoystickColor;

    /**
     * Color of the joystick border
     */
    private int mBorderColor;

    /**
     * Size in pixels of the border around the background.<!-- --> The border is drawn inward
     * from the edge of the background.
     */
    private int mBorderSize;

    //TODO: Javadoc this
    private int mThumbstickColor;

    /**
     * Radius in pixels of the thumbstick
     */
    private int mThumbstickRadius;

    /**
     * State for whether or not to automatically recenter thumbstick upon not being touched
     */
    private boolean mAutoRecenterThumbstick;

    /**
     * Delay in milliseconds before the thumbstick is automatically recentered
     */
    private int mAutoRecenterDelay;

    /**
     * Value from 0-100 representing the percentage the thumbstick must be
     * pressed before the value output from the joystick will affect the object being
     * controlled.
     * <p>
     * The purpose of this is to eliminate the need to account for the error that a
     * real-life thumbstick has in the form of "bounce-back." </p>
     * <p>
     * For example, if you are using the
     * joystick to run a motor, and the motor doesn't start to turn until 30%, you would set
     * the threshold to 30 and when the joystick moves it will start at a value of 30.
     * </p>
     */
    private int mPhysicalThreshold;

    /**
     * Value from 0-100 representing the percentage the thumbstick must be pressed
     * before the joystick values increases above 0.
     * <p>
     * Think of it as the amount you have to "inch up" with your finger before the object
     * being controlled starts to move.
     * </p>
     */
    private int mGiveThreshold;

    /**
     * State for whether or not the joystick is interact-able (enabled)
     */
    private boolean mEnabled; //MAYBE

    /**
     * State for whether or not the user can double tap the joystick to act a button press
     */
    private boolean mDoubleTapToPress;

    /**
     * Value in milliseconds the user has to tap a second time in order to register a double tap
     */
    private int mDoubleTapDelay;

    /**
     * Value in milliseconds, the program will wait before looking for a second tap.
     * <p>
     * <em>MUST BE LESS THAN</em> doubleTapDelay or will default to 10 less than the default value
     */
    private int mDoubleTapError;

    /**
     * Value in milliseconds representing the refresh rate of the joystick object
     */
    private int mRefreshRate;

    /*
     LOCAL VARIABLES
     */

    /**
     * Value of the x-axis position of the thumbstick
     */
    private int xPos;

    /**
     * Value of the y-axis position of the thumbstick
     */
    private int yPos;

    /**
     * How from the the center the thumbstick is
     */
    private float distanceFromCenter; //acts as "strength" in a sense; useful for diagonal movements

    /**
     * Angle of the thumbstick.<!-- --> Positive x-axis is zero.<!-- --> Default angle is
     * referenced clockwise.
     */
    private int angle;

    //TODO: Javadoc this
    private int layoutSize;
    private int center;
    private float joystickRadius;
    private int relativePosX;
    private int relativePosY;

    /*
     DRAWING VARIABLES
     Used to draw the image of the joystick
     */
    private Paint painter;

    private Thread mThread = new Thread(this);

    public void run() {
        while (!Thread.interrupted()) {
            System.out.println("X: " + xPos + "  Y: " + yPos);
            System.out.println("distance from center: " + distanceFromCenter);
            System.out.println("Joystick Radius: " + joystickRadius);
            try {
                Thread.sleep(DEFAULT_REFRESH_RATE);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public JoystickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //TODO: Figure out how to upgrade API if you want to use this
        //Overlapping rendering is for when a canvas is rendered opaquely on top of another canvas
        //this.forceHasOverlappingRendering(false); //Saves processing power

        //paint objects for drawing in onDraw
        painter = new Paint();

        //get the attributes specified in attrs.xml
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.JoystickView, 0, 0);

        try { //get attributes specified in attrs.xml
            mLayoutSize             = styledAttributes.getDimensionPixelSize(R.styleable.JoystickView_layoutSize,       DEFAULT_LAYOUT_SIZE);
            mCanvasPadding          = styledAttributes.getDimensionPixelSize(R.styleable.JoystickView_canvasPadding,    DEFAULT_CANVAS_PADDING);
            mBorderSize             = styledAttributes.getDimensionPixelSize(R.styleable.JoystickView_borderSize,       DEFAULT_BORDER_SIZE);
            mThumbstickRadius       = styledAttributes.getDimensionPixelSize(R.styleable.JoystickView_thumbstickRadius, DEFAULT_THUMBSTICK_RADIUS);
            mAutoRecenterThumbstick = styledAttributes.getBoolean(R.styleable.JoystickView_autoRecenterThumbstick, DEFAULT_AUTO_RECENTER_THUMBSTICK);
            mEnabled                = styledAttributes.getBoolean(R.styleable.JoystickView_enabled,                DEFAULT_ENABLED);
            mDoubleTapToPress       = styledAttributes.getBoolean(R.styleable.JoystickView_doubleTapToPress,       DEFAULT_DOUBLE_TAP_TO_PRESS);
            mAutoRecenterDelay      = styledAttributes.getInteger(R.styleable.JoystickView_autoRecenterDelay, DEFAULT_AUTO_RECENTER_DELAY);
            mPhysicalThreshold      = styledAttributes.getInteger(R.styleable.JoystickView_physicalThreshold, DEFAULT_PHYSICAL_THRESHOLD);
            mGiveThreshold          = styledAttributes.getInteger(R.styleable.JoystickView_giveThreshold,     DEFAULT_GIVE_THRESHOLD);
            mDoubleTapDelay         = styledAttributes.getInteger(R.styleable.JoystickView_doubleTapDelay,    DEFAULT_DOUBLE_TAP_DELAY);
            mDoubleTapError         = styledAttributes.getInteger(R.styleable.JoystickView_doubleTapError,    DEFAULT_DOUBLE_TAP_ERROR);
            mRefreshRate            = styledAttributes.getInteger(R.styleable.JoystickView_refreshRate,       DEFAULT_REFRESH_RATE);
            mJoystickColor          = styledAttributes.getColor(R.styleable.JoystickView_joystickColor,   DEFAULT_JOYSTICK_COLOR);
            mBorderColor            = styledAttributes.getColor(R.styleable.JoystickView_borderColor,     DEFAULT_BORDER_COLOR);
            mThumbstickColor        = styledAttributes.getColor(R.styleable.JoystickView_thumbstickColor, DEFUALT_THUMBSTICK_COLOR);
        } finally {
            styledAttributes.recycle();
        }

        center = mLayoutSize / 2;
        xPos = center;
        yPos = center;
        joystickRadius = (float) (mLayoutSize - mCanvasPadding) / 2;
        relativePosX = center;
        relativePosY = center;

        mThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        center = layoutSize / 2;
        xPos = center;
        yPos = center;
        relativePosX = center;
        relativePosY = center;

        joystickRadius = (float) (Math.min(w, h) - mCanvasPadding) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas){
        //represents half the width of the layout
        int halfLayoutWidth = layoutSize / 2;

        //Prepare the painter
        painter.setStyle(Style.FILL);
        painter.setAntiAlias(true);

        //radius of the circle is half the size of the layout minus half the size of the padding
        int radius = halfLayoutWidth - (mCanvasPadding / 2); //background radius

        //Draw the joystick border
        painter.setColor(mBorderColor);
        canvas.drawCircle(halfLayoutWidth, halfLayoutWidth, radius, painter);

        //Draw the joystick background
        painter.setColor(mJoystickColor);
        canvas.drawCircle(halfLayoutWidth, halfLayoutWidth, (radius - mBorderSize), painter);

        //TODO: This is the only circle required to be redrawn, see if I can take it out of onDraw
        //Draw the thumbstick
        painter.setColor(mThumbstickColor);
        canvas.drawCircle(xPos, yPos, mThumbstickRadius, painter);
    }

    //TODO: Make this process smoother - currently it is glitchy and I don't fully understand it
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // setting the measured values to resize the view to a certain width and height
        int size = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));
        setMeasuredDimension(size, size);

        layoutSize = size;
    }


    private int measure(int measureSpec) {
        if (MeasureSpec.getMode(measureSpec) == MeasureSpec.UNSPECIFIED) {
            // if no bounds are specified return a default size (200)
            return DEFAULT_LAYOUT_SIZE;
        } else {
            // As you want to fill the available space
            // always return the full available bounds.
            return MeasureSpec.getSize(measureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xPos = (int) event.getX();
        yPos = (int) event.getY();

        relativePosX = xPos - center;
        relativePosY = yPos - center;

        distanceFromCenter = (float) Math.sqrt((float)(Math.pow(relativePosX,2) + Math.pow(relativePosY,2)));

        //Math to make the thumbstick not be drawn outside the bounds of the joystick
        if (distanceFromCenter > joystickRadius) {
            xPos = (int) (relativePosX * joystickRadius / distanceFromCenter + center);
            yPos = (int) (relativePosY * joystickRadius / distanceFromCenter + center);
        }

        invalidate();

        return true;
    }

    /*
    GETTERS
    TODO: Determine whether getters for attribute values are needed or if there is a built-in way
     */

    /**
     * @return the x-axis value of the thumbstick
     */
    public int getXPos(){
         return xPos;
    }

    /**
     * @return the y-axis value of the thumbstick
     */
    public int getYPos(){
         return yPos;
    }


    /**
     * @return the distance the thumbstick is from the center of the joystick
     */
    public float getDistanceFromCenter(){
         return distanceFromCenter;
    }

    /**
     * @return the angle (relative clockwise) the thumbstick is at
     */
    public int getAngle(){
         return angle;
    }

    /**
     * @param clockwise option to make counter-clockwise the relative direction
     * @return the angle the thumbstick is at based off the parameter
     */
    public int getAngle(boolean clockwise){
         if (clockwise) {
             return angle;
         } else {
             return 360 - angle;
         }
    }

    /* SETTERS
     * TODO: Determine whether setters for attribute values are need or if there is a built-in way
     */
}
