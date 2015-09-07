package utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import http.Constants;

/**
 * Created by tales on 4/09/15.
 */
public class HttpParser {

    public static  String interpretarInputStream(InputStream is) {
        String jsonString = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            jsonString = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
    public static JSONObject parseStringToJSON(String jsonObjString) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(jsonObjString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jObj;
    }

    public static String getJSONValue(JSONObject jsonObject, String key){

        String value = null;
        try {
            value = jsonObject.getString(Constants.MESSAGE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

}
