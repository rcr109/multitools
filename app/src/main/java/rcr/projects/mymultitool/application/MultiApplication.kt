package rcr.projects.mymultitool.application

import android.app.Application
import rcr.projects.mymultitool.helpers.HelperDB

class MultiApplication : Application() {

    var helperDB : HelperDB? = null
    private set

    companion object{
        lateinit var instance: MultiApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)


    }



}