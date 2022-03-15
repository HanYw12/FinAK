package com.example.finak

import android.app.Activity
import android.view.View


class Ocultar :Activity(){
    private lateinit var checkFijo: View


    public fun OculEditText() {
        checkFijo.apply {
            visibility=View.VISIBLE
        }

    }
}