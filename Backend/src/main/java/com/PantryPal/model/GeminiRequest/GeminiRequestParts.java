package com.PantryPal.model.GeminiRequest;

import java.util.List;

public class GeminiRequestParts {
    private List<GeminiRequestText> parts;

    public GeminiRequestParts(List<GeminiRequestText> parts) {
        this.parts = parts;
    }

    public List<GeminiRequestText> getParts() {
        return parts;
    }

    public void setParts(List<GeminiRequestText> parts) {
        this.parts = parts;
    }
}
