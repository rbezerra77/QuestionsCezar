import java.security.MessageDigest
import java.util.*

fun main(args: Array<String>){

    fun findIntersectionNode(emailThread01: LinkedList<Email>, emailThread02: LinkedList<Email>): Email?{
        var nodeFound: Int = 0
        val noIntersectNode = 1000

        if(emailThread01.size >= emailThread02.size){
            for(index: Int in (emailThread02.size - 1) downTo 0){
                if(!emailThread02[index].getEmailHash().equals(emailThread01[index].getEmailHash())){
                    if(index == (emailThread02.size - 1)) {
                        nodeFound = noIntersectNode
                        break
                    }
                    nodeFound = index +1
                    break
                }
            }
        }else {
            for(index: Int in (emailThread01.size - 1) downTo 0){
                if(!emailThread01[index].getEmailHash().equals(emailThread02[index].getEmailHash())){
                    if(index == (emailThread01.size - 1)) {
                        nodeFound = noIntersectNode
                        break
                    }
                    nodeFound = index + 1
                    break
                }
            }
        }

        return if(nodeFound != noIntersectNode)
            emailThread01[nodeFound]
        else
            null
    }

    /*******************
    VALIDATION AREA
     ********************/

    var emaillist01: LinkedList<Email> = LinkedList()
    var emaillist02: LinkedList<Email> = LinkedList()

    val email01: Email = Email("Email_01", "Email_01_Body")
    val email02: Email = Email("Email_02", "Email_02_Body")
    val email03: Email = Email("Email_03", "Email_03_Body")
    val email04: Email = Email("Email_04", "Email_04_Body")
    val email05: Email = Email("Email_05", "Email_05_Body")
    val email06: Email = Email("Email_06", "Email_06_Body")
    val email07: Email = Email("Email_07", "Email_07_Body")


    emaillist01.add(email01)
    emaillist01.add(email02)
    emaillist01.add(email03)
    emaillist01.add(email04) //Intersection node
    emaillist01.add(email05)
    emaillist01.add(email06)
    emaillist01.add(email07)

    emaillist02.add(email01)
    emaillist02.add(email03)
    emaillist02.add(email02)
    emaillist02.add(email04) //Intersection node
    emaillist02.add(email05)
    emaillist02.add(email06)
    emaillist02.add(email07)

    println("Email List 01 ")
    for(email: Email in emaillist01){
        println(email.getEmailSubject() + " - " + email.getEmailHash())
    }

    println("Email List 02 ")
    for(email: Email in emaillist02){
        println(email.getEmailSubject() + " - " + email.getEmailHash())
    }

    //var cleanEmailList: LinkedList<Email> = removeDuplicatedEmails(emaillist01)

    var intersectEmail = findIntersectionNode(emaillist01, emaillist02)

    if(intersectEmail!=null)
        println("Intersection Email: ${intersectEmail.getEmailHash()}  - ${intersectEmail.getEmailSubject()}")
    else
        println("\n\n NO Intersection Node found!")

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
