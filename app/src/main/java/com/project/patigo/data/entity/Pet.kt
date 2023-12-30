package com.project.patigo.data.entity

data class Pet(
    var petId: String,
    var userId:String,
    var petName: String,
    var petGender: Boolean,
    var petWeight: Double,
    var petAge:Int,
    var petType: String,
    var petPicture: String,
    var petInfo:String?
) {
    companion object {
        fun fromMap(map: Map<String, Any>): Pet {
            return Pet(
                petId = map["petId"] as String,
                petName = map["petName"] as String,
                petGender = map["petGender"] as Boolean,
                petWeight = (map["petWeight"] as Number).toDouble(),
                petType = map["petType"] as String,
                petInfo = map["petInfo"] as String,
                petAge = map["petAge"] as Int,
                petPicture = map["petPicture"] as String,
                userId = map["userId"] as String,
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "petId" to petId,
            "petName" to petName,
            "petGender" to petGender,
            "petWeight" to petWeight,
            "petType" to petType,
            "petAge" to petAge,
            "petPicture" to petPicture,
            "userId" to userId,
            "petInfo" to petInfo
        )
    }
}
