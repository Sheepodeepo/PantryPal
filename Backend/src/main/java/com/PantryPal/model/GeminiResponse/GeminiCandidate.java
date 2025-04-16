package com.PantryPal.model.GeminiResponse;

public class GeminiCandidate {
    private GeminiContent content;
    private String finishReason;
    private double avgLogprobs;
    private GeminiCitationMetadata citationMetadata;

    public GeminiCandidate() {
    }

    public GeminiCandidate(GeminiContent content, double avgLogprobs, String finishReason) {
        this.content = content;
        this.avgLogprobs = avgLogprobs;
        this.finishReason = finishReason;
    }


    public GeminiContent getContent() {
        return content;
    }

    public void setContent(GeminiContent content) {
        this.content = content;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public double getAvgLogprobs() {
        return avgLogprobs;
    }

    public void setAvgLogprobs(double avgLogprobs) {
        this.avgLogprobs = avgLogprobs;
    }

    public GeminiCitationMetadata getCitationMetadata() {
        return citationMetadata;
    }

    public void setCitationMetadata(GeminiCitationMetadata citationMetadata) {
        this.citationMetadata = citationMetadata;
    }
}
