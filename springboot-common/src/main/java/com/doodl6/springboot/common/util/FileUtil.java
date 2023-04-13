package com.doodl6.springboot.common.util;

import com.alibaba.fastjson2.JSON;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 文件工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

    /**
     * 删除单个文件
     *
     * @param filePath 文件绝对路径
     */
    public static void deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new IllegalArgumentException("文件路径为空");
        }

        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件夹
     *
     * @param directoryPath 文件夹路径
     */
    public static void deleteDirectory(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            throw new IllegalArgumentException("文件夹路径为空");
        }

        if (!directoryPath.endsWith(File.separator)) {
            directoryPath += File.separator;
        }

        File directoryFile = new File(directoryPath);
        if (!directoryFile.exists() || !directoryFile.isDirectory()) {
            return;
        }

        File[] files = directoryFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDirectory(file.getAbsolutePath());
                }
            }
        }

        directoryFile.delete();
    }

    /**
     * 把内容写入目标文件
     */
    public static void writeToFile(List<String> contentList, String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (String content : contentList) {
                fileWriter.write(content);
                fileWriter.write("\n");
                fileWriter.flush();
            }
        }
    }

    public static void writeLine(FileWriter fileWriter, Object... objArray) throws IOException {
        for (Object object : objArray) {
            fileWriter.write(JSON.toJSONString(object));
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }

}
