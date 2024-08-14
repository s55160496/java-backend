package com.example.backend.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecurityUtil {

    private SecurityUtil() {

    }

    public static String generateToken() {
        // create character rules to generate passwords with

        List<CharacterRule> rules = new ArrayList<CharacterRule>();

        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 10));

        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 10));

        //rules.add(new CharacterRule(EnglishCharacterData.Digit, 10));

        // rules.add(new CharacterRule(EnglishCharacterData.Special,5));

        PasswordGenerator generator = new PasswordGenerator();
        return generator.generatePassword(20, rules);
    }

    public static Optional<String> getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return Optional.empty();
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        String principal = (String) authentication.getPrincipal();

        if (principal == null) {
            return Optional.empty();
        }

        String userId = (String) principal;
        return Optional.of(userId);

    }
}
