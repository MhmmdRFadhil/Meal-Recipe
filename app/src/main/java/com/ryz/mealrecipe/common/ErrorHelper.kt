package com.ryz.mealrecipe.common

import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

object ErrorHelper {
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is HttpException -> {
                val jsonObj =
                    JSONObject(exception.response()?.errorBody()!!.charStream().readText())
                val errorMessage = jsonObj.getString("message")
                errorMessage
            }

            is UnknownHostException -> "Tidak ada koneksi internet. Silakan periksa koneksi Anda."
            is JsonSyntaxException -> "Terjadi kesalahan dalam membaca data dari server."
            is SocketTimeoutException -> "Waktu tunggu koneksi ke server terlampaui. Silakan coba lagi nanti."
            is SSLHandshakeException -> "Terjadi kesalahan dalam melakukan SSL handshake dengan server."
            is ConnectException -> "Tidak dapat terhubung ke server. Pastikan koneksi internet Anda berfungsi."
            is NoRouteToHostException -> "Tidak ada rute jaringan yang tersedia untuk mencapai server."
            is SocketException -> "Terjadi masalah pada koneksi socket."
            is IllegalArgumentException -> "Argumen yang diberikan tidak valid."
            else -> "Terjadi kesalahan tidak terduga. Silakan coba lagi nanti."
        }
    }
}