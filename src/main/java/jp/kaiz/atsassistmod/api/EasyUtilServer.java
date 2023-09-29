package jp.kaiz.atsassistmod.api;

import java.net.HttpURLConnection;
import java.net.URL;

public class EasyUtilServer {
    public static class HTTP {
        public static void post(String url) {
            Runnable r = () -> {
                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r).start();
        }

        public static void postJson(String url, String postData) {
            Runnable r = () -> {
                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.getOutputStream().write(postData.getBytes());
                    con.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r).start();
        }

        public static void get(String url) {
            Runnable r = () -> {
                try {
                    URL u = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) u.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            new Thread(r).start();
        }

    }
}
