package com.baomidou.mybatisplus.samples.quickstart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbl.business.smslog.mapper.SmsLogMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private SmsLogMapper smsLogMapper;

    @Test
    public void testSelect() {
        QueryWrapper qw=new QueryWrapper ();
        qw.apply ("sql","sql");
        qw.last ("limit");
        qw.orderByAsc ();
        smsLogMapper.selectList (qw);

//        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
//        userList.forEach(System.out::println);
    }
}
