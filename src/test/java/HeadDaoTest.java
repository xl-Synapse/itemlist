import com.dealt.dao.HeadDao;
import com.dealt.dao.impl.HeadDaoImpl;
import com.dealt.entity.HeadEntity;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HeadDaoTest {
    @Test
    public void headUpdateTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HeadDao headDao = (HeadDao) ac.getBean("headDao");
        //headDao.updateHead(1, "被修改的测试3");
        TestCase.assertEquals(headDao.getHeadName(1), "被修改的测试3");
    }

    @Test
    public void headAddTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HeadDao headDao = (HeadDao) ac.getBean("headDao");
        HeadEntity headEntity = new HeadEntity();
        headEntity.setHeadid(5);
        headEntity.setHeadname("被添加的head");
        //headDao.addHead(headEntity);
    }

    @Test
    public void headDelTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HeadDao headDao = (HeadDao) ac.getBean("headDao");
        headDao.delHead(3);
    }

    @Test
    public void headGetTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        HeadDao headDao = (HeadDao) ac.getBean("headDao");
        TestCase.assertEquals("被修改的测试3                 ", headDao.getHeadName(1));
    }
}
