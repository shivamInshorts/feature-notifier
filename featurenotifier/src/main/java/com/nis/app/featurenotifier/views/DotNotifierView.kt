package com.nis.app.featurenotifier.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.nis.app.featurenotifier.R
import kotlin.math.min

class DotNotifierView : AppCompatImageView {

    private val mBitmapPaint = Paint()
    private val mImageAlpha: Int = DEFAULT_IMAGE_ALPHA
    private var mBitmapCanvas: Canvas? = null
    private var mBitmap: Bitmap? = null
    private var mDrawableRadius = 0f
    private val mDrawableRect = RectF()
    private val mShaderMatrix = Matrix()
    private val mColorFilter: ColorFilter? = null

    private var mInitialized = false
    private var mDrawableDirty = false
    private var mRebuildShader = false

    private var blinkStatus: Boolean = DEF_BLINK_STATUS
    private var blinkDuration: Int = DEF_BLINK_DURATION
    private var blinkLength: Int = DEF_BLINK_LENGTH

    constructor(context: Context) : super(context) {
        initialise();
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.DotNotifierView)
        blinkStatus = typedArray.getBoolean(R.styleable.DotNotifierView_dnv_blink, false)
        blinkLength = typedArray.getInt(R.styleable.DotNotifierView_dnv_blink_length, 3)
        blinkDuration = typedArray.getInt(R.styleable.DotNotifierView_dnv_blink_duration, 5)
        typedArray.recycle()
        initialise()
    }

    private fun initialise() {
        mInitialized = true
        setImageResource(R.color.red);
        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.isDither = true
        mBitmapPaint.isFilterBitmap = true
        mBitmapPaint.alpha = mImageAlpha
        mBitmapPaint.colorFilter = mColorFilter

        outlineProvider = OutlineProvider()

    }

    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas) {

        if (mBitmap != null) {
            if (mDrawableDirty && mBitmapCanvas != null) {
                mDrawableDirty = false
                val drawable = drawable
                drawable.setBounds(0, 0, mBitmapCanvas!!.width, mBitmapCanvas!!.height)
                drawable.draw(mBitmapCanvas!!)
            }

            if (mRebuildShader) {
                mRebuildShader = false
                val bitmapShader =
                    BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                bitmapShader.setLocalMatrix(mShaderMatrix)
                mBitmapPaint.shader = bitmapShader
            }

            canvas.drawCircle(
                mDrawableRect.centerX(),
                mDrawableRect.centerY(),
                mDrawableRadius,
                mBitmapPaint
            )

        }

    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        initializeBitmap()
        invalidate()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
        invalidate()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
        invalidate()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateDimensions()
        invalidate()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateDimensions()
        invalidate()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        updateDimensions()
        invalidate()
    }

    override fun invalidateDrawable(dr: Drawable) {
        mDrawableDirty = true
        invalidate()
    }

    private fun initializeBitmap() {
        mBitmap = getBitmapFromDrawable(drawable)
        mBitmapCanvas = if (mBitmap != null && mBitmap!!.isMutable) {
            Canvas(mBitmap!!)
        } else {
            null
        }

        if (!mInitialized) {
            return
        }

        if (mBitmap != null) {
            updateShaderMatrix()
        } else {
            mBitmapPaint.shader = null
        }
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLOR_DRAWABLE_DIMENSION,
                    COLOR_DRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun updateDimensions() {
        mDrawableRect.set(calculateBounds())

        mDrawableRadius = min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f)
        updateShaderMatrix()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        if (mBitmap == null) {
            return
        }
        val scale: Float
        var dx = 0f
        var dy = 0f
        mShaderMatrix.set(null)
        val bitmapHeight = mBitmap!!.height
        val bitmapWidth = mBitmap!!.width
        if (bitmapWidth * mDrawableRect.height() > mDrawableRect.width() * bitmapHeight) {
            scale = mDrawableRect.height() / bitmapHeight.toFloat()
            dx = (mDrawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = mDrawableRect.width() / bitmapWidth.toFloat()
            dy = (mDrawableRect.height() - bitmapHeight * scale) * 0.5f
        }
        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + mDrawableRect.left,
            (dy + 0.5f).toInt() + mDrawableRect.top
        )
        mRebuildShader = true
    }

    companion object {
        private val DEF_DOT_COLOR = R.color.red // red
        private const val DEF_BLINK_STATUS = false // initial blink false
        private const val DEF_BLINK_DURATION = 5 // in seconds
        private const val DEF_BLINK_LENGTH = 3 // default blink radius in dp
        private const val DEFAULT_IMAGE_ALPHA = 255
        private const val DEF_HEIGHT = 20;
        private const val DEF_WIDTH = 20

        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_DIMENSION = 2
        private const val TAG = "DotNotifierView"
    }

    private class OutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val bounds = Rect()
            outline.setRoundRect(bounds, bounds.width() / 2.0f)
        }
    }
}