package com.example.hermes.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jinhyun on 6/5/14.
 */
public class DetailActivity extends Activity {


    //TODO: MIGHT HAVE TO FIX
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        String sourceLink = extras.getString("sourceLink");
        String description = extras.getString("description");
        String title = extras.getString("title");
        String companyName = extras.getString("companyName");


        //TODO: Understand this and fix the red line
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //TODO: Working, need to move someplace else
        try {
            //title = InformationExtractor.getPageTitle(sourceLink);
            //title = getUrlSource(sourceLink);
            InformationExtractor information = new InformationExtractor(sourceLink);
            title = information.getTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: Change the actionBar
        setTitle(companyName + ": " + title);



        //Make sure to add internet permission in Android Manifest
        WebView detailWebView = (WebView) findViewById(R.id.webview);
        //TODO: Needs to fix this part for optimization
        detailWebView.loadUrl(sourceLink);





















        /*
        String titles = null;
        try {
            titles = InformationExtractor.getPageTitle("http://en.wikipedia.org/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),
                titles, Toast.LENGTH_LONG)
                .show();
                */





        /*
        try {
            getLink();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        /*
        try {
            Toast.makeText(getApplicationContext(),
                    getWebPabeSource("http://www.cnn.com/2014/06/04/world/asia/north-korea-fishermen/index.html?hpt=wo_c2"), Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        /*
        try {
            Toast.makeText(getApplicationContext(),
                    getUrlSource("http://www.cnn.com/2014/06/04/world/asia/north-korea-fishermen/index.html?hpt=wo_c2"), Toast.LENGTH_LONG)
                    .show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        /*try {
            getHtml();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //TODO: Change here
        /*
        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                DetailActivity.this.setTitle(view.getTitle());
            }
        });*/
        /*
        WebView detailWebView = (WebView) findViewById(R.id.webview);
        detailWebView.loadUrl(sourceLink);

        HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
        HttpGet httpget = new HttpGet(sourceLink); // Set the action you want to do
        HttpResponse response = null; // Executeit
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        InputStream is = null; // Create an InputStream with the response
        try {
            is = entity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) // Read line by line
                sb.append(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resString = sb.toString(); // Result is here

        try {
            is.close(); // Close the stream
        } catch (IOException e) {
            e.printStackTrace();
        }

        String temp = resString.substring(resString.indexOf("<title>"),resString.indexOf("</title>"));
        */







    }





    /*

    public void getHtml() throws ClientProtocolException, IOException
    {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://www.spartanjava.com");
        HttpResponse response = httpClient.execute(httpGet, localContext);
        String result = "";

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        response.getEntity().getContent()
                )
        );

        String line = null;
        while ((line = reader.readLine()) != null){
            result += line + "\n";
            Toast.makeText(DetailActivity.this, line.toString(), Toast.LENGTH_LONG).show();

        }

    }

    public void getLink() throws IOException {

        HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
        HttpGet httpget = new HttpGet("http://www.google.com"); // Set the action you want to do
        HttpResponse response = httpclient.execute(httpget); // Executeit
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent(); // Create an InputStream with the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) // Read line by line
            sb.append(line + "\n");

        String resString = sb.toString(); // Result is here

        is.close(); // Close the stream

        //String resString = "HELLLLLO";
        Toast.makeText(getApplicationContext(),
                resString, Toast.LENGTH_LONG)
                .show();
    }

    private static String getWebPabeSource(String sURL) throws IOException {
        URL url = new URL(sURL);
        URLConnection urlCon = url.openConnection();
        BufferedReader in = null;

        if (urlCon.getHeaderField("Content-Encoding") != null
                && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
            in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    urlCon.getInputStream())));
        } else {
            in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
        }

        String inputLine;
        StringBuilder sb = new StringBuilder();

        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();

        return sb.toString();
    }

    private static String getUrlSource(String url) throws IOException {
        URL yahoo = new URL(url);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();
    }
    */

    //TODO: This is how to do toast
                /*
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
                */

    /*
    public static String getDescription (String webPage) throws IOException {
            try {
                URL url = new URL(webPage);
                URLConnection urlConnection = url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int numCharsRead;
                char[] charArray = new char[1024];
                StringBuffer sb = new StringBuffer();
                while ((numCharsRead = isr.read(charArray)) > 0) {
                    sb.append(charArray, 0, numCharsRead);
                }
                String result = sb.toString();
                result = result.substring(result.indexOf("description")+22);
                result = result.substring(0, result.indexOf("\""));
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
     */
}
