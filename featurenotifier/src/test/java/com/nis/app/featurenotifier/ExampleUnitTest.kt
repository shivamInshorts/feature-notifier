package com.nis.app.featurenotifier

import android.util.Log
import com.google.gson.Gson
import com.nis.app.featurenotifier.model.NotifierData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private val jsonToTest = "{\n" +
            "        \"default_view_type\": \"dot\",\n" +
            "        \"nodes\": {\n" +
            "            \"discover\": {\n" +
            "                \"count\": 2,\n" +
            "                \"view_type_data\": {\n" +
            "                    \"dot\": {\n" +
            "                        \"color\": \"red\"\n" +
            "                    },\n" +
            "                    \"dialog\": {}\n" +
            "                }\n" +
            "            },\n" +
            "            \"settings\": {\n" +
            "                \"count\": 1,\n" +
            "                \"view_type_data\": {\n" +
            "                    \"dot\": {\n" +
            "                        \"color\": \"red\"\n" +
            "                    },\n" +
            "                    \"dialog\": {}\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "}"

    private val TAG = "ExampleUnitTest"
    @Test
    fun parseNotifierDataTest() {
        val response = Gson().fromJson<NotifierData>(jsonToTest, NotifierData::class.java)
        println(response);
    }
}