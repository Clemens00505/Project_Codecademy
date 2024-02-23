package objects;

public enum Gender {
    MAN("man"),
    VROUW("vrouw"),
    ANDERS("anders");

    private final String text;

    Gender(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    //method to change text from database to Gender type
    public static Gender fromString(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.text.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

