package com.example.myapplication

import java.io.Serializable

class DVD(
    var name: String,
    var img: Int,
    var info: String,
    var price: Double
) : Serializable {
}
