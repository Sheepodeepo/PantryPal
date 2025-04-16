package com.PantryPal.model.GeminiRequest;

import java.util.List;

public class GeminiRequestContent {
    private List<GeminiRequestParts> contents;

    public GeminiRequestContent() {
    }

    public GeminiRequestContent(List<GeminiRequestParts> contents) {
        this.contents = contents;
    }

    public List<GeminiRequestParts> getContents() {
        return contents;
    }

    public void setContents(List<GeminiRequestParts> contents) {
        this.contents = contents;
    }
}
