package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> findAll(){
        return noteMapper.findAll();
    }

    public Note findById(int nodeid){
        return noteMapper.findById(nodeid);
    }

    public int save(Note note){
        return noteMapper.save(note);
    }

    public void delete(int nodeid){
        noteMapper.delete(nodeid);
    }

    public List<Note> findAllByUserid(int userId) {
        return noteMapper.findAllByUserid(userId);
    }

    public int updateNote(Note note) {
        return noteMapper.merge(note);
    }
}
