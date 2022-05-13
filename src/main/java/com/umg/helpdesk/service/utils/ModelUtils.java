package com.umg.helpdesk.service.utils;

public class ModelUtils {

    public static int getPageLimit(Integer limit) {
        if (limit == null || limit < 0)
            return 20;
        return limit;
    }

    public static int getPageNumber(Integer offset, Integer limit) {
        if (offset == null)
            return 0;
        int rows = getPageLimit(limit);
        if (offset < rows)
            return 0;
        int pageNumber = ceilDiv(offset, rows);
        return Math.max(pageNumber, 0);
    }

    private static int ceilDiv(int x, int y) {
        return -Math.floorDiv(-x, y);
    }
	
}
