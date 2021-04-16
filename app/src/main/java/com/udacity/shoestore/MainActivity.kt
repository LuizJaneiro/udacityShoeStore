package com.udacity.shoestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        this.findNavController(R.id.navHostFragment)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var viewModel: ShoeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Timber.plant(Timber.DebugTree())
        appBarConfig = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.loginFragment, R.id.welcomeFragment, R.id.shoeListFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)

        viewModel = ViewModelProvider(this).get(ShoeListViewModel::class.java)

    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp()
}
