package deanoffice.controllers.admin;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.noentities.StudentSubject;
import deanoffice.noentities.TutorSubject;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;

@Controller
public class AdminManyToManyController {
    private final static Logger log = Logger.getLogger(AdminManyToManyController.class.getName());
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/admin/studenttosubject/", method = RequestMethod.GET)
    public ModelAndView allStudentToSubject(){
        ModelAndView model = new ModelAndView("/admin/studenttosubject.html");
        Iterable<Subject> subjects = subjectRepository.findAll();

        ArrayList<StudentSubject> couples = new ArrayList<>();

        for(Subject subject : subjects){
            for(Student std : subject.getStudents()){
                couples.add(new StudentSubject(std, subject));
            }
        }

        model.addObject("couples",couples);
        return model;
    }

    @RequestMapping(value = "/admin/tutortosubject/", method = RequestMethod.GET)
    public ModelAndView allTutorToSubject(){
        ModelAndView model = new ModelAndView("/admin/tutortosubject.html");
        Iterable<Subject> subjects = subjectRepository.findAll();

        ArrayList<TutorSubject> couples = new ArrayList<>();

        for(Subject subject : subjects){
            for(Tutor tutor : subject.getTutors()){
                couples.add(new TutorSubject(tutor, subject));
            }
        }

        model.addObject("couples",couples);
        return model;
    }

    @RequestMapping(value = "/admin/studenttosubject/add", method = RequestMethod.GET)
    public ModelAndView addStudentToSubject(){
        return new ModelAndView("/admin/adding/addstudenttosubject.html");
    }

    @RequestMapping(value = "/admin/studenttosubject/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddStudentToSubject(HttpServletRequest request){
        String subjectid = request.getParameter("subjectid");
        String studentid = request.getParameter("studentid");
        log.info("To by nie zadzialalo: " + subjectid + " " + studentid);
        Subject subject = subjectRepository.findById(Integer.valueOf(subjectid)).get();
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));

        subject.getStudents().add(student);
        student.getSubjects().add(subject);

        subjectRepository.save(subject);
        studentRepository.save(student);

        return this.allStudentToSubject();
    }

    @RequestMapping(value = "/admin/studenttosubject/delete", method = RequestMethod.POST)
    public ModelAndView deleteStudentToSubject(HttpServletRequest request){
        String parameter = request.getParameter("parameter");
        log.info("To by nie zadzialalo: " + parameter );
        String[] ids = parameter.split("!");
        String studentid = ids[0];
        String subjectid = ids[1];

        Subject subject = subjectRepository.findById(Integer.valueOf(subjectid)).get();
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));

        subject.getStudents().remove(student);
        student.getSubjects().remove(subject);

        subjectRepository.save(subject);
        studentRepository.save(student);

        return this.allStudentToSubject();
    }

    @RequestMapping(value = "/admin/tutortosubject/add", method = RequestMethod.GET)
    public ModelAndView addTutorToSubject(){
        return new ModelAndView("/admin/adding/addtutortosubject.html");
    }

    @RequestMapping(value = "/admin/tutortosubject/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddTutorToSubject(HttpServletRequest request){
        String subjectid = request.getParameter("subjectid");
        String tutorid = request.getParameter("tutorid");
        log.info("To by nie zadzialalo: " + subjectid + " " + tutorid);
        Subject subject = subjectRepository.findById(Integer.valueOf(subjectid)).get();
        Tutor tutor = tutorRepository.findById(Integer.valueOf(tutorid)).get();

        subject.getTutors().add(tutor);
        tutor.getSubjects().add(subject);

        subjectRepository.save(subject);
        tutorRepository.save(tutor);

        return this.allTutorToSubject();
    }

    @RequestMapping(value = "/admin/tutortosubject/delete", method = RequestMethod.POST)
    public ModelAndView deleteTutorToSubject(HttpServletRequest request){
        String parameter = request.getParameter("parameter");
        log.info("To by nie zadzialalo: " + parameter );
        String[] ids = parameter.split("!");
        String tutorid = ids[0];
        String subjectid = ids[1];

        Subject subject = subjectRepository.findById(Integer.valueOf(subjectid)).get();
        Tutor tutor = tutorRepository.findById(Integer.valueOf(tutorid)).get();

        subject.getTutors().remove(tutor);
        tutor.getSubjects().remove(subject);

        subjectRepository.save(subject);
        tutorRepository.save(tutor);

        return this.allTutorToSubject();
    }
}
