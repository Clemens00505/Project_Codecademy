package objects;

public enum Gender {
    MAN("MAN"),
    VROUW("VROUW"),
    ANDERS("ANDERS");

    private final String text;

    Gender(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    //method to change text from database to Gender type
    public static Gender fromString(String text) {
        if (text != null) {
            for (Gender gender : Gender.values()) {
                if (text.equalsIgnoreCase(gender.toString())) {
                    return gender;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

