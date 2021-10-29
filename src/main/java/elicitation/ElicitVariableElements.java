package elicitation;

import entities.grid.Grid;
import entities.grid.constructs.Construct;
import entities.grid.elements.Element;
import entities.grid.ratings.Rating;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ElicitVariableElements {
    private Grid grid;
    private List<Element> variableElements;
    private Map<Construct, List<Element>> variableElementsAndConstructs;

    public ElicitVariableElements(Grid grid) {
        this.grid = grid;
        variableElementsAndConstructs = new LinkedHashMap<>();
    }

    public Map<Construct, List<Element>> elicitVariableElementsAndConstructs() {
        List<Rating> ratingWithoutMinAndMax = filterWithoutMinAndMax();
        for (Construct construct : getConstructs()) {
            variableElements = new ArrayList<>();
            for (Rating rating : ratingWithoutMinAndMax) {
                if (construct.getId() != rating.getCon_id()) {
                    continue;
                }
                Element variableElement = getElementById(rating.getEle_id());
                variableElements.add(variableElement);
            }
            variableElementsAndConstructs.put(construct, variableElements);
        }
        return variableElementsAndConstructs;
    }

    private List<Rating> filterWithoutMinAndMax() {
        int min = grid.getRatings().getMin();
        int max = grid.getRatings().getMax();
        List<Rating> ratingWithoutMinAndMax = new ArrayList<>();
        for (Rating rating : grid.getRatings().getRatingList()) {
            if ((rating.getRating() == max) || (rating.getRating() == min)) {
                continue;
            }
            ratingWithoutMinAndMax.add(rating);
        }
        return ratingWithoutMinAndMax;
    }

    private Element getElementById(int elementId) {
        return grid.getElements().getElementList()
                .stream()
                .filter(element -> element.getId() == elementId)
                .findFirst()
                .orElse(null);
    }

    private List<Construct> getConstructs() {
        return grid.getConstructs().getConstructsList();
    }
}
