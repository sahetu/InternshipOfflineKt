package android.batch1

import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class MakeServiceCall {
    private var response = StringBuilder()
    private var url: URL? = null

    companion object {
        const val GET = 1
        const val POST = 2
    }

    fun makeServiceCall(URL: String, type: Int, postDataParams: HashMap<String, String>): String {
        return when (type) {
            GET -> {
                try {
                    response = StringBuilder()
                    url = URL(URL)
                    val httpConn = url!!.openConnection() as HttpURLConnection
                    if (httpConn.responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader(InputStreamReader(httpConn.inputStream), 8192).useLines { lines ->
                            lines.forEach {
                                response.append(it)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                response.toString()
            }
            POST -> {
                var response = ""
                try {
                    url = URL(URL)
                    val conn = url!!.openConnection() as HttpURLConnection
                    conn.readTimeout = 15000
                    conn.connectTimeout = 15000
                    conn.requestMethod = "POST"
                    conn.doInput = true
                    conn.doOutput = true
                    conn.getOutputStream().use { os ->
                        BufferedWriter(OutputStreamWriter(os, "UTF-8")).use { writer ->
                            writer.write(getPostDataString(postDataParams))
                            writer.flush()
                        }
                    }
                    val responseCode = conn.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader(InputStreamReader(conn.inputStream)).useLines { lines ->
                            lines.forEach {
                                response += it
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                response
            }
            else -> ""
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getPostDataString(params: HashMap<String, String>): String {
        val result = StringBuilder()
        var first = true
        for ((key, value) in params) {
            if (first)
                first = false
            else
                result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value, "UTF-8"))
        }
        return result.toString()
    }
}