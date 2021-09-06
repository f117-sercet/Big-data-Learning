package com.dsc.utils;

import org.apache.commons.lang3.StringUtils;
import org.graalvm.compiler.replacements.StandardGraphBuilderPlugins;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 产生词频统计模拟器
 * @Author:estic
 * @Date: 2021/9/6 11:19
 */
public class WordCountDataUtils {

    public static final List<String> WORD_LIST = Arrays.asList("Spark", "Hadoop", "HBase", "Storm", "Flink", "Hive");

    /**
     * 模拟产生词频数据
     * @return 词频数据
     */
    private static String generateData() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            Collections.shuffle(WORD_LIST);
            Random random = new Random();
            int endIndex = random.nextInt(WORD_LIST.size()) % (WORD_LIST.size()) + 1;
            String line = StringUtils.join(WORD_LIST.toArray(), "\t", 0, endIndex);
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

}
