package com.project.patigo.data.entity

data class Carer(
    var carerId: String,
    var carerName: String,
    var carerSurname: String,
    var carerAge: Int?,
    var carerAvailableDay:List<String>,
    var carerIban:String,
    var carerServices:List<String>,
    var carerProvince:String,

)
