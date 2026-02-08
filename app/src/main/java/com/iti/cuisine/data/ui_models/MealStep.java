package com.iti.cuisine.data.ui_models;

import java.util.ArrayList;
import java.util.List;

public class MealStep {
    private final String title;
    private final String content;

    public MealStep(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static List<MealStep> makeSteps(String rawText) {
        List<MealStep> steps = new ArrayList<>();

        String cleaned = rawText
                .replaceAll("[\r\n]+", " ")
                .replaceAll("(?i)step\\s*\\d*:?", "")
                .replaceAll("\\s+", " ")
                .trim();

        String[] sentences = cleaned.split("(?<=[.!?])\\s+");

        int stepNumber = 1;

        for (String sentence : sentences) {
            String content = sentence.trim();

            if (!content.isEmpty() && !content.matches("\\d+[.!?]*")) {
                String title = "Step " + stepNumber;
                steps.add(new MealStep(title, content));
                stepNumber++;
            }
        }

        return steps;
    }
}
