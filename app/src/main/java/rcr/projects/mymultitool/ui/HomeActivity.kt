package rcr.projects.mymultitool.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import rcr.projects.mymultitool.R


class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)
        initDrawer()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this);
        val calendar_image = findViewById<ImageView>(R.id.iv_calendar)
        calendar_image.setOnClickListener {
            openCloseNavigationDrawer(it)
        }
    }

    private fun initDrawer(){
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, null,R.string.txt_open_drawer , R.string.txt_close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun openCloseNavigationDrawer(view: View) {
        val drawer_layout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

    }

    @Override
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.action_home) {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        } else if (id == R.id.action_tarefas) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        } else if (id == R.id.action_sair) {
            val i = Intent(this, SobreActivity::class.java)
            finishAffinity()
        }else if (id == R.id.action_sobre) {
            val i = Intent(this, SobreActivity::class.java)
            startActivity(i)
        }
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


}