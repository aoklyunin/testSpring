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
        List<HierarhiFile> hfLst = jdbcHelper.getHFListById(Integer.parseInt(id));
        JDBCHelper.sortHFList(hfLst);
        return new Gson().toJson(new RetStruct(hfLst, jdbcHelper.getDAFPathById(Integer.parseInt(id))));
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
