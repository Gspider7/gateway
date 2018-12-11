package com.uqiansoft.gateway.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xutao
 * @date 2018-11-27 16:24
 */
public class Statistic {

    public static AtomicInteger req_count = new AtomicInteger(0);

    public static AtomicInteger fallback_count = new AtomicInteger(0);
}
