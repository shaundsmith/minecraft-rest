package dev.shaundsmith.minecraft.rest;

public class ClientResponse {

    private final int responseCode;
    private final String body;

    public ClientResponse(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getBody() {
        return body;
    }

}
