package deanoffice.noentities;

import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;

public class TutorSubject {
    Tutor tutor;
    Subject subject;

    public TutorSubject(Tutor tutor, Subject subject) {
        this.tutor = tutor;
        this.subject = subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
