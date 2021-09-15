package deanoffice.controllers.admin;

import deanoffice.entities.Faculty;
import deanoffice.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class FacultiesManagementController {
    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping(value = "/admin/allfaculties", method = RequestMethod.GET)
    public ModelAndView faculties(){
        Iterable<Faculty> faculties = facultyRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/faculties.html");
        model.addObject("faculties", faculties);
        return model;
    }

    @RequestMapping(value = "/admin/addnewfaculty", method = RequestMethod.GET)
    public ModelAndView addFaculty(){
        ModelAndView modelAndView = new ModelAndView("/admin/adding/facultyform.html");
        Faculty faculty = new Faculty();
        modelAndView.addObject(faculty);
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/editfaculty", method = RequestMethod.GET)
    public ModelAndView editFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        ModelAndView modelAndView = new ModelAndView("/admin/adding/facultyform.html");
        modelAndView.addObject("faculty", facultyRepository.findById(Integer.valueOf(id)));
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/delete", method = RequestMethod.GET)
    public String deleteFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Faculty> faculty = facultyRepository.findById(Integer.valueOf(id));
        facultyRepository.delete(faculty.get());
        return "redirect:/admin/allfaculties";
    }

    @RequestMapping(value = "/admin/faculty/confirm", method = RequestMethod.POST)
    public String confirmFaculty(Faculty faculty){
        facultyRepository.save(faculty);
        return "redirect:/admin/allfaculties";
    }
}
