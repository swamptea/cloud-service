package com.swamptea.diplom.service;

import com.swamptea.diplom.domain.File;
import com.swamptea.diplom.domain.Role;
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

    public boolean uploadFile(MultipartFile file, String user) throws IOException { //загрузить файл в бд
        if (repository.findByFilename(user + "_" + file.getOriginalFilename()).isEmpty()) {
            File fileData = repository.save(File.builder()
                    .filename(user + "_" + file.getOriginalFilename())
                    .type(file.getContentType())
                    .myFile(FileUtils.compressFile(file.getBytes()))
                    .size((int) file.getSize())
                    .owner(user)
                    .build());
            return true;
        }
        return false;
    }

    public byte[] downloadFile(String fileName, String login, Role role) throws DataFormatException { //скачать файл
        Optional<File> file = Optional.of(repository.findByFilename(fileName).get());
        if (repository.findByFilename(fileName).get().getOwner().equals(login) || role == Role.ADMIN) {
            return FileUtils.decompressFile(file.get().getMyFile());
        } else return null;
    }

    public boolean deleteFile(String filename, String login, Role role) { //удалить файл
        if (repository.findByFilename(filename).isPresent() &&
                (repository.findByFilename(filename).get().getOwner().equals(login) || role == Role.ADMIN)) {
            repository.delete(repository.findByFilename(filename).get());
            return true;
        } else return false;
    }

    public boolean editFileName(String filename, String login, String newName, Role role) { //изменить название
        String[] filenameArr = newName.split("\"");
        try {
            String newFileName = filenameArr[3];
            File file = repository.findByFilename(filename).get();
            if (file != null) {
                if (file.getOwner().equals(login) || role == Role.ADMIN) {
                    repository.setFilename(file.getFilename(), newFileName);
                    return true;
                } else return false;
            } else return false;

        } catch (NullPointerException e) {
            return false;
        }
    }

    public Iterable all() {
        return repository.findAll();
    } //все файлы

    public Iterable allForUser(String userId) {
        return repository.findAllByOwner(userId);
    } //все файлы для юзера

}
