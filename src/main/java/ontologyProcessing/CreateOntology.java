package ontologyProcessing;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import utils.Constants;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//TODO: refactor by methods separation, logic correction and implementation of usage from RG
public class CreateOntology {
    private static final String PATH = Constants.RESOURCES_PATH + "owlFiles\\output";
    private static final String EXTENSION = ".owl";

    public void createOntology() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();
        try {
            OWLOntology ontology = manager.createOntology();
            String ns = "http://example.org/";
            OWLClass device = factory.getOWLClass(IRI.create(ns + "Device"));
            OWLClass computer = factory.getOWLClass(IRI.create(ns + "Computer"));
            OWLClass tv = factory.getOWLClass(IRI.create(ns + "TV"));
            OWLObjectProperty isA = factory.getOWLObjectProperty(IRI.create(ns + "isA"));
            OWLObjectProperty isUsedFor = factory.getOWLObjectProperty(IRI.create(ns + "isUsedFor"));
            OWLClass play = factory.getOWLClass(IRI.create(ns + "Play"));
            OWLClass chill = factory.getOWLClass(IRI.create(ns + "Chill"));
            Set<OWLClass> setDevices = new HashSet<>(Set.of(computer, tv));
            Set<OWLClass> setUsages = new HashSet<>(Set.of(play, chill));
            Set<AddAxiom> axioms = new HashSet<>();
            for (OWLClass owl : setDevices) {
                OWLAxiom axiom = factory.getOWLSubClassOfAxiom(owl, device);
                OWLAxiom axiom2 = factory.getOWLObjectPropertyRangeAxiom(isA, owl);
                AddAxiom addAxiom1 = new AddAxiom(ontology, axiom);
                AddAxiom addAxiom2 = new AddAxiom(ontology, axiom2);

                axioms.addAll(Set.of(addAxiom1, addAxiom2));
            }

            for (OWLClass owl : setUsages) {
                OWLAxiom axiom = factory.getOWLObjectPropertyRangeAxiom(isUsedFor, owl);
                AddAxiom addAxiom1 = new AddAxiom(ontology, axiom);

                axioms.addAll(Set.of(addAxiom1));
            }
            for (AddAxiom axiom : axioms) {
                manager.applyChange(axiom);
            }
            OWLAxiom axiom = factory.getOWLObjectPropertyDomainAxiom(isA, device);
            AddAxiom addAxiom1 = new AddAxiom(ontology, axiom);
            manager.applyChange(addAxiom1);

            OWLAxiom axiom2 = factory.getOWLObjectPropertyDomainAxiom(isUsedFor, computer);
            AddAxiom addAxiom2 = new AddAxiom(ontology, axiom2);
            manager.applyChange(addAxiom2);

            saveOntology(manager, ontology);

        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    private void saveOntology(OWLOntologyManager manager, OWLOntology ontology) {
        String fileName = PATH + getTimestamp();
        File file = new File(fileName + EXTENSION);
        try {
            manager.saveOntology(ontology, IRI.create(file));
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    private String getTimestamp() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }
}