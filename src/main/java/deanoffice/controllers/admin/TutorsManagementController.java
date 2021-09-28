package deanoffice.controllers.admin;

import deanoffice.entities.Faculty;
import deanoffice.entities.Tutor;
import deanoffice.repositories.FacultyRepository;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class TutorsManagementController {
    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping(value = "/alltutors", method = RequestMethod.GET)
    public ModelAndView tutors(){
        Iterable<Tutor> tutors = tutorRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/tutors.html");
        model.addObject("tutors", tutors);
        return model;
    }

    @RequestMapping(value = "/tutors/add", method = RequestMethod.GET)
    public ModelAndView addtutor(){
        ModelAndView model = new ModelAndView("/admin/adding/addtutor.html");
        return model;
    }

    @RequestMapping(value = "/tutors/addconfirm", method = RequestMethod.POST)
    public String confirmAddTutor(HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");
        String whenStarted = request.getParameter("whenStarted");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Tutor tutor = new Tutor(name, surname, city, street, numberOfBuilding, numberOfFlat);
        tutor.setFaculty(op_faculty.get());
        tutor.setUsername(username);
        tutor.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        try{
            System.out.println(whenStarted);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(whenStarted);
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
            tutor.setWhenStarted(sqlStartDate);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        tutorRepository.save(tutor);

        return "redirect:/admin/alltutors";
    }

    @RequestMapping(value = "/tutors/edit", method = RequestMethod.GET)
    public ModelAndView edittutor(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/edittutor.html");
        String id = request.getParameter("id");
        Optional<Tutor> tutor = tutorRepository.findById(Integer.valueOf(id));
        model.addObject("tutor", tutor.get());
        return model;
    }

    @RequestMapping(value = "/tutors/editconfirm", method = RequestMethod.POST)
    public String confirmEditTutor(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");
        String whenStarted = request.getParameter("whenStarted");
        String whenFinnished = request.getParameter("whenFinnished");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Tutor tutor = tutorRepository.findById(Integer.valueOf(id)).get();
        tutor.setName(name);
        tutor.setSurname(surname);
        tutor.setCity(city);
        tutor.setStreet(street);
        tutor.setNumberOfBuilding(numberOfBuilding);
        tutor.setNumberOfFlat(numberOfFlat);
        tutor.setFaculty(op_faculty.get());
        tutor.setUsername(username);
        tutor.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        try{
            System.out.println(whenStarted);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(whenStarted);
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
            tutor.setWhenStarted(sqlStartDate);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date2 = sdf2.parse(whenFinnished);
            java.sql.Date sqlStartDate2 = new java.sql.Date(date2.getTime());
            tutor.setWhenFinnished(sqlStartDate2);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        tutorRepository.save(tutor);

        return "redirect:/admin/alltutors";
    }

    @RequestMapping(value = "/tutors/delete", method = RequestMethod.POST)
    public String deleteTutor(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Tutor> tutor = tutorRepository.findById(Integer.valueOf(id));
        tutorRepository.delete(tutor.get());
        return "redirect:/admin/alltutors";
    }
}
