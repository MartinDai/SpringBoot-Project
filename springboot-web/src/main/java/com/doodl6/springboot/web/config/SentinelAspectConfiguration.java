package com.doodl6.springboot.web.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SentinelAspectConfiguration {

    static {
        initFlowRules();
    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * 初始化限流规则，实际使用的时候可以使用动态配置，比如基于nacos
     */
    private static void initFlowRules() {
        List<FlowRule> rules = Lists.newLinkedList();
        FlowRule rule = new FlowRule();
        rule.setResource("getDubboInfo");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置QPS为1
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}