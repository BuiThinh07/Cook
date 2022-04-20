package com.example.cooking.Listener;

import com.example.cooking.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String messager);
    void didError(String messager);
}
