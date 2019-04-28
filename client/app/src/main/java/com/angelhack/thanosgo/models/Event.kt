package com.angelhack.thanosgo.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val location: String = "",
    val type: String = "",
    val is_finished: Boolean = false,
    val difficulty_level: Int = 1,
    val organizer_photo: String? = "",
    val finisher_photo: String = "",
    val finished_by: String = "",
    val __typename: String = "Event"
) : Parcelable