package com.PantryPal.dto;

//https://talesofdancingcurls.medium.com/spring-boot-with-postgresql-a-step-by-step-guide-c451848f0184
//https://rameshfadatare.medium.com/spring-boot-crud-example-with-postgresql-926c87f0129a
public class GeminiDto {
    private String prompt;
    private String promptWrapper;
    private String finalPrompt;

    public GeminiDto() {

    }

    public GeminiDto(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
