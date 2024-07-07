package org.example.test.infrustructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.infrastructure.persistent.dao.IAwardDao;
import org.example.infrastructure.persistent.po.Award;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AwardDaoTest {


    @Resource
    private IAwardDao awardDao;


    List<Award> awards;

    @Test
    public void test_queryAwardList(){
        System.out.println(awards.getClass());
        //List<Award> awardList = awardDao.queryAwardList();
        //log.info("测试结果{}", JSON.toJSONString(awardList));

    }
}
