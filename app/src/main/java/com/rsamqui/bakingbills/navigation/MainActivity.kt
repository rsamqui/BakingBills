package com.rsamqui.bakingbills.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rsamqui.bakingbills.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rsamqui.bakingbills.R

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list: Set<Int> = setOf(R.id.loginFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        /*Redirecting*/
        val bottomViewNav: BottomNavigationView = bottomNavigation
        val navController = findNavController(R.id.nav_host)
        bottomViewNav.setupWithNavController(navController)
        /*Redirecting*/

        navController.addOnDestinationChangedListener {_, destination, _ ->
            if(list.contains(destination.id)){
                bottomNavigation.visibility = View.GONE
            }else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

}