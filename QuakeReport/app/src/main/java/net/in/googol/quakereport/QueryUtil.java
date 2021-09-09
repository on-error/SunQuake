package net.in.googol.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;



    public final class QueryUtil {

        private static final String API_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

        /**
         * Create a private constructor because no one should ever create a {@link net.in.googol.quakereport.QueryUtil} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */
        private QueryUtil() {
        }

        /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */



        public static ArrayList<Earthquake> extractEarthquakes() {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            URL url = createUrl(API_URL);
            String jsonResponse = null;
            try {
                jsonResponse = makeHTTPRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }



            try {

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.

                JSONObject jsonObject = new JSONObject(jsonResponse);

                JSONArray jsonArray = jsonObject.getJSONArray("features");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject properties = jsonObject1.getJSONObject("properties");
                    double mag = properties.getDouble("mag");
                    String place = properties.getString("place");
                    long time = properties.getLong("time");
                    String weburl = properties.getString("url");
                    earthquakes.add(new Earthquake(mag, place, time, weburl));
                }


            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earthquakes;
        }

        private static String makeHTTPRequest(URL url) throws IOException {
            String jsonResponse = "";
            if (url == null){
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }else {
                    Log.e("ERROR IN MAKEHTTP", "error response code" + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null){
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return output.toString();
        }

        private static URL createUrl(String stringurl){
            URL url = null;
            try {
                url = new URL(stringurl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
    }

