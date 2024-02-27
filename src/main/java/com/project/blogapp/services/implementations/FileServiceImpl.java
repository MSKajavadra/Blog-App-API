package com.project.blogapp.services.implementations;

import com.project.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name= file.getOriginalFilename(); //File Name

        String randomId = UUID.randomUUID().toString();
        String fileName=randomId.concat(name.substring(name.lastIndexOf(".")));

        String filePath=path + File.separator + fileName; //Full Path

        File f=new File(path);  //Create Folder if Not Created
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));  //Copy File

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
