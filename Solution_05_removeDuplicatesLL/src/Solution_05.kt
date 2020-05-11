import java.security.MessageDigest
import java.util.*

fun main(args: Array<String>){

    fun removeDuplicatedEmails(emailThread: LinkedList<Email>): LinkedList<Email>{
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

    /*******************
       VALIDATION AREA
    ********************/

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

    println("Original Email List")
    for(email: Email in emailList){
        println(email.getEmailSubject() + " - " + email.getEmailHash())
    }

    var cleanEmailList: LinkedList<Email> = removeDuplicatedEmails(emailList)

    println("\n\nCleaned Email List")
    for(email: Email in cleanEmailList){
        println(email.getEmailSubject() + " - " + email.getEmailHash())
    }
}

class Email {
    private var subject: String = ""
    private var body: String = ""
    private var emailHash: String = ""

    constructor(subject: String, body: String) {
        var allEmailText: String = subject + body

        this.subject = subject
        this.body = body
        this.emailHash = hashString("SHA-1", allEmailText)
    }

    public fun getEmailHash(): String{
        return this.emailHash
    }

    public fun getEmailSubject(): String{
        return this.subject
    }

    public fun getEmailBody(): String{
        return this.body
    }

    //  Booth functions bellow can be found in: https://www.javacodemonk.com/md5-and-sha256-in-java-kotlin-and-android-96ed9628
    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        return printHexBinary(bytes).toUpperCase()
    }

    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    fun printHexBinary(data: ByteArray): String {
        val r = StringBuilder(data.size * 2)
        data.forEach { b ->
            val i = b.toInt()
            r.append(HEX_CHARS[i shr 4 and 0xF])
            r.append(HEX_CHARS[i and 0xF])
        }
        return r.toString()
    }
}
