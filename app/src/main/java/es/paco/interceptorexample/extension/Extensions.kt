package es.paco.interceptorexample.extension

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import es.paco.interceptorexample.ui.base.BaseActivity
import es.paco.interceptorexample.ui.base.BaseFragment
import java.io.Serializable
import java.text.Collator

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible(show: Boolean) {
    if (show) {
        visible()
    } else {
        gone()
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun BaseActivity<*>.toast(message: String) {
    baseContext.toast(message)
}

fun BaseFragment<*>.toast(message: String) {
    context?.toast(message)
}

fun ViewPager2.next() {
    currentItem += 1
}

fun ViewPager2.previous() {
    currentItem -= 1
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


//https://developer.android.com/reference/android/util/Log.html
//is thrown if the tag.length() > 23 for Nougat (7.0) and prior releases (API <= 25), there is no tag limit of concern after this API level.
val Any.TAG: String
    get() {
        val tagSimpleName = javaClass.simpleName
        val tagName = javaClass.name
        return when {
            tagSimpleName.isNotBlank() -> {
                if (tagSimpleName.length > 23) {
                    tagSimpleName.takeLast(23)
                } else {
                    tagSimpleName
                }
            }
            tagName.isNotBlank() -> {
                if (tagName.length > 23) {
                    tagName.takeLast(23)
                } else {
                    tagName
                }
            }
            else -> {
                "TAG unknow"
            }
        }
    }

fun Context.pxToDp(px: Int): Float {
    return (px / resources.displayMetrics.density)
}

fun Context.dpToPx(dp: Int): Float {
    return (dp * resources.displayMetrics.density)
}

fun Context.displayHeightInPixel(): Int {
    return resources.displayMetrics.heightPixels
}

fun Context.displayWidthInPixel(): Int {
    return resources.displayMetrics.widthPixels
}

fun String.toStringOrNull(): String? {
    var toString = toString()
    return if (toString.equals("null", ignoreCase = true)) {
        null
    } else {
        toString
    }
}

fun Double.toStringOrNull(): String? {
    var toString = toString()
    return if (toString.equals("null", ignoreCase = true)) {
        null
    } else {
        toString
    }
}

fun Int.toStringOrNull(): String? {
    val toString = toString()
    return if (toString.equals("null", ignoreCase = true)) {
        null
    } else {
        toString
    }
}

fun String.equalsTo(text: String, strength: Int = Collator.PRIMARY): Boolean {
    val insenstiveStringComparator = Collator.getInstance()
    insenstiveStringComparator.strength = strength
    return insenstiveStringComparator.compare(this, text) == 0
}

fun <T> ArrayList<T>.getFirstOf(input: T?) = input?.let { i -> this.firstOrNull { it == i } }

fun <T : Serializable?> Intent.getSerializable(key: String, m_class: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializableExtra(key, m_class)!!
    else
        this.getSerializableExtra(key) as T
}

fun <T : Serializable?> Bundle.getSerializable(key: String, m_class: Class<T>): T {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.getSerializable(key, m_class)!!
    else
        this.getSerializable(key) as T
}

fun View.showSnack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, length).show()
}