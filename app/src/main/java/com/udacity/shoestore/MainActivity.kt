package com.udacity.shoestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        this.findNavController(R.id.nav_host_fragment)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var viewModel: ShoeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        drawerLayout = binding.drawerLayout
        appBarConfig = AppBarConfiguration(setOf(R.id.loginFragment, R.id.welcomeFragment, R.id.shoeListFragment), drawerLayout, ::onSupportNavigateUp)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)

        NavigationUI.setupWithNavController(binding.navView, navController)


        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)

    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp()
}
