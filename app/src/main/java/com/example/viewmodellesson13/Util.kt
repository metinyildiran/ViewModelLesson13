package com.example.viewmodellesson13

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Util {
    fun View.showSnackbar(text: String, onAction:() -> Unit){
        Snackbar.make(
            this,
            text,
            Snackbar.LENGTH_SHORT
        ).setAction("Geri al") {
            onAction.invoke()
        }.show()
    }
}