package com.example.identity.user.passwordService;

import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

@Named
public class PasswordService {
    private final static Pattern hasUppercase = Pattern.compile("[A-Z]");
    private final static Pattern hasLowercase = Pattern.compile("[a-z]");
    private final static Pattern hasNumber = Pattern.compile("\\d");
    private final static Pattern hasSpecialChar = Pattern.compile("\\p{Punct}");
    private final static Argon2Types argon2Type = Argon2Types.ARGON2id;

    //todo make these values come from property file
    public String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(argon2Type);
        return argon2.hash(4, 2400, 2, password.toCharArray());
    }

    public boolean validatePasswords(String hash, String password) {
        Argon2 argon2 = Argon2Factory.create(argon2Type);
        return argon2.verify(hash, password.toCharArray());
    }

    public void validatePasswordStrength(String password) throws Exception {
        /* password must have the following requirements
        1 Special Character
        1 Uppercase
        1 Lowercase
        1 Number
        12 Total Characters
        */

        if (password.length() < 10) {
            throw new Exception("Password Length");
//            validationException.addField(
//                "passwordLength",
//                getString("PasswordStrength.Length", request.getLocale())
//            );
        }

        if (!hasUppercase.matcher(password).find()) {
            throw new Exception("Password needs uppercase letter");
//            validationException.addField(
//                "hasUppercase",
//                getString("PasswordStrength.Uppercase", request.getLocale())
//            );
        }

        if (!hasLowercase.matcher(password).find()) {
            throw new Exception("Password needs lowercase letter");
//            validationException.addField(
//                "hasLowercase",
//                getString("PasswordStrength.Lowercase", request.getLocale()));
        }

        if (!hasNumber.matcher(password).find()) {
            throw new Exception("Password needs a number");
//            validationException.addField(
//                "hasNumber",
//                getString("PasswordStrength.Number", request.getLocale())
//            );
        }

        if (!hasSpecialChar.matcher(password).find()) {
            throw new Exception("Password needs a special character");
//            validationException.addField(
//                "hasSpecial",
//                getString("PasswordStrength.Special", request.getLocale())
//            );
        }

//        if (validationException.hasErrors()) {
//            throw validationException;
//        }
    }

//todo manage password history

//    public void checkPasswordHistory(UUID userUuid, String newPassword) {
//
//        List<String> oldPasswords = recentPasswordRepository
//            .getMostRecentPasswords(userUuid, passwordProperties.getOldPasswordThreshold());
//
//        for (String oldPassword : oldPasswords) {
//            if (validatePasswords(oldPassword, newPassword)) {
//                throw new ValidationException().addField("oldPassword", getString("OldPassword", request.getLocale()));
//            }
//        }
//    }

//    public PasswordHistoryEntry recordPasswordHistory(User user) {
//        PasswordHistoryEntry passwordHistoryEntry = PasswordHistoryEntry.builder()
//            .userUuid(user.getUuid())
//            .password(user.getPassword())
//            .build();
//
//        return recentPasswordRepository.save(passwordHistoryEntry);
//    }
}
