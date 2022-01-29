package deanoffice.controllers;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import deanoffice.security.User;
import deanoffice.security.UserService;

@Controller
public class UsersThemselvesManagementController {
    private static final Logger log = Logger.getLogger(UsersThemselvesManagementController.class.getName());

    @Autowired
    private UserService service;

    @RequestMapping(value = "/admin/allusers", method = RequestMethod.GET)
    public ModelAndView users() {
        List<User> users = service.getAllUsers();
        ModelAndView model = new ModelAndView("/admin/users.html");
        model.addObject("users", users);
        return model;
    }

    @RequestMapping(value = "/admin/allusers/deleted", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        log.info("Username for delete: " + username);
        service.deleteUserByUsername(username);
        return this.users();
    }

    @RequestMapping(value = "/admin/allusers/edituser", method = RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (username != null) {
            log.info("User for edit: " + username);
            // TODO: remove cast
            User user = (User) service.loadUserByUsername(username);
            ModelAndView model = new ModelAndView("/admin/adding/edituser.html");
            model.addObject("user", user);
            return model;
        }
        return this.users();
    }

    @RequestMapping(value = "/admin/allusers/confirmedit", method = RequestMethod.POST)
    public ModelAndView confirmEditUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        log.info("Parameters: " + username + ", " +
                password + ", " + role + ", " + enabled);
        service.updateUser(username, password, role, enabled);
        return this.users();
    }

    @RequestMapping(value = "/admin/addnewuser", method = RequestMethod.GET)
    public ModelAndView addUserForm() {
        return new ModelAndView("/admin/adding/adduser.html");
    }

    @RequestMapping(value = "/admin/allusers/confirm", method = RequestMethod.POST)
    public ModelAndView confirmUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        log.info("Parameters: " + username + ", " +
                password + ", " + role + ", " + enabled);
        service.insertUser(username, password, role, enabled);
        return this.users();
    }
}
