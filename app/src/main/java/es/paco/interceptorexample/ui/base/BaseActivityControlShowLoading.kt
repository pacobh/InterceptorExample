package es.paco.interceptorexample.ui.base

import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseActivityControlShowLoading @Inject constructor() {

    private var loadingIsShowing: Boolean = false

    fun canShowLoading(fragmentManager: FragmentManager, tag: String): Boolean {
        val canShow = if (loadingIsShowing) {
            false
        } else {
            fragmentManager.findFragmentByTag(tag) == null
        }
        if (canShow) {
            loadingIsShowing = true
        }
        return canShow
    }

    fun canHideLoading(fragmentManager: FragmentManager, tag: String): Boolean {
        val canHide = fragmentManager.findFragmentByTag(tag) != null
        if (canHide) {
            loadingIsShowing = false
        }
        return canHide
    }

}