package ie.wit.ca1.utils

import android.content.Context
import java.io.IOException

// https://www.bezkoder.com/kotlin-android-read-json-file-assets-gson/
// this is where i found how to read data from json file.
fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return jsonString
}