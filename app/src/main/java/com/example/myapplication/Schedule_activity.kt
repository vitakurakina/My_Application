package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.webkit.WebView
import android.webkit.WebViewClient

class Schedule_activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_schedule)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val osulogoButton = findViewById<ImageButton>(R.id.nav_osu_logo)
        val notesNavButton = findViewById<ImageButton>(R.id.nav_notes)

        notesNavButton.setOnClickListener {
            val intent = Intent(this, Notes_Activity::class.java)
            startActivity(intent)
        }

        osulogoButton.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }
        val webView = findViewById<WebView>(R.id.webview_schedule)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                webView.evaluateJavascript(
                    """
                    (function() {
                        document.querySelector('header')?.remove();
                        document.querySelector('footer')?.remove();
                        document.querySelector('sidebar')?.remove();
                        document.querySelector('h1.heading-title')?.remove();
                        document.querySelector('nav.main-nav')?.remove();
                        document.querySelector('#nav-left-btn-show')?.remove();
                        document.querySelector('#nav-left-btn-hide')?.remove();
                        document.querySelector('#nav-left')?.remove();
                        document.querySelector('#nav-left-content')?.remove();
                        document.querySelector('ol.breadcrumb')?.remove();
                        document.querySelector('span.link_inner')?.remove();
                        document.querySelector('panel-footer.div')?.remove();
                        document.querySelector('#loadICS')?.remove();
                        document.querySelector('#loadICSAll')?.remove();

                    })();
                    """.trimIndent(), null
                )
            }
        }

        webView.loadUrl("https://oreluniver.ru/schedule")


    }
}