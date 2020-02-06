package com.arkapp.gyanvatika.data.firestore.responses

import android.os.Parcel
import android.os.Parcelable

class Event : Parcelable {
    var id = ""
    var startDate = ""
    var endDate = ""
    var startDateTimestamp = ""
    var endDateTimestamp = ""
    var customerName = ""
    var customerPhone = ""
    var bookingAmount = ""
    var otherInfo = ""
    var eventAddedDate = ""
    var eventAddedDateTimestamp = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()!!
        startDate = parcel.readString()!!
        endDate = parcel.readString()!!
        startDateTimestamp = parcel.readString()!!
        endDateTimestamp = parcel.readString()!!
        customerName = parcel.readString()!!
        customerPhone = parcel.readString()!!
        bookingAmount = parcel.readString()!!
        otherInfo = parcel.readString()!!
        eventAddedDate = parcel.readString()!!
        eventAddedDateTimestamp = parcel.readString()!!
    }

    constructor() : this("", "", "", "", "", "", "", "", "", "", "")

    constructor(id: String, startDate: String, endDate: String, startDateTimestamp: String, endDateTimestamp: String, customerName: String, customerPhone: String, bookingAmount: String, otherInfo: String, eventAddedDate: String, eventAddedDateTimestamp: String) {
        this.id = id
        this.startDate = startDate
        this.endDate = endDate
        this.startDateTimestamp = startDateTimestamp
        this.endDateTimestamp = endDateTimestamp
        this.customerName = customerName
        this.customerPhone = customerPhone
        this.bookingAmount = bookingAmount
        this.otherInfo = otherInfo
        this.eventAddedDate = eventAddedDate
        this.eventAddedDateTimestamp = eventAddedDateTimestamp
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeString(startDateTimestamp)
        parcel.writeString(endDateTimestamp)
        parcel.writeString(customerName)
        parcel.writeString(customerPhone)
        parcel.writeString(bookingAmount)
        parcel.writeString(otherInfo)
        parcel.writeString(eventAddedDate)
        parcel.writeString(eventAddedDateTimestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }


}