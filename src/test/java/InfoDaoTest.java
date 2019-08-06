import com.dealt.dao.HeadDao;
import com.dealt.dao.InfoDao;
import com.dealt.dao.ModelDao;
import com.dealt.entity.InfoEntity;
import com.dealt.entity.ItemEntity;
import com.dealt.service.ItemService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class InfoDaoTest {

    @Test
    public void InfoAddTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        InfoDao infoDao = (InfoDao) ac.getBean("infoDao");
        InfoEntity infoEntity = new InfoEntity();
        infoEntity.setHeadid(21);
        infoEntity.setInfolevel((long) 1);
        infoEntity.setModelid(21);
        infoEntity.setNotes("备注测试");
        infoEntity.setProgressbar((long)7);
        infoEntity.setScheduledtime(new Date());
        infoEntity.setStatus((long)1);
        infoEntity.setTodoitem("这是需要做的事2、");

        infoDao.addInfo(infoEntity);
    }

    @Test
    public void InfoUpdateTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        InfoDao infoDao = (InfoDao) ac.getBean("infoDao");
        InfoEntity infoEntity = new InfoEntity();
        infoEntity.setInfoid(21);
        infoEntity.setHeadid(21);
        infoEntity.setInfolevel((long) 1);
        infoEntity.setModelid(21);
        infoEntity.setNotes("被修改的备注、");
        infoEntity.setProgressbar((long)5);
        infoEntity.setScheduledtime(new Date());
        infoEntity.setStatus((long)1);
        infoEntity.setTodoitem("这是被修改后做的事、");

        infoDao.updateInfo(infoEntity);
    }

    @Test
    public void InfoGetAllTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        InfoDao infoDao = (InfoDao) ac.getBean("infoDao");
/*        List<InfoEntity> infoEntities = infoDao.getAllInfo();
        for(InfoEntity infoEntity : infoEntities){
            System.out.println(infoEntity.getTodoitem());
        }*/
    }

    @Test
    public void InfoExistTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        InfoDao infoDao = (InfoDao) ac.getBean("infoDao");
        String str = "这是需要做的事2、                                                                                   ";
/*        String a = str;
        int num = 0;
        try {
            num = a.getBytes("utf-8").length;
            System.out.println(num);
            a = "中文";
            num = a.getBytes("utf-8").length;
            System.out.println(num);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        System.out.println("isExist === " +
                infoDao.isExist(1, "这是需要做的事2、"));
    }

    @Test
    public void InfoServiceAddTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        ItemService itemService  = (ItemService) ac.getBean("itemService");
        ModelDao modelDao = (ModelDao) ac.getBean("modelDao");
        HeadDao headDao = (HeadDao)ac.getBean("headDao");

        InfoEntity infoEntity = new InfoEntity();
        infoEntity.setInfoid(21);
        infoEntity.setHeadid(21);
        infoEntity.setInfolevel((long) 1);
        infoEntity.setModelid(21);
        infoEntity.setNotes("被修改的备注、");
        infoEntity.setProgressbar((long)5);
        infoEntity.setScheduledtime(new Date());
        infoEntity.setStatus((long)1);
        infoEntity.setTodoitem("这是被修改后做的事、");

        //查询模块名、
        String modelName = modelDao.getModelName(infoEntity.getModelid());

        //查询负责人、
        String headName = headDao.getHeadName(infoEntity.getHeadid());

        ItemEntity itemEntity = new ItemEntity(
                infoEntity.getInfoid(),
                modelName,
                infoEntity.getTodoitem(),
                infoEntity.getProgressbar(),
                infoEntity.getStatus(),
                infoEntity.getScheduledtime(),
                infoEntity.getInfolevel(),
                headName,
                infoEntity.getNotes()
        );
        itemService.addItem(itemEntity);
    }
}
