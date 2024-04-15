package internship.offlinekt

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabDemoActivity : AppCompatActivity() {

    lateinit var tablayout : TabLayout
    lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_demo)

        tablayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.tab_pager)

        tablayout.post {
            tablayout.setupWithViewPager(viewPager)
        }

        viewPager.adapter = TabDemoAdapter(supportFragmentManager)

    }

    class TabDemoAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {

        override fun getPageTitle(position: Int): CharSequence? {
            when (position){
                0 -> return "Chat"
                1 -> return "Status"
                2 -> return "Call"
            }
            //return super.getPageTitle(position)
            return "Demo"
        }

        override fun getItem(position: Int): Fragment {
            //TODO("Not yet implemented")
            when (position){
                0 -> return ChatFragment()
                1 -> return StatusFragment()
                2 -> return CallFragment()
            }
            return DemoFragment()
        }

        override fun getCount(): Int {
            //TODO("Not yet implemented")
            return 10
        }

    }
}