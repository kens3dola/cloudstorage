package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from notes where userid = #{userid}")
    List<Note> findAll();

    @Select("select * from notes where noteid = #{noteid}")
    Note findById(int nodeid);

    @Insert("insert into notes(notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    int save(Note note);

    @Delete("delete from notes where noteid = #{noteid}")
    void delete(int nodeid);

    @Select("select * from notes where userid = #{userid}")
    List<Note> findAllByUserid(int userId);

    @Update("update notes set notetitle = #{notetitle}, notedescription = #{notedescription} where noteid = #{noteid}")
    int merge(Note note);
}
