package entities.grid.constructs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Construct {
    @JsonProperty("id")
    private int id;
    @JsonProperty("weight")
    private int weight;
    @JsonProperty("pole")
    private List<Pole> poles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Pole> getPoles() {
        return poles;
    }

    public void setPoles(List<Pole> poles) {
        this.poles = poles;
    }
}
