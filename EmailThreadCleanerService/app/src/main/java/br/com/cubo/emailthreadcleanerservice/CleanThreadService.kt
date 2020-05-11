package br.com.cubo.emailthreadcleanerservice

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class CleanThreadService : IntentService("CleanThreadService"){

    private val appTAG: String = "appEmailThreadClean"

    override fun onHandleIntent(intent: Intent?) {

        val data = intent?.extras
        var originalEmailThread = data?.getParcelableArrayList<Email>("EmailThreadSend") as ArrayList<Email>
        var requesterID = data?.getString("RequesterID")
        var receiver = data?.getParcelable("receiver") as ResultReceiver

        //ArrayList ---> LinkedList
        var lLBackEmailThread: LinkedList<Email> = LinkedList<Email>()
        for(email: Email in originalEmailThread){
            lLBackEmailThread.add(email)
        }

        //Cleaning the Thread
        var cleanEmailThread: LinkedList<Email> = removeDuplicatedEmails(lLBackEmailThread)

        //LinkedList ---> ArrayList
        var aLBackEmailThread: ArrayList<Email> = ArrayList<Email>()
        for(email: Email in cleanEmailThread){
            aLBackEmailThread.add(email)
        }

        //Preparing bundle to send array back
        val retBundle : Bundle? = Bundle()
        retBundle?.putParcelableArrayList("returnedEmailThread", aLBackEmailThread)
        retBundle?.putString("RequesterID", requesterID)

        receiver?.send(0, retBundle)
    }

    private fun removeDuplicatedEmails(emailThread: LinkedList<Email>): LinkedList<Email> {
        var uniqueHashSet= mutableSetOf("")
        var cleanEmailThread: LinkedList<Email> = LinkedList<Email>()

        if(emailThread.size <=1 ) return  emailThread

        for (email: Email in emailThread){
            if(uniqueHashSet.add(email.getEmailHash())){
                cleanEmailThread.add(email)
            }
        }

        return cleanEmailThread
    }
}