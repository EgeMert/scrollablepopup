package com.egemert.scrollablepopup.infodialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.egemert.scrollablepopup.R
import com.egemert.scrollablepopup.databinding.InfoDialogBinding

class InfoDialog private constructor(private val context: Context, builder: Builder) :
    View.OnClickListener {

    private val dialog: Dialog = Dialog(context, R.style.Theme_AppCompat_Dialog)
    private val mLayoutAdapter: InfoDialogBinding
    private var primaryClickListener: DialogClickListener? = null
    private var secondaryClickListener: DialogClickListener? = null

    private fun applyBuilder(builder: Builder) {
        if (hasText(builder.primaryActionText)) {
            mLayoutAdapter.actionPrimary.text = builder.primaryActionText
            primaryClickListener = builder.primaryActionListener
        } else {
            mLayoutAdapter.actionPrimary.visibility = View.GONE
        }
        if (hasText(builder.secondaryActionText)) {
            mLayoutAdapter.actionHelper.text = builder.secondaryActionText
            secondaryClickListener = builder.secondaryActionListener
        } else {
            mLayoutAdapter.actionHelper.visibility = View.GONE
        }
        if (hasText(builder.title)) {
            mLayoutAdapter.dialogTitle.text = builder.title
        } else {
            mLayoutAdapter.dialogTitle.visibility = View.GONE
        }
        if (hasText(builder.infoText)) {
            mLayoutAdapter.dialogContent.text = builder.infoText
        }
    }

    private fun initClickListeners() {
        if (mLayoutAdapter.actionPrimary.visibility === View.VISIBLE) {
            if (primaryClickListener == null) {
                primaryClickListener = DefaultDismissClickListener()
            }
            mLayoutAdapter.actionPrimary.setOnClickListener(this)
        }
        if (mLayoutAdapter.actionHelper.visibility === View.VISIBLE) {
            if (secondaryClickListener == null) {
                secondaryClickListener = DefaultDismissClickListener()
            }
            mLayoutAdapter.actionHelper.setOnClickListener(this)
        }
    }

    private fun hasText(text: String?): Boolean {
        return text != null && text.isNotEmpty()
    }

    private fun hasText(text: CharSequence?): Boolean {
        return text != null && text.isNotEmpty()
    }

    private fun inflateView(context: Context): View {
        return LayoutInflater.from(context).inflate(R.layout.info_dialog, null, false)
    }

    fun show() {
        if (!(context as Activity).isFinishing) {
            dialog.show()
        }
    }

    private fun dismiss() {
        dialog.dismiss()
    }

    override fun onClick(v: View) {
        when {
            v === mLayoutAdapter.actionPrimary -> {
                primaryClickListener?.onClick(dialog)
            }
            v === mLayoutAdapter.actionHelper -> {
                secondaryClickListener?.onClick(dialog)
            }
            else -> {
                dismiss()
            }
        }
    }

    class Builder {
        internal var title: CharSequence? = null
        internal var infoText: CharSequence? = null
        internal var primaryActionText: CharSequence? = null
        internal var secondaryActionText: CharSequence? = null
        internal var primaryActionListener: DialogClickListener? = null
        internal var secondaryActionListener: DialogClickListener? = null
        fun title(title: CharSequence?): Builder {
            this.title = title
            return this
        }

        fun infoText(info: CharSequence?): Builder {
            infoText = info
            return this
        }

        fun primaryActionText(info: CharSequence?): Builder {
            primaryActionText = info
            return this
        }

        fun secondaryActionText(info: CharSequence?): Builder {
            secondaryActionText = info
            return this
        }

        fun primaryAction(actionListener: DialogClickListener?): Builder {
            primaryActionListener = actionListener
            return this
        }

        fun secondaryAction(actionListener: DialogClickListener?): Builder {
            secondaryActionListener = actionListener
            return this
        }

        fun build(context: Context): InfoDialog {
            return InfoDialog(context, this)
        }
    }

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        val view = inflateView(context)
        mLayoutAdapter = InfoDialogBinding.bind(view)
        dialog.setContentView(mLayoutAdapter.root)
        applyBuilder(builder)
        initClickListeners()
    }
}