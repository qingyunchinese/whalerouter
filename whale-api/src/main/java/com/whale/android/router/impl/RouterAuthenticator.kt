package com.whale.android.router.impl

import com.whale.android.router.meta.RouterRequest
import com.whale.android.router.meta.RouterResponse
import java.io.IOException

interface RouterAuthenticator {

    @Throws(IOException::class)
    fun authenticate(response: RouterResponse): RouterRequest?

    companion object {
        /** An authenticator that knows no credentials and makes no attempt to authenticate. */
        @JvmField
        val NONE = object : RouterAuthenticator {
            override fun authenticate(response: RouterResponse): RouterRequest? = null
        }
    }
}