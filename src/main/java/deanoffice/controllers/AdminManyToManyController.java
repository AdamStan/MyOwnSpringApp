package deanoffice.controllers;

import deanoffice.entities.Subject;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class AdminManyToManyController {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/admin/studenttosubject/", method = RequestMethod.GET)
    public ModelAndView allStudentToSubject(){
        return new ModelAndView();
    }

    @RequestMapping(value = "/admin/tutortosubject/", method = RequestMethod.GET)
    public ModelAndView allTutorToSubject(){
        return new ModelAndView();
    }

    @RequestMapping(value = "/admin/studenttosubject/add", method = RequestMethod.GET)
    public ModelAndView addStudentToSubject(){
        return new ModelAndView("/admin/adding/addstudenttosubject.html");
    }

    @RequestMapping(value = "/admin/studenttosubject/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddStudentToSubject(HttpServletRequest request){

        return this.allStudentToSubject();
    }

    @RequestMapping(value = "/admin/studenttosubject/delete", method = RequestMethod.POST)
    public ModelAndView deleteStudentToSubject(HttpServletRequest request){

        return this.allStudentToSubject();
    }

    @RequestMapping(value = "/admin/tutortosubject/add", method = RequestMethod.GET)
    public ModelAndView addTutorToSubject(){
        return new ModelAndView("/admin/adding/addtutortosubject.html");
    }

    @RequestMapping(value = "/admin/tutortosubject/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddTutorToSubject(HttpServletRequest request){

        return this.allTutorToSubject();
    }

    @RequestMapping(value = "/admin/tutortosubject/delet", method = RequestMethod.POST)
    public ModelAndView deleteTutorToSubject(HttpServletRequest request){

        return this.allTutorToSubject();
    }
}
