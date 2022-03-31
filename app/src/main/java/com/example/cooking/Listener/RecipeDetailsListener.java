package com.example.cooking.Listener;

import com.example.cooking.Models.RecipeDettailsRespone;

public interface RecipeDetailsListener {
    void didFetch(RecipeDettailsRespone respone, String message);
    void didError(String message);
}
