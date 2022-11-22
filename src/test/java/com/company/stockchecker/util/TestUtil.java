package com.company.stockchecker.util;

import java.util.concurrent.ThreadLocalRandom;

public class TestUtil {

    public static long randomLong() {
        return ThreadLocalRandom.current().nextLong(1000L);
    }


}
