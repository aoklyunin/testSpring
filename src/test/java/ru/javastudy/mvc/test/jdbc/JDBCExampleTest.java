package ru.javastudy.mvc.test.jdbc;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javastudy.mvcHtml5Angular.mvc.jdbc.JDBCHelper;

/**
 * Created for JavaStudy.ru on 24.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mvc-config.xml", "classpath:application-context.xml"})
public class JDBCExampleTest {

//IMPORTANT - DISABLE THIS <bean class="org.springframework.web.servlet.view.XmlViewResolver"> in mvc-config.xml

    @Autowired
    private JDBCHelper jdbcHelper;


	/*//TEST METHOD for Test Table inside HSQLDB
    @Test
	public void queryAllTestTableRecords() {
    	List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM TEST");
    	for (Map<String, Object> row : rows) {
				System.out.println("TESTTABLE tectcolumn: " + row.get("TESTCOLUMN"));
		}
	}*/


}
