package com.modarsin.portal

import androidx.annotation.NonNull
import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerResultsIntent
import android.view.WindowManager.LayoutParams
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant



class MainActivity: FlutterActivity() {

    private var url: String? = null;
    //private var headers: Map<String, String>? = null;
    private val CHANNEL = "com.modarsin.portal.intent_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.d("intent URI", intent.toUri(0));

        getWindow().addFlags(LayoutParams.FLAG_SECURE)
        getWindow().setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE)

        var url: String? = null
        //var headers: Map<String, String>? = null
        val action = intent.action
        if (RecognizerResultsIntent.ACTION_VOICE_SEARCH_RESULTS == action) {
            return
        }
        if (Intent.ACTION_VIEW == action) {
            val data: Uri? = intent.data
            if (data != null) url = data.toString()
        } else if (Intent.ACTION_SEARCH == action || MediaStore.INTENT_ACTION_MEDIA_SEARCH == action
                || Intent.ACTION_WEB_SEARCH == action) {
            url = intent.getStringExtra(SearchManager.QUERY)
        }
//        if (url != null && url.startsWith("http")) {
//            val pairs = intent
//                    .getBundleExtra(Browser.EXTRA_HEADERS)
//            if (pairs != null && !pairs.isEmpty) {
//                val iter: Iterator<String> = pairs.keySet().iterator()
//                headers = HashMap()
//                while (iter.hasNext()) {
//                    val key = iter.next()
//                    headers.put(key, pairs.getString(key)!!)
//                }
//            }
//        }

        this.url = url
        //this.headers = headers
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    val methodName = call.method
                    if (methodName == "getIntentData") {
//                        val data = ArrayList<Any?>();
//                        data.add(url)
//                        data.add(headers)
                        result.success(url)
                        this.url = null;
                        //this.headers = null;
                    }
                }
    }
}
