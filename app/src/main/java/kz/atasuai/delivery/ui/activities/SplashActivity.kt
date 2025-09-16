package kz.atasuai.delivery.ui.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kz.atasuai.delivery.common.Translator
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.activities.welcome.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        var isLoading = true
        splashScreen.setKeepOnScreenCondition { isLoading }

        lifecycleScope.launchWhenCreated {
            loadLanguagePack {
                isLoading = false
            }
        }

    }

    private fun loadLanguagePack(onComplete: () -> Unit) {
        val currentLanguage = AtasuaiApp.currentLanguage.value
        Translator.loadLanguagePack(currentLanguage) { success ->
            if (success) {
                (application as AtasuaiApp).registerServices()
            }
            runOnUiThread {
                proceedToNextScreen()
                onComplete()
            }
        }
    }

    private fun proceedToNextScreen() {
        val nextActivity = when {
            AtasuaiApp.isFirstLaunch -> LoginActivity::class.java
            AtasuaiApp.currentPerson == null -> LoginActivity::class.java
            else -> MainActivity::class.java
        }
        startActivity(Intent(this, nextActivity))
        overridePendingTransition(0, 0)
        finish()
    }
}