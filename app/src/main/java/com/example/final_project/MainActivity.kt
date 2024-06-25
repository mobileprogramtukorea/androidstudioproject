package com.example.final_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.final_project.checklist.CheckListFragment
import com.example.final_project.home.HomeFragment
import com.example.final_project.mypage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //test
    companion object {
        const val REQUEST_CODE_WALK = 1
    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_WALK && resultCode == RESULT_OK) {
            val resultData = data?.getStringExtra("resultData")
            if (resultData != null) {
                val userFragment = supportFragmentManager.findFragmentByTag("MyPageFragment") as? MyPageFragment
                userFragment?.updateData(resultData)
            }
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