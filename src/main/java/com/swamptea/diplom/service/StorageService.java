package com.swamptea.diplom.service;

import com.swamptea.diplom.domain.File;
import com.swamptea.diplom.repo.MyFileRepo;
import com.swamptea.diplom.util.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class StorageService {
    private final MyFileRepo repository;

    public StorageService(MyFileRepo repository) {
        this.repository = repository;
    }

    public boolean uploadFile(MultipartFile file) throws IOException { //загрузить файл в бд
        if (repository.findByFilename(file.getOriginalFilename()) == null) {
            File fileData = repository.save(File.builder()
                    .filename(file.getOriginalFilename())
                    .type(file.getContentType())
                    .myFile(FileUtils.compressFile(file.getBytes()))
                    .size((int) file.getSize())
                    .build());
            return true;
        }
        return false;
    }

    public byte[] downloadFile(String fileName) throws DataFormatException { //скачать файл
        Optional<File> file = Optional.ofNullable(repository.findByFilename(fileName));
        if (file.isPresent()) {
            return FileUtils.decompressFile(file.get().getMyFile());
        } else return null;
    }

    public boolean deleteFile(String filename) { //удалить файл
        if (repository.findByFilename(filename) != null) {
            repository.delete(repository.findByFilename(filename));
            return true;
        } else return false;
    }

    public boolean editFileName(String filename, String newName) { //изменить название
        String[] filenameArr = newName.split("\"");
        try {
            String newFileName = filenameArr[3];
            File file = repository.findByFilename(filename);
            if (file != null) {
                repository.setFilename(file.getFilename(), newFileName);
                return true;
            } else return false;

        } catch (NullPointerException e) {
            return false;
        }
    }

    public Iterable all() {
        return repository.findAll();
    } //все файлы
}
