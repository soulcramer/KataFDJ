package app.soulcramer.cache

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    companion object {
        private const val PREF_FDJ_PACKAGE_NAME = "app.soulcramer.fdj.preferences"

        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val fdjPref: SharedPreferences

    init {
        fdjPref = context.getSharedPreferences(PREF_FDJ_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = fdjPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = fdjPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}