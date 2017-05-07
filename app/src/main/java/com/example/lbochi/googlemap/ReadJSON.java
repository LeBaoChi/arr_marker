package com.example.lbochi.googlemap;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadJSON {
    // Đọc file points.json và chuyển thành đối tượng java.
    public static points readCompanyJSONFile(Context context) throws IOException,JSONException {

        // Đọc nội dung text của file company.json
        String jsonText = readText(context, R.raw.points);

        // Đối tượng JSONObject gốc mô tả toàn bộ tài liệu JSON.
        JSONObject jsonRoot = new JSONObject(jsonText);

        JSONArray jsonArray1 = jsonRoot.getJSONArray("lng");
        String[] lng = new String[jsonArray1.length()];

        for (int i = 0; i < jsonArray1.length(); i++) {
            lng[i] = jsonArray1.getString(i);
        }

        JSONArray jsonArray2 = jsonRoot.getJSONArray("lat");
        String[] lat = new String[jsonArray2.length()];
        for (int i = 0; i < jsonArray2.length(); i++) {
            lat[i] = jsonArray2.getString(i);
        }

        JSONArray jsonArray3 = jsonRoot.getJSONArray("name");
        String[] name = new String[jsonArray2.length()];
        for (int i = 0; i < jsonArray3.length(); i++) {
            name[i] = jsonArray3.getString(i);
        }
        points Points =new points();
        Points.setLng(lng);
        Points.setLng(lat);
        Points.setLng(name);
        Log.d("ADebugTag", "Destroy ap ");
        // Đọc nội dung text của một file nguồn.
        return Points;
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
