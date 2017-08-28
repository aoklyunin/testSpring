package ru.testWork.aoklyunin.mvc.jdbc;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.testWork.aoklyunin.mvc.bean.HierarhiFile;

import java.util.List;

/**
 *  @author Клюнин А.О.
 *  @version 1.0
 *  Контроллер REST-запросов
 */
@RestController
public class RController {
    @Autowired
    JDBCHelper jdbcHelper;


    @RequestMapping(value = "/getFiles", method = RequestMethod.POST, headers = "Accept=application/json")
    public
    @ResponseBody
    String getFiles(@RequestParam(value = "id", defaultValue = "0") String id) {
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

    /**
     *  Класс для формирования json-ответа
     */
    class RetStruct {
        List<HierarhiFile> lst;
        String path;

        RetStruct(List<HierarhiFile> lst, String path) {
            this.lst = lst;
            this.path = path;
        }
    }
}
