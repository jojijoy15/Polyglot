package com.problems.learning.algo.simple;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRUIPTest {

    LRUIP lruip = new LRUIP();

    @Test
    void findLRUIP() {
        String[] logs =  new String[]{
                "192.168.1.10 - - [10/Jan/2026:10:01:10 +0530] \"GET /home HTTP/1.1\" 200",
                "192.168.1.11 - - [10/Jan/2026:10:01:20 +0530] \"GET /login HTTP/1.1\" 200",
                "192.168.1.10 - - [10/Jan/2026:10:02:10 +0530] \"GET /profile HTTP/1.1\" 200",
                "192.168.1.12 - - [10/Jan/2026:10:03:10 +0530] \"GET /about HTTP/1.1\" 200"
        };
        String lruIP = lruip.findLRUIP(Arrays.asList(logs));
        assertThat(lruIP).isEqualTo("192.168.1.11");
    }
}