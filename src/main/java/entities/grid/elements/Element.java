package entities.grid.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {
    @JsonProperty("id")
    private int id;
    @JsonProperty("content")
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
