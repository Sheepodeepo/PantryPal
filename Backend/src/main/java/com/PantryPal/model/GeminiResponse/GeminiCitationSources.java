package com.PantryPal.model.GeminiResponse;

public class GeminiCitationSources {
    private int startIndex;
    private int endIndex;
    private String uri;

    public GeminiCitationSources() {
    }

    public GeminiCitationSources(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public GeminiCitationSources(int startIndex, int endIndex, String uri) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.uri = uri;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
