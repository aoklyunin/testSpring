package ru.testWork.aoklyunin.mvc.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.testWork.aoklyunin.mvc.bean.DirAndFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 *  @author Клюнин А.О.
 *  @version 1.0
 *  Контроллер страниц
 */
@Controller
public class JDBCController {

    @Autowired
    JDBCHelper jdbcHelper;

    @RequestMapping(value = "/dirs_and_files")
    public String dirsAndFilesPage(Model model) {
        List<DirAndFile> dafList = jdbcHelper.getDAFList();
        model.addAttribute("dbLogs", dafList);
        return "dirs_and_files";
    }

    @RequestMapping(value = "/dirs_and_files", method = RequestMethod.POST)
    public String dirsAndFilesPOST(HttpServletRequest request) {
        String path = request.getParameter("path");
        jdbcHelper.addDAFByPath(path);
        return "redirect:/dirs_and_files";
    }
}
