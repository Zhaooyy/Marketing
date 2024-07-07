package org.example.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 策略奖品实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardEntity {

    private Long strategyId;
    /** 抽奖奖品ID */
    private Integer awardId;

    private Integer awardCount;
    /** 奖品库存总量 */
    private Integer awardCountSurplus;
    /** 奖品库存剩余 */
    private BigDecimal awardRate;
}
