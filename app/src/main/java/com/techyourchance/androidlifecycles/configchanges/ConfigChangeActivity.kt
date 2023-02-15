package com.techyourchance.androidlifecycles.configchanges

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.techyourchance.androidlifecycles.BackgroundDetector
import com.techyourchance.androidlifecycles.CustomApplication
import com.techyourchance.androidlifecycles.MyFragmentLifecycleCallbacks
import com.techyourchance.androidlifecycles.R
import timber.log.Timber

class ConfigChangeActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector

    private lateinit var txtUserInput: TextView

    private var isFragmentAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate()")

        val application = this.applicationContext as Application

        super.onCreate(savedInstanceState)

        backgroundDetector = (application as CustomApplication).backgroundDetector

        setContentView(R.layout.activity_config_change)

        txtUserInput = findViewById(R.id.txtUserInput)

        updateUserInput("")

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragmentContainer,
                    ConfigChangeFragment.newInstance(object : ConfigChangeFragment.Listener {
                        override fun onUserInputChanged(userInput: String) {
                            updateUserInput(userInput)
                        }
                    }),
                    "fragmentTag"
                ).commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifecycleCallbacks(), false)
    }

    private fun updateUserInput(userInput: String) {
        txtUserInput.text = "User input: $userInput"
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ConfigChangeActivity::class.java)
            context.startActivity(intent)
        }
    }

}