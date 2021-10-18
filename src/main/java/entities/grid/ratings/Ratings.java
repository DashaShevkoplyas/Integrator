package entities.grid.ratings;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ratings {
    @JsonProperty("scale")
    private String scale;
    @JsonProperty("min")
    private int min;
    @JsonProperty("max")
    private int max;
    @JsonProperty("rating")
    private List<Rating> ratings;

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}