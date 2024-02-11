package com.catquest.helpers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Json {

    public static JSONObject parse(String file) throws IOException, ParseException {
        FileReader reader = new FileReader(file);
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }
}
