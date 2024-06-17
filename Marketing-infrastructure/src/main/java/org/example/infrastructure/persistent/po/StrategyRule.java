package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * 奖品规则
 * */

@Data
public class StrategyRule {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID */
    private Integer awardId;
    /** 抽象规则类型 */
    private Integer ruleType;
    /** 抽奖规则模型 */
    private String ruleModels;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 抽奖规则描述 */
    private String ruleDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
