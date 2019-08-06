import com.dealt.dao.ModelDao;
import com.dealt.entity.ModelEntity;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ModelDaoTest {

    @Test
    public void modelAddTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ModelDao modelDao = (ModelDao) ac.getBean("modelDao");
        ModelEntity modelEntity = new ModelEntity();
        modelEntity.setModelname("第八世界、");
        //modelDao.addModel(modelEntity);
    }
}
