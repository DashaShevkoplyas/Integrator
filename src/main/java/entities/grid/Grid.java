package entities.grid;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.grid.constructs.Constructs;
import entities.grid.elements.Elements;
import entities.grid.ratings.Ratings;

public class Grid {
    @JsonProperty("header")
    private Header header;
    @JsonProperty("elements")
    private Elements elements;
    @JsonProperty("constructs")
    private Constructs constructs;
    @JsonProperty("ratings")
    private Ratings ratings;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public Constructs getConstructs() {
        return constructs;
    }

    public void setConstructs(Constructs constructs) {
        this.constructs = constructs;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }
}
