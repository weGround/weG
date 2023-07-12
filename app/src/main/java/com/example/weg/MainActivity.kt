package com.example.weg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weg.databinding.ActivityMainBinding
import com.example.weg.ui.home.HomeFragment
import com.example.weg.ui.home.HomeMainFragment
import com.example.weg.ui.login.LoginActivity
import com.example.weg.ui.sideSheet.GroupListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;
    private var currentUserId : String? = null;
    private var currentGroup : String? = null;
    private val requestCode = 123;

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            currentUserId = result.data?.getStringExtra("userId")
            // 수신된 데이터 처리
            val welcome = getString(R.string.welcome)
            Toast.makeText(
                applicationContext,
                "$welcome $currentUserId",
                Toast.LENGTH_LONG
            ).show()
            val groupListFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as GroupListFragment;
            groupListFragment.initGroupList(0, true);

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val navView: BottomNavigationView = binding.navView

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_first, R.id.navigation_second, R.id.navigation_third
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // setupActionBarwithNavController에서 up button이 연동되어 설정되기에 때문에, 이 메소드 이전에 up button을 활성화 하면 씹힘. 꼭 이후에 부르기
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_android)
        }

        val intent = Intent(this, LoginActivity::class.java)
        resultLauncher.launch(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_main_menu, menu);
        return true;
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.add_action);
        item?.isVisible = false;
        return super.onPrepareOptionsMenu(menu);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // up button 클릭 이벤트 처리
                onGroupIconClicked();
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onGroupIconClicked(){
        Toast.makeText(this, "Group Icon is clicked!", Toast.LENGTH_SHORT).show();
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val gravity = GravityCompat.START;
        drawerLayout.openDrawer(gravity);
    }

    fun getUserId() : String?{
        return this.currentUserId;
    }
    fun getCurrentGroup() : String?{
        return this.currentGroup;
    }

    fun setCurrentGroup(currentGroup:String){
        this.currentGroup = currentGroup;
    }
    fun addGroup(newGroupName : String){
        Log.d("Add New Group", "addGroup: " + newGroupName);
        val groupListFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as GroupListFragment;
        groupListFragment.addGroup(newGroupName);
    }

    fun makeGroup(newGroupName : String){
        Log.d("Add New Group", "addGroup: " + newGroupName);
        val groupListFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as GroupListFragment;
        groupListFragment.makeGroup(newGroupName);
    }

    fun onGroupChanged(newGroupName: String?){
        currentGroup = newGroupName;
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val homeFragment = navHostFragment.childFragmentManager.fragments[0] as HomeFragment
        val homeMainFragment = homeFragment.childFragmentManager.findFragmentById(R.id.fragmentContainer) as HomeMainFragment;
        homeMainFragment.onGroupChanged(currentGroup);
    }
}