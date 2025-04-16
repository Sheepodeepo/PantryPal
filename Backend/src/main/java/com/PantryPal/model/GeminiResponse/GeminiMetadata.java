package com.PantryPal.model.GeminiResponse;

import java.util.List;

public class GeminiMetadata {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;
    private List<GeminiPromptDetails> promptTokensDetails;
    private List<GeminiCandidatesDetails> candidatesTokensDetails;

    public GeminiMetadata() {
    }

    public GeminiMetadata(int promptTokenCount, List<GeminiCandidatesDetails> candidatesTokensDetails, List<GeminiPromptDetails> promptTokensDetails, int totalTokenCount, int candidatesTokenCount) {
        this.promptTokenCount = promptTokenCount;
        this.candidatesTokensDetails = candidatesTokensDetails;
        this.promptTokensDetails = promptTokensDetails;
        this.totalTokenCount = totalTokenCount;
        this.candidatesTokenCount = candidatesTokenCount;
    }

    public int getPromptTokenCount() {
        return promptTokenCount;
    }

    public void setPromptTokenCount(int promptTokenCount) {
        this.promptTokenCount = promptTokenCount;
    }

    public int getCandidatesTokenCount() {
        return candidatesTokenCount;
    }

    public void setCandidatesTokenCount(int candidatesTokenCount) {
        this.candidatesTokenCount = candidatesTokenCount;
    }

    public int getTotalTokenCount() {
        return totalTokenCount;
    }

    public void setTotalTokenCount(int totalTokenCount) {
        this.totalTokenCount = totalTokenCount;
    }

    public List<GeminiPromptDetails> getPromptTokensDetails() {
        return promptTokensDetails;
    }

    public void setPromptTokensDetails(List<GeminiPromptDetails> promptTokensDetails) {
        this.promptTokensDetails = promptTokensDetails;
    }

    public List<GeminiCandidatesDetails> getCandidatesTokensDetails() {
        return candidatesTokensDetails;
    }

    public void setCandidatesTokensDetails(List<GeminiCandidatesDetails> candidatesTokensDetails) {
        this.candidatesTokensDetails = candidatesTokensDetails;
    }
}
