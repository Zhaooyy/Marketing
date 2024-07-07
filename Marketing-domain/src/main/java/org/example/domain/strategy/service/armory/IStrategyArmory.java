package org.example.domain.strategy.service.armory;

/**
 * 策略装配库（兵工厂—），负责初始化策略计算
 */

public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置（
     * @param strategyId
     */
    boolean assembleLotteryStrategy(Long strategyId);



}
