package com.example.final_project.mypage

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.final_project.R

class MyPageFragment: Fragment(R.layout.fragment_mypage) {

    private val fabOpen: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_open) }
    private val fabClose: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.fab_close) }
    private val clockWise: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_clockwise) }
    private val anticlockWise: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.rotate_anticlockwise) }

    private var clicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    private fun setAnimation(clicked: Boolean) {
        if(!clicked) {
            fabaddprofile.startAnimation(clockWise)
        }else{
            fabaddprofile.startAnimation(anticlockWise)
        }
    }
    */
}