package entities.grid.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {
    @JsonProperty("id")
    private String id;
    @JsonProperty("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
