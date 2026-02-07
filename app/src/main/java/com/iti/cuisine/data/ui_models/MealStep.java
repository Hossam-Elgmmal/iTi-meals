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
                .replace("\\r\\n", "\n")
                .replace("\\n", "\n")
                .trim();

        String[] parts = cleaned.split("(?i)step\\s+\\d+");

        int stepNumber = 1;

        for (int i = 1; i < parts.length; i++) {
            String content = parts[i].trim();

            content = content.replaceAll("\n{3,}", "\n\n");

            String title = "Step " + stepNumber;

            steps.add(new MealStep(title, content));
            stepNumber++;
        }

        return steps;
    }
}
