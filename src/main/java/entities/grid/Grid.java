package entities.grid;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.grid.constructs.Constructs;
import entities.grid.elements.Elements;
import entities.grid.ratings.Ratings;

public class Grid {
    @JsonProperty("id")
    private int id;
    @JsonProperty("images")
    private String images;
    @JsonProperty("xmlns:noNameSpaceLocation")
    private String spaceLocation;
    @JsonProperty("xmlns:xsi")
    private String xsi;
    @JsonProperty("header")
    private Header header;
    @JsonProperty("elements")
    private Elements elements;
    @JsonProperty("constructs")
    private Constructs constructs;
    @JsonProperty("ratings")
    private Ratings ratings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpaceLocation() {
        return spaceLocation;
    }

    public void setSpaceLocation(String spaceLocation) {
        this.spaceLocation = spaceLocation;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public void setImages(String images) {
        this.images = images;
    }

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

    public String getImages() {
        return images;
    }
}
