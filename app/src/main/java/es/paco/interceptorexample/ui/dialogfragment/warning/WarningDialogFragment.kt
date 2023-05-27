package es.paco.interceptorexample.ui.dialogfragment.warning

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import es.paco.interceptorexample.R
import es.paco.interceptorexample.databinding.FragmentDialogWarningBinding
import es.paco.interceptorexample.extension.gone
import es.paco.interceptorexample.extension.visible

class WarningDialogFragment : DialogFragment() {

    interface WarningDialogFragmentListener {
        fun onWarningDialogFragmentClickClose()
        fun onWarningDialogFragmentClickButton()
    }

    companion object {
        const val ERROR_DIALOG_FRAGMENT_TAG: String = "ERROR_DIALOG_FRAGMENT_TAG"
    }

    private lateinit var binding: FragmentDialogWarningBinding
    private lateinit var title: CharSequence
    private lateinit var message: CharSequence
    private lateinit var codeError: String
    private lateinit var warningDialogFragmentListener: WarningDialogFragmentListener

    private var drawableIcon: Drawable? = null
    private var textButtonPositive: String? = null
    private var showClose: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        binding = FragmentDialogWarningBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ -> return@OnKeyListener (keyCode == KeyEvent.KEYCODE_BACK) })

        showErrorInfo()

        return dialog
    }

    private fun showErrorInfo() {
        if (showClose) {
            binding.ivClose.visible()
            binding.ivClose.setOnClickListener {
                warningDialogFragmentListener.onWarningDialogFragmentClickClose()
                dismiss()
            }
        } else {
            binding.ivClose.gone()
        }

        if (textButtonPositive != null) {
            binding.btPositive.visible()
            binding.btPositive.text = textButtonPositive
            binding.btPositive.setOnClickListener {
                warningDialogFragmentListener.onWarningDialogFragmentClickButton()
                dismiss()
            }
        } else {
            binding.btPositive.gone()
        }

        showTitleAndMessage()

        showCodeError()

        drawableIcon?.let {
            binding.ivIcon.setImageDrawable(it)
        }
    }

    private fun showCodeError() {
        if (codeError.isBlank()) {
            binding.tvCodeError.gone()
        } else {
            binding.tvCodeError.visible()
            binding.tvCodeError.text = getString(R.string.generic_error_code_error, codeError)
        }
    }

    private fun showTitleAndMessage() {
        binding.tvTitle.text = title
        binding.tvMessage.text = message
    }

    fun setValue(
        warningDialogFragmentListener: WarningDialogFragmentListener,
        title: String,
        message: String,
        codeError: String,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        showClose: Boolean
    ) {
        this.warningDialogFragmentListener = warningDialogFragmentListener
        this.title = title
        this.message = message
        this.codeError = codeError
        this.drawableIcon = drawableIcon
        this.textButtonPositive = textButtonPositive
        this.showClose = showClose
    }

    fun setValue(
        warningDialogFragmentListener: WarningDialogFragmentListener,
        errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        showClose: Boolean
    ) {
        setValue(
            warningDialogFragmentListener,
            errorModel.error,
            errorModel.message,
            errorModel.errorCode,
            drawableIcon,
            textButtonPositive,
            showClose
        )
    }

    fun setErrorAndRefreshInfo(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
        this.title = errorModel.error
        this.message = errorModel.message
        this.codeError = errorModel.errorCode
        showTitleAndMessage()
    }
}