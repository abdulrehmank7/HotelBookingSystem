package com.arkapp.gyanvatika.data.firestore

import com.arkapp.gyanvatika.data.firestore.responses.Event
import com.arkapp.gyanvatika.utils.printLog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


fun getDBRef(): FirebaseFirestore = FirebaseFirestore.getInstance()

fun addNewEventDoc(event: Event): Task<Void> {

    val eventDoc = getDBRef().collection(EVENTS_COLLECTION).document()
    event.id = eventDoc.id

    return eventDoc.set(event)
}

suspend fun getMonthEvents(startTimestamp: Long, endTimestamp: Long): List<Event> {

    printLog("start time:$startTimestamp")
    printLog("end   time:$endTimestamp")
    return getDBRef()
        .collection(EVENTS_COLLECTION)
        .whereGreaterThanOrEqualTo(START_DATE_TIMESTAMP, startTimestamp.toString())
        .whereLessThanOrEqualTo(START_DATE_TIMESTAMP, endTimestamp.toString())
        .get()
        .await()
        .toObjects(Event::class.java)

}

fun deleteEventDoc(event: Event): Task<Void> {
    return getDBRef()
        .collection(EVENTS_COLLECTION)
        .document(event.id)
        .delete()
}

fun updateEventDoc(event: Event): Task<Void> {

    return getDBRef()
        .collection(EVENTS_COLLECTION)
        .document(event.id)
        .set(event)
}
