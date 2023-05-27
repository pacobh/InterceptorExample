package es.paco.interceptorexample.ui.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import es.paco.interceptorexample.R
import es.paco.interceptorexample.extension.TAG
import es.paco.interceptorexample.extension.gone
import es.paco.interceptorexample.extension.visible
import es.paco.interceptorexample.ui.dialogfragment.loading.LoadingDialogFragment
import es.paco.interceptorexample.ui.dialogfragment.loading.LoadingDialogFragment.Companion.LOADING_DIALOG_FRAGMENT_TAG
import es.paco.interceptorexample.ui.dialogfragment.warning.WarningDialogFragment
import es.paco.interceptorexample.ui.dialogfragment.warning.WarningDialogFragment.Companion.ERROR_DIALOG_FRAGMENT_TAG
import javax.inject.Inject


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(), View.OnClickListener {

    enum class BottomMenuSection {
        HOME, PATRIMONY, BROKER, FUNDS
    }

    enum class AlertsSection(val code: String) {
        HOME("home")
    }

    lateinit var binding: B

    private var isKeyboardVisible = false
    private var tbToolbar: Toolbar? = null
    private var ibToolbarBack: ImageButton? = null
    private var tvToolbarTitle: TextView? = null

    private var loadingDialogFragment: LoadingDialogFragment = LoadingDialogFragment()
    private var warningDialogFragment: WarningDialogFragment = WarningDialogFragment()

    private var fail401 = false

    @Inject
    lateinit var baseActivityControlShowLoading: BaseActivityControlShowLoading

    override fun onResume() {
        super.onResume()
        configureToolbar()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateBinding()
        setContentView(binding.root)
        findViewByIdToolbar()
        setListenerKeyboardIsVisible()
        setupViewModel()
        observeViewModel()
        createAfterInflateBindingSetupObserverViewModel(savedInstanceState)
        setListenersClickToolbarButtons()
        fixProblemAddFragmentNotCallConfigureToolbar()
    }

    private fun fixProblemAddFragmentNotCallConfigureToolbar() {
        supportFragmentManager.addOnBackStackChangedListener {
            val numbersFragments = supportFragmentManager.fragments.size
            if (numbersFragments > 0) {
                var calledConfigureToolbar = false
                var nextFragment = numbersFragments - 1

                while (!calledConfigureToolbar && nextFragment >= 0) {
                    if (supportFragmentManager.fragments[nextFragment] is BaseFragment<*>) {
                        (supportFragmentManager.fragments[nextFragment] as BaseFragment<*>).configureToolbar()
                        calledConfigureToolbar = true
                    }
                    nextFragment--
                }
            }
        }
    }

    private fun findViewByIdToolbar() {
        tbToolbar = findViewById(R.id.tbToolbar)
        ibToolbarBack = findViewById(R.id.ibToolbarBack)
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle)
    }

    private fun setListenerKeyboardIsVisible() {
        val rootViewLayout = window.decorView.rootView
        rootViewLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rootViewLayout.getWindowVisibleDisplayFrame(r)
            val screenHeight = rootViewLayout.rootView.height
            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            val keypadHeight = screenHeight - r.bottom
            //Log.d(TAG, "l> keypadHeight = $keypadHeight")
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                // keyboard is opened
                if (!isKeyboardVisible) {
                    isKeyboardVisible = true
                }
            } else {
                // keyboard is closed
                if (isKeyboardVisible) {
                    isKeyboardVisible = false
                }
            }
        }
    }

    private fun setListenersClickToolbarButtons() {
        ibToolbarBack?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ibToolbarBack -> clickToolbarBack()
        }
    }

    protected open fun clickToolbarBack() {
        onBackPressed()
    }

    fun popAllPreviousFragments() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
    }

    fun goBackNumberSteps(steps: Int) {
        var counter = steps
        if (supportFragmentManager.fragments.size > 0 && supportFragmentManager.fragments.size >= steps) {
            while (counter > 0) {
                counter--
                onBackPressed()
            }
        }
    }

    fun existsFragmentWithTAG(tag: String): Boolean {
        return supportFragmentManager.findFragmentByTag(tag) != null
    }

    fun removeFragmentByTag(tag: String) {
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(tag)
        if (findFragmentByTag != null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.remove(findFragmentByTag)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.commit()
        }
    }

    fun isKeyboardVisible(): Boolean {
        return isKeyboardVisible
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }

    fun hideToolbar() {
        tbToolbar?.gone()
    }

    private fun hideAllElementToolbar() {
        ibToolbarBack?.gone()
        tvToolbarTitle?.gone()
    }

    fun showToolbar(
        showBack: Boolean = false,
        title: String = "",
    ) {
        hideAllElementToolbar()
        tbToolbar?.visible()
        if (showBack) {
            ibToolbarBack?.visible()
        }
        if (title.isNotBlank()) {
            tvToolbarTitle?.visible()
            tvToolbarTitle?.text = title
        }
    }

    fun updateShowToolbarBack(showBack: Boolean) {
        if (showBack) {
            ibToolbarBack?.visible()
        } else {
            ibToolbarBack?.gone()
        }
    }

    fun updateShowToolbarTitle(title: String) {
        if (title.isNotBlank()) {
            tvToolbarTitle?.visible()
            tvToolbarTitle?.text = title
        } else {
            tvToolbarTitle?.gone()
        }
    }

    fun getToolbarTitleLowerCase(): String {
        return if (tvToolbarTitle != null && tvToolbarTitle?.text != null) {
            tvToolbarTitle?.text.toString().lowercase()
        } else {
            ""
        }
    }

    fun showLoading(show: Boolean) {
        if (show) {
            if (baseActivityControlShowLoading.canShowLoading(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)) {
                loadingDialogFragment.show(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)
            }
        } else {
            if (baseActivityControlShowLoading.canHideLoading(supportFragmentManager, LOADING_DIALOG_FRAGMENT_TAG)) {
                loadingDialogFragment.dismiss()
            }
        }
    }

    fun showErrorInformativeOnly(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
        showErrorInformativeOnly(errorModel,
            object : WarningDialogFragment.WarningDialogFragmentListener {
                override fun onWarningDialogFragmentClickClose() = Unit
                override fun onWarningDialogFragmentClickButton() = Unit
            }
        )
    }

    fun showErrorInformativeOnly(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel, warningDialogFragmentListener: WarningDialogFragment.WarningDialogFragmentListener) {
        if (errorModel.errorCode == "401") {
            Log.e(TAG, "l> Problema de session codigo 401")
        } else {
            if (supportFragmentManager.findFragmentByTag(ERROR_DIALOG_FRAGMENT_TAG) == null) {
                warningDialogFragment = WarningDialogFragment()
                warningDialogFragment.setValue(
                    warningDialogFragmentListener,
                    errorModel,
                    ResourcesCompat.getDrawable(resources, R.drawable.warning, null),
                    getString(R.string.generic_error_popup_button_accept),
                    true
                )
                showDialogFragment(warningDialogFragment, ERROR_DIALOG_FRAGMENT_TAG)
            } else {
                warningDialogFragment.setErrorAndRefreshInfo(errorModel)
            }
        }
    }

    fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            dialogFragment.show(supportFragmentManager, tag)
        }
    }

    fun setClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied text", text)
        clipboard.setPrimaryClip(clip)
    }

    abstract fun inflateBinding()
    abstract fun setupViewModel()
    abstract fun observeViewModel()
    abstract fun createAfterInflateBindingSetupObserverViewModel(savedInstanceState: Bundle?)
    abstract fun configureToolbar()
    abstract fun needRefreshTokenInViewModel()
}