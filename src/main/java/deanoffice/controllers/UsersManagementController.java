package deanoffice.controllers;

import deanoffice.noentities.User;
import deanoffice.noentities.UsersTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.logging.Logger;

@Controller
public class UsersManagementController {
    private static final Logger log = Logger.getLogger(UsersManagementController.class.getName());

    @Autowired
    private UsersTableData data;

    @RequestMapping(value = "/admin/allusers", method = RequestMethod.GET)
    public ModelAndView users() {
        ArrayList<User> users = data.getAllUsers();
        ModelAndView model = new ModelAndView("/admin/users.html");
        model.addObject("users", users);
        return model;
    }

    @RequestMapping(value = "/admin/allusers/deleted", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        log.info("Username for delete: " + username);
        data.deleteUserByUsername(username);
        return this.users();
    }

    @RequestMapping(value = "/admin/allusers/edituser", method = RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (username != null) {
            log.info("User for edit: " + username);
            User user = data.findUserByUsername(username);
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
        data.updateUser(username, password, role, enabled);
        return this.users();
    }

    @RequestMapping(value = "/admin/addnewuser", method = RequestMethod.GET)
    public ModelAndView addUserForm() {
        ModelAndView model = new ModelAndView("/admin/adding/adduser.html");
        return model;
    }

    @RequestMapping(value = "/admin/allusers/confirm", method = RequestMethod.POST)
    public ModelAndView confirmUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        log.info("Parameters: " + username + ", " +
                password + ", " + role + ", " + enabled);
        data.insertUser(username, password, role, enabled);
        return this.users();
    }
}
