package br.com.cubo.emailthreadcleanerservice

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.security.MessageDigest

class Email() : Serializable, Parcelable {
    private var subject: String = ""
    private var body: String = ""
    private var emailHash: String = ""

    constructor(subject: String, body: String) : this() {
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

    fun printHexBinary(data: ByteArray): String {
        val r = StringBuilder(data.size * 2)
        data.forEach { b ->
            val i = b.toInt()
            r.append(HEX_CHARS[i shr 4 and 0xF])
            r.append(HEX_CHARS[i and 0xF])
        }
        return r.toString()
    }

    //This implementation bellow comes with adding Parcel feature to this class
    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    constructor(parcel: Parcel) : this() {
        subject = parcel.readString()
        body = parcel.readString()
        emailHash = parcel.readString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subject)
        parcel.writeString(body)
        parcel.writeString(emailHash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Email> {
        override fun createFromParcel(parcel: Parcel): Email {
            return Email(parcel)
        }

        override fun newArray(size: Int): Array<Email?> {
            return arrayOfNulls(size)
        }
    }
}