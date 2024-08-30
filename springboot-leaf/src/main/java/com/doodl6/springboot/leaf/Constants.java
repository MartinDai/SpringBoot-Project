package com.doodl6.springboot.leaf;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String LEAF_SEGMENT_ENABLE = "leaf.segment.enable";
    public static final String LEAF_JDBC_URL = "leaf.jdbc.url";
    public static final String LEAF_JDBC_USERNAME = "leaf.jdbc.username";
    public static final String LEAF_JDBC_PASSWORD = "leaf.jdbc.password";

}