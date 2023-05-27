package es.paco.interceptorexample.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import es.paco.interceptorexample.ui.dialogfragment.warning.WarningDialogFragment

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    var binding: B? = null

    lateinit var baseActivity: BaseActivity<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity<*>
    }

    override fun onResume() {
        super.onResume()
        configureToolbar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        callViewModelSaveData()
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inflateBinding()
        createViewAfterInflateBinding(inflater, container, savedInstanceState)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeViewModel()
        viewCreatedAfterSetupObserverViewModel(view, savedInstanceState)
    }

    fun hideToolbar() {
        baseActivity.hideToolbar()
    }

    fun showToolbar(
        showBack: Boolean = false,
        title: String = "",
    ) {
        baseActivity.showToolbar(
            showBack,
            title,
        )
    }

    fun updateShowToolbarBack(showBack: Boolean) {
        baseActivity.updateShowToolbarBack(showBack)
    }

    fun updateShowToolbarTitle(title: String) {
        baseActivity.updateShowToolbarTitle(title)
    }

    fun showLoading(show: Boolean) {
        baseActivity.showLoading(show)
    }

    fun showError(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
        baseActivity.showErrorInformativeOnly(errorModel)
    }

    fun showError(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel, warningDialogFragmentListener: WarningDialogFragment.WarningDialogFragmentListener) {
        baseActivity.showErrorInformativeOnly(errorModel, warningDialogFragmentListener)
    }

    fun showErrorInformativeOnly(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
        baseActivity.showErrorInformativeOnly(errorModel)
    }

    fun showErrorInformativeOnly(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel, warningDialogFragmentListener: WarningDialogFragment.WarningDialogFragmentListener) {
        baseActivity.showErrorInformativeOnly(errorModel, warningDialogFragmentListener)
    }

    fun showDialogFragment(dialogFragment: DialogFragment, tag: String) {
        baseActivity.showDialogFragment(dialogFragment, tag)
    }

    fun removeFragmentByTag(tag: String) {
        baseActivity.removeFragmentByTag(tag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun setClipboard(context: Context, text: String) {
        baseActivity.setClipboard(context, text)
    }

    abstract fun inflateBinding()
    abstract fun createViewAfterInflateBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    abstract fun setupViewModel()
    abstract fun callViewModelSaveData()
    abstract fun observeViewModel()
    abstract fun viewCreatedAfterSetupObserverViewModel(view: View, savedInstanceState: Bundle?)
    abstract fun configureToolbar()
}