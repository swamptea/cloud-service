package com.swamptea.diplom.repo;

import com.swamptea.diplom.domain.myFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface myFileRepo extends CrudRepository<myFile, Long> {
  //  List<myFile> getmyFileBy (String tag);
}