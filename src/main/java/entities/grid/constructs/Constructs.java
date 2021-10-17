package entities.grid.constructs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Constructs {
    @JsonProperty("construct")
    private List<Construct> constructs;

    public List<Construct> getConstructs() {
        return constructs;
    }

    public void setConstructs(List<Construct> constructs) {
        this.constructs = constructs;
    }
}
