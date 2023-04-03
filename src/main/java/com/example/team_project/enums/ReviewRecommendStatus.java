package com.example.team_project.enums;

public enum ReviewRecommendStatus {
    BEST("Best"),
    WORST("Worst"),
    CANCEL("Cancel");

    private final String name;

    ReviewRecommendStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
