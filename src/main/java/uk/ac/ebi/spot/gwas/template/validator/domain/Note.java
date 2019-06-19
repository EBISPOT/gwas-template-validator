package uk.ac.ebi.spot.gwas.template.validator.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Note {

    private String studyTag;

    private String note;

    private String noteSubject;

    private String status;

    public Note(String studyTag, String note, String noteSubject, String status) {
        this.studyTag = studyTag;
        this.note = note;
        this.noteSubject = noteSubject;
        this.status = status;
    }

    public String getStudyTag() {
        return studyTag;
    }

    public String getNote() {
        return note;
    }

    public String getNoteSubject() {
        return noteSubject;
    }

    public String getStatus() {
        return status;
    }
}
