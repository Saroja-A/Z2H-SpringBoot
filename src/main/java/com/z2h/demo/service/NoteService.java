package com.z2h.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.z2h.demo.model.Note;

@Service
public class NoteService {
  private List<Note> notes = new ArrayList<>();
  private Long nextId = 1L;

  public List<Note> getAllNotes() {
    return notes;
  }

  public Note createNote(String title, String content) {
    Note note = new Note();
    note.setId(nextId++);
    note.setTitle(title);
    note.setContent(content);
    notes.add(note);
    return note;
  }

  public Note getNoteById(Long id) {
    return notes.stream()
        .filter(note -> note.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public Note updateNote(Long id, String title, String content) {
    for (Note note : notes) {
      if (note.getId().equals(id)) {
        note.setTitle(title);
        note.setContent(content);
        return note;
      }
    }
    return null;
  }

  public void deleteNote(Long id) {
    notes.removeIf(note -> note.getId().equals(id));
  }
}
