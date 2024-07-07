package org.example.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.types.common.Constants;

/**
 * 策略实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class StrategyEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    //抽奖规则模型
    private String ruleModels;

    public String[] ruleModels(){
        if(StringUtils.isAllBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight(){
        String[] ruleModels = this.ruleModels();
        for(String ruleModel : ruleModels){
            if("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }
}
