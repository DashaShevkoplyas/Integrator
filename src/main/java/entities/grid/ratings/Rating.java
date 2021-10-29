package entities.grid.ratings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {
    @JsonProperty("ele_id")
    private int ele_id;
    @JsonProperty("con_id")
    private int con_id;
    @JsonProperty("content")
    private int rating;

    public int getEle_id() {
        return ele_id;
    }

    public void setEle_id(int ele_id) {
        this.ele_id = ele_id;
    }

    public int getCon_id() {
        return con_id;
    }

    public void setCon_id(int con_id) {
        this.con_id = con_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
