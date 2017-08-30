package ru.testWork.aoklyunin.mvc.test.jdbc;

import org.junit.Assert;
import org.junit.Test;
import ru.testWork.aoklyunin.mvc.bean.HierarhiFile;
import ru.testWork.aoklyunin.mvc.jdbc.JDBCHelper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
public class JDBCExampleTest extends Assert {

    //Проверка подсчета суммарного размера файлов
    //b.    Проверка вывода размера в понятном человеку формате
    // c.     Проверка сортировки (на примере данных из макета)

    @Test
    public void getSumSize() throws IOException {
        assertEquals(8413913088L, JDBCHelper.getSumSize(Paths.get("C:\\")));
    }

    @Test
    public void getBfFileSize() throws IOException {
        assertEquals("117,7Mb", JDBCHelper.getBfFileSize(123423474));
    }

    @Test
    public void getBfFileSize2() throws IOException {
        assertEquals("0b", JDBCHelper.getBfFileSize(0));
    }

    @Test
    public void getBfFileSize3() throws IOException {
        assertEquals("8,6Gb", JDBCHelper.getBfFileSize(9214128321L));
    }

    @Test
    public void sortFolderList() {
        ArrayList<HierarhiFile> lst = new ArrayList<>(Arrays.asList(
                new HierarhiFile("901,48Kb", "F4_00127.pdf", 1),
                new HierarhiFile("12,57Kb", "F1.txt", 1),
                new HierarhiFile("<DIR>", "innerTep", 1),
                new HierarhiFile("4,28Kb", "f.txt", 1),
                new HierarhiFile("26,01Mb", "f0008.doc", 1),
                new HierarhiFile("1,52Mb", "f4_99.JPG", 1),
                new HierarhiFile("<DIR>", "X-FILES", 1),
                new HierarhiFile("3,57Kb", "function.cpp", 1)
        ));
        JDBCHelper.sortHFList(lst);
        assertEquals(new ArrayList<>(Arrays.asList(
                new HierarhiFile("<DIR>", "innerTep", 1),
                new HierarhiFile("<DIR>", "X-FILES", 1),
                new HierarhiFile("4,28Kb", "f.txt", 1),
                new HierarhiFile("12,57Kb", "F1.txt", 1),
                new HierarhiFile("1,52Mb", "f4_99.JPG", 1),
                new HierarhiFile("901,48Kb", "F4_00127.pdf", 1),
                new HierarhiFile("26,01Mb", "f0008.doc", 1),
                new HierarhiFile("3,57Kb", "function.cpp", 1)
        )),lst);
    }
    @Test
    public void test1(){
        System.out.println("f.".compareTo("F1"));
    }

}
