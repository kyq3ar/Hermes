package com.example.hermes.app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//import android.os.StrictMode;
//////////LOOK ABOVE
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.nio.charset.Charset;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

/**
 * Created by Jinhyun on 6/5/14.
 */
public class InformationExtractor {

    String sourceCode;
    String title;
    String description;
    String source;

    //=================TODO: CHANGED BY KAREN==================================//
    String image;
    Date date;

    public InformationExtractor(String url) throws IOException {
        source = url;
        sourceCode = getUrlSource(url);
        title = findTitle();
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    //=================TODO: CHANGED BY KAREN==================================//
    public String getImage(){
        try {
            Document doc = Jsoup.connect(source).get();
            Elements media = doc.select("[src]");
            String image = "";
            for (Element src : media) {
                if (src.tagName().equals("img")) {
                    try {
                        int size = Integer.parseInt(src.attr("width")) * Integer.parseInt(src.attr("height"));
                        if (size > 1024 * 30) {
                            image = src.attr("abs:src");
                            System.out.println("I WAS HEREEEE");
                            break;
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        continue;
                    }
                }
            }
            return image;
        }
        catch (IOException e){

        }
        return "";
    }

    public String getDescription(int position, String key) {
        int index = sourceCode.indexOf(key);

        if (index != -1) {
            index += position;
            String detail = sourceCode.substring(index, sourceCode.indexOf("<", index));
            if (detail.length() < 100)
                return detail;
            else
                return detail.substring(0, 97)+"...";
        }
        else {
            return title;
        }

    }

    public String getCompanyName() {
        if (source.contains("cnn"))
            return "CNN";
        else
            return "CNN";
    }
/*
    public ArrayList<String> getLinks(String after, String before) {
        int index = sourceCode.indexOf(after);
        int endIndex = sourceCode.indexOf(before, index);
        ArrayList<String> links = new ArrayList<String>();

        while (index < endIndex) {
            index = sourceCode.indexOf("<a href=\"", index)+9;
            links.add("http://www.cnn.com/"+sourceCode.substring(index, sourceCode.indexOf("\"", index)));
        }

        return links;
    }
    */
    public HashMap<String, String> getLink(String target, String after, String before) throws IOException {
        int index = sourceCode.indexOf(after);
        int endIndex = sourceCode.indexOf(before, index);
        HashMap<String, String> links = new HashMap<String, String>();
        while (index < endIndex) {
            index = sourceCode.indexOf("<a href=", index)+8;
            if (index > endIndex) break;
            System.out.println("HAHAHAHAHA" + sourceCode.charAt(index));
            if (sourceCode.indexOf("http", index) >= index + 20) {
                index = sourceCode.indexOf("/", index);
                String link = target + sourceCode.substring(index, sourceCode.indexOf(" ", index)-1);
                InformationExtractor info = new InformationExtractor(link);
                links.put(info.getTitle(), link);
            }
            else {
                index = sourceCode.indexOf("//", index);
                String link = "http:" + sourceCode.substring(index, sourceCode.indexOf(" ", index)-1);
                InformationExtractor info = new InformationExtractor(link);
                links.put(info.getTitle(), link);
            }

        }
        return links;
    }

    private String findTitle() {
        return sourceCode.substring(sourceCode.indexOf("<title>")+7, sourceCode.indexOf("</title>"));
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
//    //TODO: What is this pattern?
//    private static final Pattern TITLE_TAG =
//            Pattern.compile("\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
//
//
//    //TODO: Meta and other information
//    private static final Pattern META_TAG =
//            Pattern.compile("\\<meta>(.*)\\</meta>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
//
//    private static final Pattern DESCRIPTION_TAG =
//            Pattern.compile("\\<meta>(.*)\\</meta>", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
//
//
//    /**
//     * @param url the HTML page
//     * @return title text (null if document isn't HTML or lacks a title tag)
//     * @throws IOException
//     */
//    public static String getPageTitle(String url) throws IOException {
//        URL currentUrl = new URL(url);
//        URLConnection connection = currentUrl.openConnection();
//
//        // ContentType is an inner class defined below
//        ContentType contentType = getContentTypeHeader(connection);
//        if (!contentType.contentType.equals("text/html"))
//            return null; // don't continue if not HTML
//        else {
//            // determine the charset, or use the default
//            Charset charset = getCharset(contentType);
//            if (charset == null)
//                charset = Charset.defaultCharset();
//
//            // read the response body, using BufferedReader for performance
//            InputStream in = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
//            int n = 0, totalRead = 0;
//            char[] buf = new char[1024];
//            StringBuilder content = new StringBuilder();
//
//            // read until EOF or first 8192 characters
//            while (totalRead < 8192 && (n = reader.read(buf, 0, buf.length)) != -1) {
//                content.append(buf, 0, n);
//                totalRead += n;
//            }
//            reader.close();
//
//            // extract the title
//            Matcher matcher = TITLE_TAG.matcher(content);
//            if (matcher.find()) {
//                /* replace any occurrences of whitespace (which may
//                 * include line feeds and other uglies) as well
//                 * as HTML brackets with a space */
//                return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
//            }
//            else
//                return null;
//        }
//    }
//
//    /**
//     * Loops through response headers until Content-Type is found.
//     * @param conn
//     * @return ContentType object representing the value of
//     * the Content-Type header
//     */
//    private static ContentType getContentTypeHeader(URLConnection conn) {
//        int i = 0;
//        boolean moreHeaders = true;
//        do {
//            String headerName = conn.getHeaderFieldKey(i);
//            String headerValue = conn.getHeaderField(i);
//            if (headerName != null && headerName.equals("Content-Type"))
//                return new ContentType(headerValue);
//
//            i++;
//            moreHeaders = headerName != null || headerValue != null;
//        }
//        while (moreHeaders);
//
//        return null;
//    }
//
//    private static Charset getCharset(ContentType contentType) {
//        if (contentType != null && contentType.charsetName != null && Charset.isSupported(contentType.charsetName))
//            return Charset.forName(contentType.charsetName);
//        else
//            return null;
//    }
//
//    /**
//     * Class holds the content type and charset (if present)
//     */
//    private static final class ContentType {
//        private static final Pattern CHARSET_HEADER = Pattern.compile("charset=([-_a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
//
//        private String contentType;
//        private String charsetName;
//        private ContentType(String headerValue) {
//            if (headerValue == null)
//                throw new IllegalArgumentException("ContentType must be constructed with a not-null headerValue");
//            int n = headerValue.indexOf(";");
//            if (n != -1) {
//                contentType = headerValue.substring(0, n);
//                Matcher matcher = CHARSET_HEADER.matcher(headerValue);
//                if (matcher.find())
//                    charsetName = matcher.group(1);
//            }
//            else
//                contentType = headerValue;
//        }
//    }
//
//    public static String getDescription (String webPage) throws IOException {
//        try {
//            URL url = new URL(webPage);
//            URLConnection urlConnection = url.openConnection();
//            InputStream is = urlConnection.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//
//            int numCharsRead;
//            char[] charArray = new char[1024];
//            StringBuffer sb = new StringBuffer();
//            while ((numCharsRead = isr.read(charArray)) > 0) {
//                sb.append(charArray, 0, numCharsRead);
//            }
//            String result = sb.toString();
//            result = result.substring(result.indexOf("description")+22);
//            result = result.substring(0, result.indexOf("\""));
//            return result;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
