package com.example.lbochi.googlemap;

/**
 * Created by acer on 5/7/2017.
 */

import android.util.JsonWriter;
import java.io.IOException;
import java.io.Writer;

public class JSONWrite {

    // Ghi một đối tượng Java thành dữ liệu JSON vào Writer.
    public static void writeJsonStream(Writer output, points Points ) throws IOException {
        JsonWriter jsonWriter = new JsonWriter(output);

        jsonWriter.beginObject();// begin root

        String[] lng= Points.getLng();
        String[] lat= Points.getLat();
        String[] name= Points.getName();

        // "lng": [ ....]
        jsonWriter.name("lng").beginArray(); // begin lng
        for(String l1: lng) {
            jsonWriter.value(l1);
        }
        jsonWriter.endArray();// end lng

        // "lng": [ ....]
        jsonWriter.name("lat").beginArray(); // begin lat
        for(String l2: lat) {
            jsonWriter.value(l2);
        }
        jsonWriter.endArray();// end lng

        // "lng": [ ....]
        jsonWriter.name("name").beginArray(); // begin lng
        for(String l3: name) {
            jsonWriter.value(l3);
        }
        jsonWriter.endArray();// end name


    }


    public static points createPoints() {

        points Points=new points();

        String[] lng = { "105.842613", "105.942613" };
        Points.setLng(lng);
        String[] lat = { "21.007050", "21.107050" };
        Points.setLat(lat);
        String[] name = { "BK1", "BK2" };
        Points.setName(name);

        return Points;
    }

}