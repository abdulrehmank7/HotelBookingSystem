package com.arkapp.gyanvatika.utils.pojo

import android.os.Parcel
import android.os.Parcelable
import com.arkapp.gyanvatika.data.firestore.responses.Event

class GeneratedEvents() :Parcelable {
    var event: Event? = null
    var eventDate: String = ""
    var eventDateType: Int? = null

    constructor(parcel: Parcel) : this() {
        event = parcel.readParcelable(Event::class.java.classLoader)
        eventDate = parcel.readString()!!
        eventDateType = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    constructor(event: Event?, eventDate: String, eventDateType: Int?) : this() {
        this.event = event
        this.eventDate = eventDate
        this.eventDateType = eventDateType
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(event, flags)
        parcel.writeString(eventDate)
        parcel.writeValue(eventDateType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeneratedEvents> {
        override fun createFromParcel(parcel: Parcel): GeneratedEvents {
            return GeneratedEvents(parcel)
        }

        override fun newArray(size: Int): Array<GeneratedEvents?> {
            return arrayOfNulls(size)
        }
    }
}