package com.arkapp.gyanvatika.data.firestore.responses

class Event {
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


}