package dev.shaundsmith.minecraft.rest;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * Test helper for building JSON structures.
 */
public class JsonSupport {

    public static JsonObjectBuilder jsonObject() {
        return Json.createObjectBuilder();
    }

    public static JsonArrayBuilder jsonArray() {
        return Json.createArrayBuilder();
    }

}
