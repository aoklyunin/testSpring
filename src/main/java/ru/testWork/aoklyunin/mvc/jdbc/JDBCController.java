package ru.testWork.aoklyunin.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.testWork.aoklyunin.mvc.bean.DirAndFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
@Controller
public class JDBCController {

    @Autowired
    JDBCHelper jdbcHelper;

    @RequestMapping(value = "/dirs_and_files")
    public String dirsAndFiles(Model model) {
        System.out.println("queryAllDirs jdbcSelect is called");
        List<DirAndFile> dbLogs = jdbcHelper.queryAllDirs();
        System.out.println(dbLogs);
        model.addAttribute("dbLogs", dbLogs);
        return "dirs_and_files";
    }

    @RequestMapping(value = "/dirs_and_files", method = RequestMethod.POST)
    public String dirsAndFilesPOST(HttpServletRequest request) {
        String path = request.getParameter("path");
        System.out.println(jdbcHelper.addDir(path));
        System.out.println(path);
        return "redirect:/dirs_and_files";
    }
}