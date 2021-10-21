package entities.grid.constructs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Constructs {
    @JsonProperty("construct")
    private List<Construct> constructs;

    public List<Construct> getConstructsList() {
        return constructs;
    }

    public void setConstructsList(List<Construct> constructs) {
        this.constructs = constructs;
    }
}
