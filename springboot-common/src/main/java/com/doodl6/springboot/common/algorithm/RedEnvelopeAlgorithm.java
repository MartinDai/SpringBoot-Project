package com.doodl6.springboot.common.algorithm;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 网上找的红包算法,略有调整
 * https://blog.csdn.net/paincupid/article/details/82054647
 */
public class RedEnvelopeAlgorithm {

    private static Logger logger = LoggerFactory.getLogger(RedEnvelopeAlgorithm.class);

    /**
     * 返回一次抽奖在指定中奖概率下是否中奖
     *
     * @param rate 中奖概率
     */
    private static boolean canReward(double rate) {
        return Math.random() <= rate;
    }

    /**
     * 返回min~max区间内随机数，含min和max
     */
    private static int getRandomVal(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min;
    }

    /**
     * 带概率偏向的随机算法，概率偏向subMin~subMax区间
     * 返回boundMin~boundMax区间内随机数（含boundMin和boundMax），同时可以指定子区间subMin~subMax的优先概率
     * 例：传入参数(10, 50, 20, 30, 0.8)，则随机结果有80%概率从20~30中随机返回，有20%概率从10~50中随机返回
     *
     * @param boundMin 边界
     */
    private static int getRandomValWithSpecifySubRate(int boundMin, int boundMax, int subMin, int subMax, double subRate) {
        logger.info("概率偏向获取随机数 | {} | {} | {} | {} | {}", boundMin, boundMax, subMin, subMax, subRate);
        if (canReward(subRate)) {
            return getRandomVal(subMin, subMax);
        }
        return getRandomVal(boundMin, boundMax);
    }

    /**
     * 随机分配第n个红包
     *
     * @param totalBonus 总红包量
     * @param totalNum   总份数
     * @param sentBonus  已发送红包量
     * @param sentNum    已发送份数
     * @param rdMin      随机下限
     * @param rdMax      随机上限
     * @param bigRate    大包比例（小于1）
     */
    private static int randomBonusWithSpecifyBound(int totalBonus, int totalNum, int sentBonus,
                                                   int sentNum, int rdMin, int rdMax, double bigRate) {
        logger.info("获取下一个红包金额 | {} | {} | {} | {} | {} | {} | {}", totalBonus, totalNum, sentBonus, sentNum, rdMin, rdMax, bigRate);
        Integer avg = totalBonus / totalNum;  // 平均值
        Integer leftLen = avg - rdMin;
        Integer rightLen = rdMax - avg;
        int boundMin, boundMax;

        // 大范围设置小概率
        if (leftLen.equals(rightLen)) {
            boundMin = Math.max((totalBonus - sentBonus - (totalNum - sentNum - 1) * rdMax), rdMin);
            boundMax = Math.min((totalBonus - sentBonus - (totalNum - sentNum - 1) * rdMin), rdMax);
        } else if (rightLen.compareTo(leftLen) > 0) {
            // 上限偏离
            int standardRdMax = avg + leftLen;  // 右侧对称上限点
            int _rdMax = canReward(bigRate) ? rdMax : standardRdMax;
            boundMin = Math.max((totalBonus - sentBonus - (totalNum - sentNum - 1) * standardRdMax), rdMin);
            boundMax = Math.min((totalBonus - sentBonus - (totalNum - sentNum - 1) * rdMin), _rdMax);
        } else {
            // 下限偏离
            int standardRdMin = avg - rightLen;  // 左侧对称下限点
            int _rdMin = canReward(bigRate) ? rdMin : standardRdMin;
            boundMin = Math.max((totalBonus - sentBonus - (totalNum - sentNum - 1) * rdMax), _rdMin);
            boundMax = Math.min((totalBonus - sentBonus - (totalNum - sentNum - 1) * standardRdMin), rdMax);
        }

        // 已发平均值偏移修正-动态比例
        if (boundMin == boundMax) {
            return getRandomVal(boundMin, boundMax);
        }
        double currAvg = sentNum == 0 ? (double) avg : (sentBonus / (double) sentNum);  // 当前已发平均值
        double middle = (boundMin + boundMax) / 2.0;
        int subMin = boundMin, subMax = boundMax;
        // 期望值
        double exp = avg - (currAvg - avg) * sentNum / (double) (totalNum - sentNum);
        if (exp < subMin) {
            exp = subMin;
        } else if (exp > subMax) {
            exp = subMax;
        }

        if (middle > exp) {
            subMax = (int) Math.round((boundMin + exp) / 2.0);
        } else {
            subMin = (int) Math.round((exp + boundMax) / 2.0);
        }
        Integer expBound = (boundMin + boundMax) / 2;
        Integer expSub = (subMin + subMax) / 2;
        double subRate = (exp - expBound) / (double) (expSub - expBound);

        return getRandomValWithSpecifySubRate(boundMin, boundMax, subMin, subMax, subRate);
    }

    /**
     * 生成红包一次分配结果
     *
     * @param bigRate 指定大范围区间的概率,小于1
     */
    public static List<Integer> createBonusList(int totalBonus, int totalNum, int rdMin, int rdMax, double bigRate) {
        int sentBonus = 0;
        int sentNum = 0;
        List<Integer> bonusList = Lists.newArrayList();
        while (sentNum < totalNum) {
            int bonus = randomBonusWithSpecifyBound(totalBonus, totalNum, sentBonus, sentNum, rdMin, rdMax, bigRate);
            bonusList.add(bonus);
            sentNum++;
            sentBonus += bonus;
        }
        return bonusList;
    }

    public static void main(String[] args) {
        List<Integer> createBonusList = createBonusList(100_00, 13, 100, 200_00, 0.8);
        int index = 0;
        int total = 0;
        for (int i : createBonusList) {
            total += i;
            System.out.println("第" + (++index) + "个红包大小：" + i);
        }

        System.out.println("总金额:" + total);
    }

}