package entities.grid.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Elements {
    @JsonProperty("element")
    private List<Element> elements;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
