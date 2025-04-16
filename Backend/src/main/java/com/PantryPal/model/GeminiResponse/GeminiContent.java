package com.PantryPal.model.GeminiResponse;
import java.util.List;

public class GeminiContent {
    private List<GeminiPart> parts;
    private String role;

    public GeminiContent() {
    }

    public GeminiContent(List<GeminiPart> parts, String role) {
        this.parts = parts;
        this.role = role;
    }

    public List<GeminiPart> getParts() {
        return parts;
    }

    public void setParts(List<GeminiPart> parts) {
        this.parts = parts;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
