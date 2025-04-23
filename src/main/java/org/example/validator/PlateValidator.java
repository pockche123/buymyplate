package org.example.validator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class PlateValidator {

    public static class BannedWordsData {
        @JsonProperty("banned_words")
        public List<String> bannedWords;
        @JsonProperty("regex_patterns")
        public List<String> regexPatterns;
    }

    private final List<String> bannedWords;
    private final List<String> regexPatterns;

    public PlateValidator() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream is = new ClassPathResource("banned_words.json").getInputStream();

        BannedWordsData data = objectMapper.readValue(is, BannedWordsData.class);
        this.bannedWords = data.bannedWords;
        this.regexPatterns = data.regexPatterns;
    }

    public String normalisePlate(String input) {
        return input.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }

    public boolean checkContainsBannedWords(String input) {
        String normalized = normalisePlate(input);
        for (String banned : bannedWords) {
            if (normalized.contains(banned)) {
                System.out.println("matched with word: " + banned);
                return true;
            }
        }
        return false;
    }

    public boolean checkRegexMatch(String input) {
        for (String regex : regexPatterns) {
            if (!input.matches(regex)) {
                System.out.println("matched with regex: " + regex);
                return true;
            }
        }
        return false;
    }
}
