package com.arkapp.gyanvatika.utils.pojo

import com.arkapp.gyanvatika.data.firestore.responses.Event

class GeneratedEvents {
    var event: Event? = null
    var eventDate: String = ""
    var eventDateType: Int? = null

    constructor(event: Event?, eventDate: String, eventDateType: Int?) {
        this.event = event
        this.eventDate = eventDate
        this.eventDateType = eventDateType
    }
}