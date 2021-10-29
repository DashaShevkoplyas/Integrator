package ontologyCreator;

import entities.grid.constructs.Construct;
import entities.grid.constructs.Pole;
import entities.grid.elements.Element;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import utils.Constants;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//TODO: refactor by methods separation, logic correction and implementation of usage from RG
public class ProcessOntology {
    private static final String PATH = Constants.RESOURCES_PATH + "owlFiles\\output";
    private static final String EXTENSION = ".owl";
    private static final String NS = "http://example.org/";

    private final Set<AddAxiom> axioms;
    private final OWLOntologyManager manager;
    private final OWLDataFactory factory;

    public ProcessOntology() {
        manager = OWLManager.createOWLOntologyManager();
        factory = manager.getOWLDataFactory();
        axioms = new HashSet<>();
    }

    public void createOntology(Map<Construct, List<Element>> variableElements) {
        try {
            OWLOntology ontology = manager.createOntology();
            OWLClass device = factory.getOWLClass(IRI.create(NS + "Device"));
            OWLClass condition = factory.getOWLClass(IRI.create(NS + "Condition"));
            OWLObjectProperty isA = factory.getOWLObjectProperty(IRI.create(NS + "isA"));
            OWLObjectProperty isUsedInCondition = factory.getOWLObjectProperty(IRI.create(NS + "isUsedInCondition"));

            Set<OWLClass> devicesSubClasses = createOWLClassForElements(variableElements, factory);
            Set<OWLClass> conditionsSubClasses = createOWLClassForConstructs(variableElements, factory);

            addAxiomForRootElement(ontology, device, isA);
            addAxiomForRootElement(ontology, condition, isA);
            addAxioms(ontology, device, isA, devicesSubClasses);
            addAxioms(ontology, condition, isA, conditionsSubClasses);

            for (AddAxiom axiom : axioms) {
                manager.applyChange(axiom);
            }
            saveOntology(manager, ontology);
        } catch (OWLOntologyCreationException e) {
            JOptionPane.showMessageDialog(null, "Error while creating ontology: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAxiomForRootElement(OWLOntology ontology, OWLClass owlClass, OWLObjectProperty objectProperty) {
        OWLAxiom hasProperty = factory.getOWLObjectPropertyDomainAxiom(objectProperty, owlClass);
        AddAxiom isAnElement = new AddAxiom(ontology, hasProperty);
        axioms.add(isAnElement);
    }

    private void addAxioms(OWLOntology ontology, OWLClass owlClass, OWLObjectProperty objectProperty, Set<OWLClass> owlClassSet) {
        for (OWLClass deviceSubClass : owlClassSet) {
            OWLAxiom isASubClass = factory.getOWLSubClassOfAxiom(deviceSubClass, owlClass);
            OWLAxiom hasProperty = factory.getOWLObjectPropertyRangeAxiom(objectProperty, deviceSubClass);
            AddAxiom classIsSubClassOf = new AddAxiom(ontology, isASubClass);
            AddAxiom classHasProperty = new AddAxiom(ontology, hasProperty);

            axioms.addAll(Set.of(classIsSubClassOf, classHasProperty));
        }
    }

    private Set<OWLClass> createOWLClassForElements(Map<Construct, List<Element>> variableElements, OWLDataFactory factory) {
        Set<OWLClass> owlElements = new HashSet<>();
        for (String element : getUniqueVariableElementNames(variableElements)) {
            owlElements.add(factory.getOWLClass(IRI.create(NS + element)));
        }
        return owlElements;
    }

    private Set<String> getUniqueVariableElementNames(Map<Construct, List<Element>> variableGridData) {
        Set<String> variableElementNames = new HashSet<>();
        for (Map.Entry<Construct, List<Element>> variableData : variableGridData.entrySet()) {
            for (Element variableElement : variableData.getValue()) {
                if (variableElementNames.contains(variableElement.getElementName())) {
                    continue;
                }
                variableElementNames.add(variableElement.getElementName());
            }
        }
        return variableElementNames;
    }

    private Set<OWLClass> createOWLClassForConstructs(Map<Construct, List<Element>> variableGridData, OWLDataFactory factory) {
        Set<OWLClass> variableConstructNames = new HashSet<>();
        for (Construct variableConstruct : variableGridData.keySet()) {
            for (Pole constructPole : variableConstruct.getPoles()) {
                variableConstructNames.add(factory.getOWLClass(IRI.create(NS + constructPole.getPoleName())));
            }
        }
        return variableConstructNames;
    }

    private void saveOntology(OWLOntologyManager manager, OWLOntology ontology) {
        String fileName = PATH + getTimestamp();
        File file = new File(fileName + EXTENSION);
        try {
            manager.saveOntology(ontology, IRI.create(file));
        } catch (OWLOntologyStorageException e) {
            JOptionPane.showMessageDialog(null, "Error while saving ontology: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }
}
