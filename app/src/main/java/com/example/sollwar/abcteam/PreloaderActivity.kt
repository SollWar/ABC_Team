package com.example.sollwar.abcteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val ONESIGNAL_APP_ID = "0daf0ae7-743d-4088-8f01-7fb1841de495"
const val AF_DEV_KEY = "yEp4Qfaqzr7ighZ8mkCC5D"

class PreloaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preloader)
        var conversionSuccess = false

        val intent = Intent(this, MainActivity::class.java)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(applicationContext)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        val conversionDataListener  = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                Log.e("AppFlyer", data.toString())
                if (data != null) {
                    intent.putExtra("af_status", data["af_status"] as String)
                    intent.putExtra("install_time", data["install_time"] as String)
                    intent.putExtra("is_first_launch", data["is_first_launch"] as Boolean)
                    conversionSuccess = true
                }
            }
            override fun onConversionDataFail(error: String?) {
                Log.e("AppFlyer", "error onAttributionFailure :  $error")
            }
            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    Log.d("AppFlyer", "onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }
            override fun onAttributionFailure(error: String?) {
                Log.e("AppFlyer", "error onAttributionFailure :  $error")
            }
        }

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            if (conversionSuccess) {
                startActivity(intent)
                finish()
            } else {
                while (!conversionSuccess) {
                    delay(100L)
                }
                startActivity(intent)
                finish()
            }

        }
    }
}
