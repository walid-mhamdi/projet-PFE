package ouachousoft.BackEnd0.dto;

public class PasswordValidator {
    public static String validatePassword(String password) {
        if (password.length() < 8) {
            return "Le mot de passe doit contenir au moins 8 caractères.";
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUpperCase) {
            return "Le mot de passe doit contenir au moins une lettre majuscule.";
        }
        if (!hasLowerCase) {
            return "Le mot de passe doit contenir au moins une lettre minuscule.";
        }
        if (!hasDigit) {
            return "Le mot de passe doit contenir au moins un chiffre.";
        }
        if (!hasSpecialChar) {
            return "Le mot de passe doit contenir au moins un caractère spécial.";
        }

        return null;
    }
}
