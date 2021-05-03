package com.airlines.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JdbcUtil {
    public static String getSqlQueryString(String filePath) {
        Path path = Paths.get(filePath);
        return convertSqlFileToString(path);
    }

    private static String convertSqlFileToString(Path path) {
        try {
            return String.join("\n", Files.readAllLines(path));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Couldn't read lines from file with path: %s", path), e);

        }
    }
}