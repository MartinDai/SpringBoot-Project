package com.doodl6.springboot.common.util;

/**
 * 分页工具类
 */
public final class PageUtil {

    private PageUtil() {
    }

    public static int checkPageNo(Integer pageNo) {
        return (pageNo == null || pageNo < 1) ? 1 : pageNo;
    }

    public static int checkPageSize(Integer pageSize) {
        return checkPageSize(pageSize, 10);
    }

    public static int checkPageSize(Integer pageSize, int defaultPageSize) {
        return checkPageSize(pageSize, defaultPageSize, Integer.MAX_VALUE);
    }

    public static int checkPageSize(Integer pageSize, int defaultPageSize, int maxPageSize) {
        return (pageSize == null || pageSize < 1) ? defaultPageSize : (pageSize > maxPageSize ? maxPageSize : pageSize);
    }

    /**
     * 计算页数
     */
    public static int calcPageCount(int count, int pageSize) {
        int pageCount = count / pageSize;
        if (count % pageSize != 0 || pageCount == 0) {
            pageCount++;
        }

        return pageCount;
    }

    /**
     * 计算每页数量
     */
    public static int calcPageSize(long count, int pageCount) {
        int pageSize = (int) (count / pageCount);
        if (count % pageSize != 0) {
            pageSize++;
        }

        return pageSize;
    }

}
