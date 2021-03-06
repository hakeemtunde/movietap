package com.gudacity.scholar.movietap.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Client {

    private static final String TAG = Client.class.getSimpleName();

    private HttpURLConnection clientConnection;
    private BufferedReader bufferedReader;
    private int statusCode;

    public Client() {}

    public String makeHttpRequest(String moviePath) throws IOException {

        URL url = new URL(moviePath);
        clientConnection = (HttpURLConnection)url.openConnection();

        StringBuilder responseStringBuilder = new StringBuilder();
        String inputLine;

        statusCode = clientConnection.getResponseCode();

        bufferedReader = new BufferedReader(
                new InputStreamReader(clientConnection.getInputStream()));

        while ((inputLine = bufferedReader.readLine()) != null) {
            responseStringBuilder.append(inputLine);
        }

        return responseStringBuilder.toString();

    }

    public void disconnect() throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }

        clientConnection.disconnect();
    }

    public int getStatusCode() {
        return statusCode;
    }


}
