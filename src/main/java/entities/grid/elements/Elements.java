package entities.grid.elements;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Elements {
    @JsonProperty("element")
    private List<Element> elements;

    public List<Element> getElementList() {
        return elements;
    }

    public void setElementList(List<Element> elements) {
        this.elements = elements;
    }
}
