package deanoffice.services;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.MarkRepository;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MarkService implements BaseService<Mark> {
    private static Logger log = Logger.getLogger(MarkService.class.getName());

    private MarkRepository markRepository;
    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;
    private TutorRepository tutorRepository;

    @Autowired
    public void setMarkRepository(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Autowired
    public void setSubjectRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setTutorRepository(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Override
    public Mark getObjectToAdd() {
        Mark newMark = new Mark();
        newMark.setId(-1);
        newMark.setTutor(new Tutor());
        newMark.setStudent(new Student());
        newMark.setSubject(new Subject());
        return newMark;
    }

    @Override
    public Mark getObjectToEdit(String id) {
        Optional<Mark> mark = markRepository.findById(Integer.valueOf(id));
        if (mark.isPresent()) {
            return mark.get();
        } else {
            throw new IllegalArgumentException("There is no mark with given id!");
        }
    }

    @Override
    public void removeObject(String id) {
        Optional<Mark> m = markRepository.findById(Integer.valueOf(id));
        m.ifPresent(mark -> markRepository.delete(mark));
    }

    @Override
    public List<Mark> getAll() {
        List<Mark> markList = new ArrayList<>();
        Iterable<Mark> marks = markRepository.findAll();
        marks.forEach(markList::add);
        return markList;
    }

    public void update(String id, String newValue, String studentId, String tutorId, String subjectName) {
        Optional<Mark> m = markRepository.findById(Integer.parseInt(id));
        log.info(String.format("Mark was found: %s", m.isPresent()));
        Mark mark = m.orElseGet(Mark::new);
        mark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentId));
        Optional<Tutor> t = tutorRepository.findById(Integer.valueOf(tutorId));

        Subject subject = subjectRepository.findByName(subjectName);

        mark.setValue(Double.valueOf(newValue));
        mark.setStudent(student);
        mark.setSubject(subject);
        t.ifPresent(mark::setTutor);
        log.info(String.format("Mark to save: %s", mark));
        markRepository.save(mark);
    }
}
