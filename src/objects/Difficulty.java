package objects;

public enum Difficulty {
    BEGINNER("BEGINNER"),
    GEVORDERD("GEVORDERD"),
    EXPERT("EXPERT");

    private final String text;

    Difficulty (String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    //method to change text from database to Difficulty type
    public static Difficulty fromString(String text) {
        if (text != null) {
            for (Difficulty difficulty : Difficulty.values()) {
                if (text.equalsIgnoreCase(difficulty.toString())) {
                    return difficulty;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}




