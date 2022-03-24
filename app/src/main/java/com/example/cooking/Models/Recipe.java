package com.example.cooking.Models;

import java.util.List;

public class Recipe {
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private int weightWatcherSmartPoints;
    private String gaps;
    private boolean lowFodmap;
    public int aggregateLikes;
    private int spoonacularScore;
    private int healthScore;
    private String creditsText;
    private String license;
    private String sourceName;
    private double pricePerServing;
    private List<ExtendedIngredients> extendedIngredients;
    private int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    private String sourceUrl;
    public String image;
    private String imageType;
    private String summary;
    private List<String> cuisines;
    private List<String> dishTypes;
    private List<String> diets;
    private List<String> occasions;
    private String instructions;
    private List<AnalyzedInstructions> analyzedInstructions;
    private String originalId;
    private String spoonacularSourceUrl;
}
