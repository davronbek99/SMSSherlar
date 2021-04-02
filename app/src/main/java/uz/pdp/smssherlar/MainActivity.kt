package uz.pdp.smssherlar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.pdp.smssherlar.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.container, HomeFragment(), HomeFragment.toString()).commit()
    }
}