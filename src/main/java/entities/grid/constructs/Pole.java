package entities.grid.constructs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pole {
    @JsonProperty("type")
    private String type;
    @JsonProperty("content")
    private String poleName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoleName() {
        return poleName;
    }

    public void setPoleName(String poleName) {
        this.poleName = poleName;
    }
}
