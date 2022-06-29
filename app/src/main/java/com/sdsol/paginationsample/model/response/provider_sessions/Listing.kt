package com.sdsol.paginationsample.model.response.provider_sessions


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Listing(
    @SerializedName("AuthorizeAmount")
    var authorizeAmount: Double,
    @SerializedName("DataHeader")
    var dataHeader: String,
    @SerializedName("IsMessageRead")
    var isMessageRead: Boolean,
    @SerializedName("IsViewedByClient")
    var isViewedByClient: Boolean,
    @SerializedName("IsViewedByTutor")
    var isViewedByTutor: Boolean,
    @SerializedName("LessonDurationString")
    var lessonDurationString: String,
    @SerializedName("Location")
    var location: String,
    @SerializedName("LocationAddress")
    var locationAddress: String,
    @SerializedName("LocationLat")
    var locationLat: Double,
    @SerializedName("LocationLong")
    var locationLong: Double,
    @SerializedName("NeedLessonNow")
    var needLessonNow: Boolean,
    @SerializedName("NeedLessonNowExpired")
    var needLessonNowExpired: Boolean,
    @SerializedName("RecordStatus")
    var recordStatus: String,
    @SerializedName("RowIndex")
    var rowIndex: Int,
    @SerializedName("SessionDate")
    var sessionDate: String,
    @SerializedName("SessionDuration")
    var sessionDuration: String,
    @SerializedName("SessionDurationHr")
    var sessionDurationHr: Int,
    @SerializedName("SessionID")
    var sessionID: Int,
    @SerializedName("SessionPrimaryID")
    var sessionPrimaryID: Int,
    @SerializedName("SessionStatusDisplay")
    var sessionStatusDisplay: String,
    @SerializedName("IsPastSession")
    var isPastSession: Boolean? = null,
    @SerializedName("SessionTime")
    var sessionTime: String,
    @SerializedName("SortOrder")
    var sortOrder: Int,
    @SerializedName("ServiceName")
    var sportName: String,
    @SerializedName("ClientFor")
    var ClientFor: String,
    @SerializedName("ClientID")
    var ClientID: Int,
    @SerializedName("ClientName")
    var ClientName: String,
    @SerializedName("ClientRating")
    var ClientRating: Int,
    @SerializedName("ThumbnailURL")
    var thumbnailURL: String,
    @SerializedName("TimeAgo")
    var timeAgo: String,
    @SerializedName("TutorID")
    var tutorID: Int,
    @SerializedName("TutorPackageID")
    var tutorPackageID: Int,
    var sportId: Int? = 0
) : Parcelable