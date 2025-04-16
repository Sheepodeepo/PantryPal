package com.PantryPal.model.GeminiResponse;

import java.util.List;

public class GeminiPart {
    private String text;

    public GeminiPart() {
    }

    public GeminiPart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
