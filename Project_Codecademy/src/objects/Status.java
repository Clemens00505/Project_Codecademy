package objects;

public enum Status {
    CONCEPT("CONCEPT"),
    ACTIEF("ACTIEF"),
    GEARCHIVEERD("GEARCHIVEERD");

    private final String text;

    Status (String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    //method to change text from database to Gender type
    public static Status fromString(String text) {
        if (text != null) {
            for (Status status : Status.values()) {
                if (text.equalsIgnoreCase(status.toString())) {
                    return status;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}




