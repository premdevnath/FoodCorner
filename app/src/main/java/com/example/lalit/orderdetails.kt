package com.example.lalit

import android.os.Parcel
import android.os.Parcelable
import com.example.lalit.databinding.ActivityPaymentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import java.io.Serializable

//R13 yaha ba claas ko parcelabe banaya hai
class orderdetails():Serializable {

  var aname:String?=null
  var aadress:String?=null
   var phone:String?=null
    var amount:String?=null
    var  fname :ArrayList<String>?=null
    var fprice  :ArrayList<String>?=null
    var fimage   :ArrayList<String>?=null
    var fquantity :ArrayList<Int>?=null
     var userid:String?=null
    var currenttime:Long?=null
    var itempushkey:String?=null
    var orderacept:Boolean?=false
    var paymentrecive:Boolean?=false
     var total:String?=null
    var fdic    :ArrayList<String>?=null
    var fing     :ArrayList<String>?=null


    constructor(parcel: Parcel) : this() {
        aname = parcel.readString()
        aadress = parcel.readString()
        phone = parcel.readString()
        amount = parcel.readString()
        userid = parcel.readString()
        total = parcel.readString()
        orderacept = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentrecive = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itempushkey = parcel.readString()
        currenttime = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    constructor(
        aname: String,
        aadress: String,
        amount:String,
        phone: String,
        fname: ArrayList<String>,
        fprice: ArrayList<String>,
        fimage: ArrayList<String>,
        fquantity: ArrayList<Int>,
        userid: String,
        time: Long,
        itempushkey: String?,
        b: Boolean,
        b1: Boolean
    ) : this(){
        this.aname=aname
        this.aadress=aadress
        this.amount=amount
        this.phone=phone
        this.fname=fname
        this.fprice=fprice
        this.fimage=fimage
        this.fquantity=fquantity
        this.userid=userid
        this.currenttime=time
        this.itempushkey=itempushkey
        this.orderacept=orderacept
        this.paymentrecive=paymentrecive

    }


    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(aname)
        parcel.writeString(aadress)
        parcel.writeString(phone)
        parcel.writeString(amount)
        parcel.writeString(userid)
        parcel.writeString(total)
        parcel.writeValue(orderacept)
        parcel.writeValue(paymentrecive)
        parcel.writeString(itempushkey)
        parcel.writeValue(currenttime)
    }

   fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<orderdetails> {
        override fun createFromParcel(parcel: Parcel): orderdetails {
            return orderdetails(parcel)
        }

        override fun newArray(size: Int): Array<orderdetails?> {
            return arrayOfNulls(size)
        }
    }

}
