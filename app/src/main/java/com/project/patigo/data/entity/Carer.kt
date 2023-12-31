package com.project.patigo.data.entity

import Comment

data class Carer(
    var carerId: String,
    var carerName: String,//
    var carerSurname: String,//
    var carerAge: Int?,
    var carerAvailableDay: List<String>,
    var carerIban: String,
    var carerServices: List<String>,//
    var carerProvince: String,//
    var carerReviewList: List<Comment>,
    var carerProfilePict:String,//
    var carerInfo:String,
    var carerPhoneNumber: String
)
