import com.dealt.dao.InfoDao;
import com.dealt.entity.InfoEntity;
import com.dealt.entity.ItemEntity;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemEntityTest {

    @Test
    public void strToTimeTest(){
        ItemEntity itemEntity = new ItemEntity();
        Date time = ItemEntity.strToDate("2019-07-26 10:16:05");
        System.out.println("time = " + time);
    }

    @Test
    public void timeToStrTest(){
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        InfoDao infoDao = (InfoDao) ac.getBean("infoDao");

        InfoEntity infoEntity = infoDao.getInfo(1);
        String strTime = ItemEntity.dateToStr(infoEntity.getScheduledtime());
        System.out.println("strTime = " + strTime);
    }

    @Test
    public void testtime(){
        //(1)
        java.util.Date time1 = new java.util.Date();
        //(2)
        java.sql.Date time2 = new java.sql.Date(System.currentTimeMillis());
        //(3)
        java.sql.Time time3 = new java.sql.Time(System.currentTimeMillis());
        //(4)
        java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
        System.out.println(time);
        DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH：mm：ss 今年第ww周  第DD天");
        System.out.println("北京时间：" + sdf.format(time1) + "\n");
        System.out.println("北京时间：" + sdf.format(time2)+ "\n");
        System.out.println("北京时间：" + sdf.format(time3)+ "\n");
        System.out.println("北京时间：" + sdf.format(time)+ "\n");
    }
}
