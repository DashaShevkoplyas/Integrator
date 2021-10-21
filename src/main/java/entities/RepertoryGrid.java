package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.grid.Grid;

public class RepertoryGrid {
    @JsonProperty("grid")
    private Grid grid;

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
