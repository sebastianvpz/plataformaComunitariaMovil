package com.example.proyectocomunitario.model

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("token") val token: String,
)
