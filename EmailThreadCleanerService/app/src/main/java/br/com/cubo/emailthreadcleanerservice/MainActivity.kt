package br.com.cubo.emailthreadcleanerservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    private val appTAG: String = "appEmailThreadClean"
    private val requesterID: String = "SElSRSBST0RSSUdPIEJFWkVSUkE="
    private var cleanThreadBC: CleanThreadBroadcast = CleanThreadBroadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var filter: IntentFilter = IntentFilter("br.com.cubo.emailthreadcleanerservice.BROADCAST")
        registerReceiver(cleanThreadBC, filter)

        sendBroadcast()
    }

    private fun sendBroadcast(){
        var intent: Intent = Intent("br.com.cubo.emailthreadcleanerservice.BROADCAST")

        /*****/
        // SOME STUB EMAILS FOR TESTING THE BroadCast and the Service
        var emailList: LinkedList<Email> = LinkedList()

        val email01: Email = Email("Email_01", "Email_01_Body")
        val email02: Email = Email("Email_02", "Email_02_Body")
        val email03: Email = Email("Email_03", "Email_03_Body")
        val email04: Email = Email("Email_04", "Email_04_Body")

        emailList.add(email01)
        emailList.add(email02)
        emailList.add(email03)
        emailList.add(email02) //Duplicated
        emailList.add(email02) //Duplicated
        emailList.add(email03) //Duplicated
        emailList.add(email04)
        /*****/

        Log.i(appTAG, "==================================================================")
        for(email: Email in emailList){
            Log.i(appTAG, "Lista de Email ANTES DE SER LIMPO: [" + email.getEmailHash() + "] " + email.getEmailSubject() + ": " + email.getEmailBody())
        }
        Log.i(appTAG, "==================================================================")

        intent.putExtra ("EmailThreadSend", emailList)
        intent.putExtra("RequesterID", requesterID)

        sendBroadcast(intent)
    }

    //This Broadcast receiver is to receive the list back
    private var brReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            //Must check if the Filter is correct and if the feedback is for right Requester
            if(intent?.action.equals("br.com.cubo.emailthreadcleanerservice.BROADCAST_BACK")){
                if(requesterID.equals(intent?.getStringExtra("RequesterID"))){

                    val data = intent?.extras
                    var returnedEmailThread : java.util.ArrayList<Email> = data?.getParcelableArrayList<Email>("returnedEmailThread") as java.util.ArrayList<Email>

                    Log.i(appTAG, "==================================================================")
                    for(email: Email in returnedEmailThread){
                        Log.i(appTAG, "Lista de Email DEPOIS DE SER LIMPO: [" + email.getEmailHash() + "] " + email.getEmailSubject() + ": " + email.getEmailBody())
                    }
                    Log.i(appTAG, "==================================================================")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var filter: IntentFilter = IntentFilter("br.com.cubo.emailthreadcleanerservice.BROADCAST_BACK")
        registerReceiver(brReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(brReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(cleanThreadBC)
    }
}
