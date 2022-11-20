package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("insert into files(filename, filesize, userid, filedata) values(#{filename}, #{filesize}, #{userid}, #{filedata})")
    int save(File file);

    @Select("select * from files")
    List<File> findAll();

    @Select("select * from files where fileid = #{fileid}")
    File findById(int fileId);

    @Select("select * from files where userid = #{userid}")
    List<File> findAllByUserid(int userId);

    @Delete("delete from files where fileid = #{fileid}")
    void deleteById(int fileid);

    @Select("select * from files where filename = #{filename}")
    File findByFilename(String filename);
}
