package com.egemert.scrollablepopup.infodialog

import android.app.Dialog

class DefaultDismissClickListener : DialogClickListener {
    override fun onClick(dialog: Dialog) {
        if(dialog != null){
            dialog.dismiss()
        }
    }
}