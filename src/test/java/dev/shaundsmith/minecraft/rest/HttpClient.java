package dev.shaundsmith.minecraft.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Test helper for making HTTP requests.
 */
public class HttpClient {

    /**
     * Performs a GET request to the target URL.
     *
     * @param targetUrl the URL
     *
     * @return the response
     *
     * @throws IOException if an error occurs whilst making the request
     */
    public static ClientResponse get(String targetUrl) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            String body = "";
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                    body = reader.lines().collect(Collectors.joining(""));
                }
            }
            return new ClientResponse(connection.getResponseCode(), body);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

}
