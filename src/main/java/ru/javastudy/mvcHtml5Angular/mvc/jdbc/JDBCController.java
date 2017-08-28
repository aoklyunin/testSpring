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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
@Controller
public class JDBCController {

    @Autowired
    JDBCExample jdbcExample;

    @RequestMapping(value = "/dirs_and_files")
    public String dirsAndFiles(Model model) {
        System.out.println("queryAllDirs jdbcSelect is called");
        List<DirAndFile> dbLogs = jdbcExample.queryAllDirs();
        System.out.println(dbLogs);
        model.addAttribute("dbLogs", dbLogs);
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
