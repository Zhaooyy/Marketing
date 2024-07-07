package org.example.test.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.service.armory.IStrategyArmory;
import org.example.domain.strategy.service.armory.IStrategyDispatch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StrategyArmoryTest {
    @Resource
    private IStrategyArmory strategyArmory;

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Before
    public void test_strategyArmory(){
        boolean success = strategyArmory.assembleLotteryStrategy(10001L);
        log.info("测试结果：{}", success);
    }

    @Test
    public void test_getRandomAwardId(){
        log.info("测试结果：{} -奖品ID值", strategyDispatch.getRandomAwardId(10001L));

    }

    @Test
    public void test_getRandomAwardId_ruleWeight(){
        log.info("测试结果：{} -奖品ID值 - 4000 策略配置", strategyDispatch.getRandomAwardId(10001L, "4000:102,103,104,105"));
        log.info("测试结果：{} -奖品ID值 - 5000 策略配置", strategyDispatch.getRandomAwardId(10001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} -奖品ID值 - 6000 策略配置", strategyDispatch.getRandomAwardId(10001L, "6000:102,103,104,105,106,107,108,109"));

    }

}
