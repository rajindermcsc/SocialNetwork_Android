package com.websoftq.socialnetwork.core

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class SocialNetwork :Application() {
    override fun onCreate() {
        super.onCreate()

        socialNetwork = this
        Fresco.initialize(this)

    }


    companion object {
        private var socialNetwork: SocialNetwork? = null
        fun get ()= SocialNetwork()
    }
}