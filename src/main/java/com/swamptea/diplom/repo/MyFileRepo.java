package com.swamptea.diplom.repo;

import com.swamptea.diplom.domain.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface MyFileRepo extends CrudRepository<File, Long> {

    File findByFilename(String filename);

    //для замены имени файла в базе
    @Modifying
    @Query("update File f set f.filename =:newName where f.filename =:filename")
    void setFilename(String filename, String newName);

}