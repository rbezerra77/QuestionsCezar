package br.com.cubo.emailthreadcleanerservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import kotlin.collections.ArrayList

class CleanThreadBroadcast: BroadcastReceiver() {

    private val appTAG: String = "appEmailThreadClean"

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action.equals("br.com.cubo.emailthreadcleanerservice.BROADCAST")){

            //Get data from Broadcast sender
            val data = intent?.extras
            val originalEmailThread = data?.getParcelableArrayList<Email>("EmailThreadSend") as ArrayList<Email>
            val requesterID = data.getString("RequesterID")

            // Declaring a handler to get the Array back from the Service
            val myHandler = Handler()
            var mRec = object : ResultReceiver(myHandler){
                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                    super.onReceiveResult(resultCode, resultData)

                    val data = intent?.extras
                    var returnedRequesterID = resultData?.getString("RequesterID")
                    var returnedEmailThread = resultData?.getParcelableArrayList<Email>("returnedEmailThread")

                    val intentBack = Intent("br.com.cubo.emailthreadcleanerservice.BROADCAST_BACK")
                    intentBack.putExtra("RequesterID", returnedRequesterID)
                    intentBack.putExtra("returnedEmailThread", returnedEmailThread)

                    context!!.sendBroadcast(intentBack)
                }
            }

            //Start the IntentService and passing the data for processing
            //decided to use the IntentService coz' there's a requirement to queue the requests
            //and IntentService does it automatically
            val cleanEmailThreadIntent = Intent(context, CleanThreadService::class.java)

            //Add the requesterID to identify each request to be filter in the client's feedback
            cleanEmailThreadIntent.putExtra("RequesterID", requesterID)

            //1st PutExtra is related to receive object
            cleanEmailThreadIntent.putExtra("receiver", mRec)

            //2nd PutExtra is related to ArrayList
            cleanEmailThreadIntent.putExtra("EmailThreadSend", originalEmailThread)

            context?.startService(cleanEmailThreadIntent)
        }
    }
}