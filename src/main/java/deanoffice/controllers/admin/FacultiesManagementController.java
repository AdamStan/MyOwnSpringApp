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
        ModelAndView modelAndView = new ModelAndView("/admin/adding/addfaculty.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/editfaculty", method = RequestMethod.GET)
    public ModelAndView editFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        ModelAndView modelAndView = new ModelAndView("/admin/adding/editfaculty.html");
        modelAndView.addObject("id", id);
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
    public String confirmAddFaculty(HttpServletRequest request){
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        Faculty newFaculty = new Faculty(name, desc);
        facultyRepository.save(newFaculty);
        return "redirect:/admin/allfaculties";
    }

    @RequestMapping(value = "/admin/faculty/confirmedit", method = RequestMethod.POST)
    public String confirmEditFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("desc");
        Optional<Faculty> opf = facultyRepository.findById(Integer.valueOf(id));
        Faculty facultyUpdate = opf.get();
        facultyUpdate.setName(name);
        facultyUpdate.setDescription(description);
        facultyRepository.save(facultyUpdate);
        return "redirect:/admin/allfaculties";
    }
}
