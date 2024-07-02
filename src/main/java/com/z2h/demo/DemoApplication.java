package com.z2h.demo;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.z2h.demo.model.Note;
import com.z2h.demo.service.NoteService;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private NoteService noteService;

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean exit = false;
		while (!exit) {
			System.out.println("Notes Application (Command-line version)");
			System.out.println("1. Show all notes");
			System.out.println("2. Create a note");
			System.out.println("3. Edit a note");
			System.out.println("4. Delete a note");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline character

			switch (choice) {
				case 1:
					showAllNotes();
					break;
				case 2:
					createNote();
					break;
				case 3:
					editNote();
					break;
				case 4:
					deleteNote();
					break;
				case 5:
					exit = true;
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
			}
		}
		scanner.close();
	}

	private void showAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		if (notes.isEmpty()) {
			System.out.println("No notes found.");
		} else {
			System.out.println("All Notes:");
			for (Note note : notes) {
				System.out.println(note.getId() + ": " + note.getTitle() + " - " + note.getContent());
			}
		}
	}

	private void createNote() {
		System.out.print("Enter note title: ");
		String title = scanner.nextLine();
		System.out.print("Enter note content: ");
		String content = scanner.nextLine();
		Note createdNote = noteService.createNote(title, content);
		System.out.println("Note created: " + createdNote.getId());
	}

	private void editNote() {
		System.out.print("Enter note ID to edit: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Consume newline character
		Note existingNote = noteService.getNoteById(id);
		if (existingNote == null) {
			System.out.println("Note not found.");
			return;
		}
		System.out.print("Enter new title (current: " + existingNote.getTitle() + "): ");
		String newTitle = scanner.nextLine();
		System.out.print("Enter new content (current: " + existingNote.getContent() + "): ");
		String newContent = scanner.nextLine();
		Note updatedNote = noteService.updateNote(id, newTitle, newContent);
		if (updatedNote != null) {
			System.out.println("Note updated: " + updatedNote.getId());
		} else {
			System.out.println("Failed to update note.");
		}
	}

	private void deleteNote() {
		System.out.print("Enter note ID to delete: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // Consume newline character
		noteService.deleteNote(id);
		System.out.println("Note deleted: " + id);
	}
}
