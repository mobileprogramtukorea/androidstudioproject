package com.example.final_project

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.final_project.checklist.CheckListFragment
import com.example.final_project.home.HomeFragment
import com.example.final_project.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val homeFragment = HomeFragment()
        val checkListFragment = CheckListFragment()
        val myPageFragment = MyPageFragment()

        replaceFragment(homeFragment)
        bottomNavigationView.selectedItemId = R.id.home
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home ->replaceFragment(homeFragment)
                R.id.checkList ->replaceFragment(checkListFragment)
                R.id.myPage ->replaceFragment(myPageFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}