package uz.pdp.smssherlar.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PoemEntity {

    @PrimaryKey
    var id: Int? = null

    var name: String? = null

    var desc: String? = null

    var likedId: Int? = null

}