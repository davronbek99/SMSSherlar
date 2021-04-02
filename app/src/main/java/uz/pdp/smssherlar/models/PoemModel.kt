package uz.pdp.smssherlar.models

import java.io.Serializable

class PoemModel : Serializable {
    var id: Int? = null
    var name: String? = null
    var desc: String? = null

    constructor()

    constructor(id: Int?, name: String?, desc: String?) {
        this.id = id
        this.name = name
        this.desc = desc
    }

}