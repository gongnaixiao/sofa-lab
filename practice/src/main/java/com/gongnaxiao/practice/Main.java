package com.gongnaxiao.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Paths.get("").toAbsolutePath());
    }
}