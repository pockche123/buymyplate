package org.example.validator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlateValidator {

    public static class BannedWordsData {
        @JsonProperty("banned_words")
        public List<String> bannedWords;
        @JsonProperty("regex_patterns")
        public List<String> regexPatterns;
    }

    private List<String> bannedWords;
    private List<String> regexPatterns;

    public PlateValidator(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream(path);
        if(is == null){
            throw new FileNotFoundException("Resource not found: " + path);
        }
        BannedWordsData bannedWordsData = objectMapper.readValue(is,BannedWordsData.class);
        this.bannedWords = bannedWordsData.bannedWords;
        this.regexPatterns = bannedWordsData.regexPatterns;


    }

    public String normalisePlate(String input){
        return input.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }

    public boolean checkContainsBannedWords(String input){
        String normalisedInput = normalisePlate(input);
        for(String bannedWord : bannedWords){
            if(normalisedInput.contains(bannedWord)) {
                return true;
            }
        }
        return false;
    }
}
