package ru.javastudy.mvcHtml5Angular.mvc.jdbc;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javastudy.mvcHtml5Angular.mvc.bean.HierarhiFile;

import java.util.Comparator;
import java.util.List;

/**
 * Created by aokly on 27.08.2017.
 */


@RestController
public class RController {
    @Autowired
    JDBCHelper jdbcHelper;

    class RetStruct {
        List<HierarhiFile> lst;
        String path;

        public RetStruct(List<HierarhiFile> lst, String path) {
            this.lst = lst;
            this.path = path;
        }
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.POST, headers = "Accept=application/json")
    public
    @ResponseBody
    String getFiles(@RequestParam(value = "id", defaultValue = "0") String id) {
        System.out.println(id);
        List<HierarhiFile> lst = jdbcHelper.getDirsById(Integer.parseInt(id));
        lst.sort((o1, o2) -> {
            boolean o1d = o1.getSIZE().equals("<DIR>");
            boolean o2d = o2.getSIZE().equals("<DIR>");

            if ((o1d && o2d) || (!o1d && !o2d))
                return o1.getNAME().toLowerCase().compareTo(o2.getNAME().toLowerCase());
            else if (o1d) return -1;
            else return 1;
        });

        return new Gson().toJson(new RetStruct(lst, jdbcHelper.getPathById(Integer.parseInt(id))));
    }
}
