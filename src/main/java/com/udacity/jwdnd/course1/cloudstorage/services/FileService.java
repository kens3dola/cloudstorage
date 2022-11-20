package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public boolean existingFile(String filename){
        return fileMapper.findByFilename(filename) != null;
    }

    public boolean save(int userId, MultipartFile multipartFile){
        File file = new File();
        try {
            file.setFilename(multipartFile.getOriginalFilename());
            file.setFiledata(multipartFile.getInputStream().readAllBytes());
            file.setUserid(userId);
            file.setFilesize(String.valueOf(multipartFile.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileMapper.save(file) == 1;
    }

    public List<File> findAllByUserid(int userId){
        return fileMapper.findAllByUserid(userId);
    }

    public File findById(int id) {
        return fileMapper.findById(id);
    }

    public void deleteById(int id){
        fileMapper.deleteById(id);
    }
}
