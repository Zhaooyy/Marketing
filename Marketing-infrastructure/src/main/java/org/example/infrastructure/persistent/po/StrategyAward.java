package org.example.infrastructure.persistent.po;

/**
 * 抽奖策略明细配置 - 概率 规则
 * */

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class StrategyAward {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID */
    private Integer awardId;
    /**  */
    private String awardTitle;
    /** 抽奖奖品标题 */
    private String awardSubtitle;
    /** 抽奖奖品副标题 */
    private Integer awardCount;
    /** 奖品库存总量 */
    private Integer awardCountSurplus;
    /** 奖品库存剩余 */
    private BigDecimal awardRate;
    /** 奖品中奖概率 */
    private String ruleModels;
    /** 规则模型 */
    private Integer sort;
    /** 排序 */
    private Date createTime;
    /** 创建时间 */
    private Date updateTime;
}
