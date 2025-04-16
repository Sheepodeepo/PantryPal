package com.PantryPal.model.GeminiResponse;

import java.util.List;

public class GeminiModelResponse {
    private List<GeminiCandidate> candidates;
    private GeminiMetadata usageMetadata;
    private String modelVersion;

    public GeminiModelResponse() {
    }

    public GeminiModelResponse(List<GeminiCandidate> candidates, GeminiMetadata usageMetadata, String modelVersion) {
        this.candidates = candidates;
        this.usageMetadata = usageMetadata;
        this.modelVersion = modelVersion;
    }

    public List<GeminiCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<GeminiCandidate> candidates) {
        this.candidates = candidates;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public GeminiMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(GeminiMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }
}
