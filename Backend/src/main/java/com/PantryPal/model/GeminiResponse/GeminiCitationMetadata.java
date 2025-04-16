package com.PantryPal.model.GeminiResponse;

import java.util.List;

public class GeminiCitationMetadata {
    private List<GeminiCitationSources> citationSources;

    public GeminiCitationMetadata() {
    }

    public GeminiCitationMetadata(List<GeminiCitationSources> citationSources) {
        this.citationSources = citationSources;
    }

    public List<GeminiCitationSources> getCitationSources() {
        return citationSources;
    }

    public void setCitationSources(List<GeminiCitationSources> citationSources) {
        this.citationSources = citationSources;
    }
}
