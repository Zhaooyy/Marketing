package org.example.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * 奖品
 * */

@Data
public class Award {
    private Long id;
    private Integer awardId;
    /** 奖品对接标识，每一个都对应一个发奖策略 */
    private String awardKey;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品内容描述 */
    private String awardDesc;
    private Date createTime;
    private Date updateTime;


}
