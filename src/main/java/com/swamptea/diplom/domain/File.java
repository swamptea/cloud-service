package com.swamptea.diplom.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "my_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyFile {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name="id")
    private Long id;

    @Lob
 //   @Column(name = "file", columnDefinition="BLOB")
    private byte[] file;

 //   @Column(name = "fileName")
    private String filename;

//    @Column(name = "type")
    private String type;

 //   @Column(name = "size")
    private Integer size;


}
