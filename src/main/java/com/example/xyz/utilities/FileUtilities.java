package com.example.xyz.utilities;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class FileUtilities {

    public static void saveArticleImage(String fileName,
            MultipartFile multipartFile) {
        String path = "c:/images/offres/";

        try {
            Files.deleteIfExists(Paths.get(path + fileName));
            multipartFile.transferTo(
                    new File(path + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticleImage(String fileName) {
        String path = "c:/images/offres/";
        try {
            Files.deleteIfExists(Paths.get(path + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
