import cn.itsource.hrm.CourseServerApplication;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.service.ICourseTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={CourseServerApplication.class,First.class})
public class First {

    /*@Autowired
    private ICourseTypeService service;
    @Test
    public void test() throws Exception{
        for (CourseType courseType : service.findChildById(1037L)) {
            System.out.println(courseType);
        }
    }*/
}
