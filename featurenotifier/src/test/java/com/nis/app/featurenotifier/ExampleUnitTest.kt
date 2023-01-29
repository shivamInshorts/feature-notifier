package com.nis.app.featurenotifier

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nis.app.featurenotifier.model.NotifierData
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val TAG = "ExampleUnitTest"

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private val jsonToTest = "{\n" +
            "  \"notifier_data\": {\n" +
            "    \"default_view_type\": \"dot\",\n" +
            "    \"nodes\": {\n" +
            "      \"discover\": {\n" +
            "        \"count\": 3,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"blue\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home\"\n" +
            "      },\n" +
            "      \"settings\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"blue\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home/discover\"\n" +
            "      },\n" +
            "      \"change_relevancy\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"green\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home/discover/settings\",\n" +
            "        \"is_feature\": true\n" +
            "      },\n" +
            "      \"pools\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"green\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home/discover\",\n" +
            "        \"is_feature\": true\n" +
            "      },\n" +
            "      \"unread\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"green\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home/discover\",\n" +
            "        \"is_feature\": true\n" +
            "      },\n" +
            "      \"feed_screen\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"blue\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home\"\n" +
            "      },\n" +
            "      \"bookmarks\": {\n" +
            "        \"count\": 1,\n" +
            "        \"view_type\": {\n" +
            "          \"dot\": {\n" +
            "            \"color\": \"green\"\n" +
            "          }\n" +
            "        },\n" +
            "        \"path\": \"/home/bookmarks\",\n" +
            "        \"is_feature\": true\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}"
    @Test
    fun parseNotifierDataTest() {
        val response = Gson().fromJson(jsonToTest, JsonObject::class.java);
        val notifierData = Gson().fromJson<NotifierData>(response.get("notifier_data"), NotifierData::class.java);
        println(notifierData);
    }
}