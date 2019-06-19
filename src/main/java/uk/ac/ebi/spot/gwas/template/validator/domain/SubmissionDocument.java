package uk.ac.ebi.spot.gwas.template.validator.domain;

import java.util.ArrayList;
import java.util.List;

public class SubmissionDocument {

    private List<Study> studyEntries;

    private List<Study> associationEntries;

    private List<Study> sampleEntries;

    private List<Study> noteEntries;

    public SubmissionDocument() {
        studyEntries = new ArrayList<>();
        associationEntries = new ArrayList<>();
        sampleEntries = new ArrayList<>();
        noteEntries = new ArrayList<>();
    }

    public List<Study> getStudyEntries() {
        return studyEntries;
    }

    public void setStudyEntries(List<Study> studyEntries) {
        this.studyEntries = studyEntries;
    }

    public List<Study> getAssociationEntries() {
        return associationEntries;
    }

    public void setAssociationEntries(List<Study> associationEntries) {
        this.associationEntries = associationEntries;
    }

    public List<Study> getSampleEntries() {
        return sampleEntries;
    }

    public void setSampleEntries(List<Study> sampleEntries) {
        this.sampleEntries = sampleEntries;
    }

    public List<Study> getNoteEntries() {
        return noteEntries;
    }

    public void setNoteEntries(List<Study> noteEntries) {
        this.noteEntries = noteEntries;
    }
}
