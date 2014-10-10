package com.example.hermes.app;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Jinhyun on 6/6/14.
 */
public class WebsiteFinder {

    private static String CNN = "http://www.cnn.com";

    public static String searchTodayNews() throws IOException {
        return null;
    }


    public static String searchTodayNews(String company) throws IOException {
        return null;
    }

    public static String search(String query, int index) throws IOException {
        return null;
    }

    public static String searchCNN() throws IOException {
        return null;
    }

    //TODO: Right now, it only does US & World
    //US, World, Justice, Entertainment, Tech, Health, Living, Travel, Opinion, Money, Sports
    public static ArrayList<String> searchCNN(String section) throws IOException {
        InformationExtractor information = new InformationExtractor("http://www.cnn.com/"+section+"/?hpt=sitenav");


        //TODO: Change the location
        HashMap<String, String[][]> keywords = new HashMap<String, String[][]>();
        String [][] keyword = new String[][] {{"TOP U.S. STORIES", "</ul>"},
                {"Editor's Choice", "<div id=\"cnn_maintoplive\""},
                {"<div id=\"cnn_mtt1lftarea\">", "FULL STORY"}};
        keywords.put("US", keyword);
        keyword = new String[][] {{"Top World Stories", "</ul>"},
                {"Don't Miss","<div id=\"cnn_maintoplive\""},
                {"<div id=\"cnn_mtt1lftarea\">", "FULL STORY"}};
        keywords.put("WORLD", keyword);


        ArrayList<String> list = new ArrayList<String>
                (information.getLink(CNN, keywords.get(section)[0][0],
                        keywords.get(section)[0][1]).values());
        for (int i = 1; i < keywords.get(section).length; i++) {
            list.addAll(information.getLink(CNN, keywords.get(section)[i][0],
                            keywords.get(section)[i][1]).values());
        }
        return list;
        //return null;
    }


    public static String searchNYTimes() throws IOException {
        return null;
    }

    //World, US, New York, Opinions, Business, Technology, Science, Health, Sports, Arts, Fashion
    public static String searchNYTimes(String section) throws IOException {
        return null;
    }











//    //Google custom Search
//    public static String search(String query) throws IOException {
//
//        //Google API Custom Search
//        String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
//        String charset = "UTF-8";
//
//        URL url = new URL(address + URLEncoder.encode(query, charset));
//        Reader reader = new InputStreamReader(url.openStream(), charset);
//        //TODO: Learn
//        GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//
//        //TODO: Learn
//        //int total = results.getResponseData().getResults().size();
//        //System.out.println("total: " + total);
//        // Show title and URL of each results
//       /* for (int i = 0; i <= total - 1; i++) {
//            System.out.println("Title: " + results.getResponseData().getResults().get(i).getTitle());
//            System.out.println("URL: " + results.getResponseData().getResults().get(i).getUrl() + "\n");
//
//        }*/
//        return results.getResponseData().getResults().get(0).getUrl();
//    }
//}
//
//
////TODO: Read over
//class GoogleResults{
//
//    private ResponseData responseData;
//
//    public ResponseData getResponseData() {
//        return responseData;
//    }
//
//    public void setResponseData(ResponseData responseData) {
//        this.responseData = responseData;
//    }
//
//    public String toString() {
//        return "ResponseData[" + responseData + "]";
//    }
//
//    static class ResponseData {
//        private List<Result> results;
//
//        public List<Result> getResults() {
//            return results;
//        }
//
//        public void setResults(List<Result> results) {
//            this.results = results;
//        }
//        public String toString() {
//            return "Results[" + results + "]";
//        }
//    }
//
//    static class Result {
//        private String url;
//        private String title;
//        public String getUrl() {
//            return url;
//        }
//        public String getTitle() {
//            return title;
//        }
//        public void setUrl(String url) {
//            this.url = url;
//        }
//        public void setTitle(String title) {
//            this.title = title;
//        }
//        public String toString() {
//            return "Result[url:" + url +",title:" + title + "]";
//        }
//    }
}
