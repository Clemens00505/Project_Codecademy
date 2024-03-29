package objects;

public class AverageCompletionModules {
    private String title;
    private int version;
    private Double averageProgress; // Change float to Double

    public AverageCompletionModules() {

    }

    public AverageCompletionModules(String title, int version, Double averageProgress) { // Change float to Double
        this.title = title;
        this.version = version;
        this.averageProgress = averageProgress;
    }

    public String getTitle() {
        return title;
    }

    public int getVersion() {
        return version;
    }

    public Double getAverageProgress() { // Change float to Double
        return averageProgress;
    }
}
