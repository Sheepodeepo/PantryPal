package com.PantryPal.model.GeminiResponse;

public class GeminiPromptDetails {
    private String modality;
    private int tokenCount;

    public GeminiPromptDetails() {
    }

    public GeminiPromptDetails(String modality, int tokenCount) {
        this.modality = modality;
        this.tokenCount = tokenCount;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }
}
