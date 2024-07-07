package org.example.domain.strategy.service.armory;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.strategy.model.entity.StrategyAwardEntity;
import org.example.domain.strategy.model.entity.StrategyEntity;
import org.example.domain.strategy.model.entity.StrategyRuleEntity;
import org.example.domain.strategy.repository.IStrategyRepository;
import org.example.types.enums.ResponseCode;
import org.example.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository repository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);

        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        //权重策略配置 - 适用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if(null == ruleWeight) return true;

        StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);

        if(null == strategyRuleEntity){
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }

        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();

        for(String key : keys){
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            //拿到的值是否在存储范围内，如果在则存储否则删除
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));

            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);

        }

        return true;

    }


    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities){

        //获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);

        //获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //用1/0.001获取概率范围，通过范围值获取所有奖品所需位置
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0 , RoundingMode.CEILING);

        ArrayList<Integer> strategyAwardSearchRateTables =  new ArrayList<>(rateRange.intValue());
        //生成策略，即在概率表中填写奖品号，以供后续随机奖品
        for(StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            strategyAwardEntity.getAwardId();
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();

            //计算每个概率需要几个位置,即每个概率值存放到查找表的数量，并循环填充
            for(int i=0; i<rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++){
                strategyAwardSearchRateTables.add(awardId); //eg.第一个奖品为80%概率，则在其中放80个此奖品
            }
        }
        //乱序集合，并存入哈希表
        Collections.shuffle(strategyAwardSearchRateTables);
        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTable = new HashMap<>();
        for(int i=0; i<strategyAwardSearchRateTables.size(); i++){
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }

        //存储进redis
        repository.storeStrategyAwardSearchRateTable(key, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);
    }


    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange = repository.getRateRange(key);
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));

    }

}
