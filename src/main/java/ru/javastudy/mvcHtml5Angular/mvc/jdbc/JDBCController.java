package ru.javastudy.mvcHtml5Angular.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javastudy.mvcHtml5Angular.mvc.bean.DBLog;
import ru.javastudy.mvcHtml5Angular.mvc.bean.DirAndFile;
import ru.javastudy.mvcHtml5Angular.mvc.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
@Controller
public class JDBCController {

    @Autowired
    JDBCExample jdbcExample;

    @RequestMapping(value = "/jdbcQueryAllUsers", method = RequestMethod.GET)
    public ModelAndView jdbcSelectAllUsers() {
        System.out.println("JDBCController jdbcSelectAllUsers() is called");
        List<User> users = jdbcExample.queryAllUsers();
        return new ModelAndView("/jdbc/jdbc", "resultObject", users);
    }

    @RequestMapping(value = "/jdbcInsert/logstring/{logstring}", method = RequestMethod.GET)
    public ModelAndView jdbcInsert(@PathVariable(value = "logstring") String logstring) {
        System.out.println("JDBCController jdbcInsert is called");
        DBLog dblog = new DBLog();
        dblog.setLOGSTRING(logstring);
        boolean result = jdbcExample.insertLog(dblog);
        return new ModelAndView("/jdbc/jdbc", "resultObject", result);
    }

    @RequestMapping(value = "/jdbcSelectLogs", method = RequestMethod.GET)
    public ModelAndView jdbcSelect() {
        System.out.println("JDBCController jdbcSelect is called");
        List<DBLog> dbLogs = jdbcExample.queryAllLogs();
        return new ModelAndView("/jdbc/jdbc", "resultObject", dbLogs);

    }

    @RequestMapping(value = "/jdbcDelete/user/{iduser}", method = RequestMethod.GET)
    public ModelAndView jdbcDelete(@PathVariable(value = "iduser") int iduser) {
        System.out.println("JDBCController jdbcDelete is called");
        boolean result = jdbcExample.deleteUSER(iduser);
        return new ModelAndView("/jdbc/jdbc", "resultObject", result);

    }

    @RequestMapping(value = "/jdbcUpdate/user/username/{username}/enabled/{enabled}", method = RequestMethod.GET)
    public ModelAndView jdbcUpdate(@PathVariable(value = "username") String username, @PathVariable(value = "enabled") boolean enabled) {
        System.out.println("JDBCController jdbcUpdate is called");
        User user = new User();
        user.setUsername(username);
        boolean result = jdbcExample.updateUserEnable(user, enabled);
        return new ModelAndView("/jdbc/jdbc", "resultObject", result);

    }

    @RequestMapping(value = "/dirs_and_files")
    public String dirsAndFiles(Model model) {
        System.out.println("queryAllDirs jdbcSelect is called");
        List<DirAndFile> dbLogs = jdbcExample.queryAllDirs();
        model.addAttribute("dbLogs",dbLogs);
        return "/jdbc/dirs_and_files";
    }

    @RequestMapping(value = "/dirs_and_files", method = RequestMethod.POST)
    public String dirsAndFilesPOST(HttpServletRequest request) {
        String path = request.getParameter("path");
        System.out.println(jdbcExample.addDir(path));
        System.out.println(path);
        return "redirect:/dirs_and_files";
    }


}
