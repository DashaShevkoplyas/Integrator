package translation;

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

public class TranslateToOWL {
    private static final String PATH = Constants.RESOURCES_PATH + "owlFiles\\output";
    private static final String EXTENSION = ".owl";
    private static final String NS = "http://example.org/";

    private final Set<AddAxiom> axioms;
    private final OWLOntologyManager manager;
    private final OWLDataFactory factory;

    public TranslateToOWL() {
        manager = OWLManager.createOWLOntologyManager();
        factory = manager.getOWLDataFactory();
        axioms = new HashSet<>();
    }

    public void translateToOWL(Map<Construct, List<Element>> variableElements) {
        try {
            OWLOntology ontology = manager.createOntology();
            OWLClass device = factory.getOWLClass(IRI.create(NS + "Device"));
            OWLClass condition = factory.getOWLClass(IRI.create(NS + "Condition"));
            OWLObjectProperty isA = factory.getOWLObjectProperty(IRI.create(NS + "isA"));
            OWLObjectProperty isUsedInCondition = factory.getOWLObjectProperty(IRI.create(NS + "isUsedInCondition"));
            OWLObjectProperty isAPartOf = factory.getOWLObjectProperty(IRI.create(NS + "isAPartOf"));

            Map<Set<OWLClass>, Set<OWLClass>> variableOWLClasses = convertVariableDataIntoVariableOWLClasses(variableElements, factory);
            Set<OWLClass> devicesSubClasses = getDeviceSubClasses(variableOWLClasses);
            Set<OWLClass> conditionsSubClasses = getConditionSubClasses(variableOWLClasses);

            addAxiomRangeForRootElement(ontology, device, isA);
            addAxioms(ontology, device, isA, devicesSubClasses);
            addAxiomRangeForRootElement(ontology, condition, isAPartOf);
            addAxioms(ontology, condition, isAPartOf, conditionsSubClasses);
            addAxiomsForVariableElements(variableOWLClasses, ontology, isUsedInCondition.getIRI().getFragment());

            for (AddAxiom axiom : axioms) {
                manager.applyChange(axiom);
            }

            saveOntology(manager, ontology);
        } catch (OWLOntologyCreationException e) {
            JOptionPane.showMessageDialog(null, "Error while creating ontology: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Set<OWLClass> getDeviceSubClasses(Map<Set<OWLClass>, Set<OWLClass>> variableOWLClasses) {
        Set<OWLClass> variableElements = new HashSet<>();
        for (Map.Entry<Set<OWLClass>, Set<OWLClass>> variableData : variableOWLClasses.entrySet()) {
            for (OWLClass variableElement : variableData.getValue()) {
                if (variableElements.contains(variableElement)) {
                    continue;
                }
                variableElements.add(variableElement);
            }
        }
        return variableElements;
    }

    private Set<OWLClass> getConditionSubClasses(Map<Set<OWLClass>, Set<OWLClass>> variableOWLClasses) {
        Set<OWLClass> conditionSubClasses = new HashSet<>();
        for (Map.Entry<Set<OWLClass>, Set<OWLClass>> variableClasses : variableOWLClasses.entrySet()) {
            conditionSubClasses.addAll(variableClasses.getKey());
        }
        return conditionSubClasses;
    }

    private Map<Set<OWLClass>, Set<OWLClass>> convertVariableDataIntoVariableOWLClasses(Map<Construct, List<Element>> variableGridData, OWLDataFactory factory) {
        Set<OWLClass> variableConstructNames;
        Set<OWLClass> variableElementNames;
        Map<Set<OWLClass>, Set<OWLClass>> variableOWLClasses = new LinkedHashMap<>();
        for (Map.Entry<Construct, List<Element>> variableData : variableGridData.entrySet()) {
            variableConstructNames = new HashSet<>();
            variableElementNames = new HashSet<>();
            Construct variableConstruct = variableData.getKey();
            for (Pole pole : variableConstruct.getPoles()) {
                variableConstructNames.add(factory.getOWLClass(IRI.create(NS + pole.getPoleName())));
            }

            List<Element> variableElementList = variableData.getValue();
            for (Element variableElement : variableElementList) {
                variableElementNames.add(factory.getOWLClass(IRI.create(NS + variableElement.getElementName())));
            }
            variableOWLClasses.put(variableConstructNames, variableElementNames);
        }
        return variableOWLClasses;
    }

    private void addAxiomsForVariableElements(Map<Set<OWLClass>, Set<OWLClass>> variableOWLClasses, OWLOntology ontology, String objectProperty) {
        int index = 1;
        for (Map.Entry<Set<OWLClass>, Set<OWLClass>> variableData : variableOWLClasses.entrySet()) {
            OWLObjectProperty property = factory.getOWLObjectProperty(IRI.create(NS + objectProperty + index));
            Set<OWLClass> variableConstructs = variableData.getKey();
            variableConstructs.forEach(variableConstruct -> addAxiomRangeForRootElement(ontology, variableConstruct, property));
            Set<OWLClass> variableElements = variableData.getValue();
            variableElements.forEach(variableElement -> addAxiomDomainForRootElement(ontology, variableElement, property));
            index++;
        }
    }

    private void addAxiomRangeForRootElement(OWLOntology ontology, OWLClass owlClass, OWLObjectProperty objectProperty) {
        OWLAxiom hasProperty = factory.getOWLObjectPropertyRangeAxiom(objectProperty, owlClass);
        AddAxiom isAnElement = new AddAxiom(ontology, hasProperty);
        axioms.add(isAnElement);
    }

    private void addAxiomDomainForRootElement(OWLOntology ontology, OWLClass owlClass, OWLObjectProperty objectProperty) {
        OWLAxiom hasProperty = factory.getOWLObjectPropertyDomainAxiom(objectProperty, owlClass);
        AddAxiom isAnElement = new AddAxiom(ontology, hasProperty);
        axioms.add(isAnElement);
    }

    private void addAxioms(OWLOntology ontology, OWLClass owlClass, OWLObjectProperty objectProperty, Set<OWLClass> owlClassSet) {
        for (OWLClass subClass : owlClassSet) {
            OWLAxiom isASubClass = factory.getOWLSubClassOfAxiom(subClass, owlClass);
            OWLAxiom hasProperty = factory.getOWLObjectPropertyDomainAxiom(objectProperty, subClass);
            AddAxiom classIsSubClassOf = new AddAxiom(ontology, isASubClass);
            AddAxiom classHasProperty = new AddAxiom(ontology, hasProperty);

            axioms.addAll(Set.of(classIsSubClassOf, classHasProperty));
        }
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
