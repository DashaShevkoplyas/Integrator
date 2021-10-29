package entities.grid.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {
    @JsonProperty("id")
    private int id;
    @JsonProperty("content")
    private String elementName;
    @JsonProperty("class")
    private String elementClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getElementClass() {
        return elementClass;
    }

    public void setElementClass(String elementClass) {
        this.elementClass = elementClass;
    }
}
