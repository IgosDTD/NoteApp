package com.example.notesapp.ui;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;
import com.example.notesapp.data.Constants;
import com.example.notesapp.data.InMemoryRepoImpl;
import com.example.notesapp.data.Note;
import com.example.notesapp.data.Repo;


public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Note note;
    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update);

        Intent intent = getIntent();
        if (intent != null) {
            Note note = (Note) intent.getSerializableExtra((Constants.NOTE));
            id = note.getId();
            title.setText(note.getTitle());
            description.setText((note.getDescription()));
        }
        saveNote.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String updateTitle = title.getText().toString();
        String updateDescription = description.getText().toString();
        Intent intent = new Intent(this,NotesListActivity.class);
        Note note = new Note(id, updateTitle, updateDescription );
        note.setTitle(updateTitle);
        note.setDescription(updateDescription);
        if(note.getId() == null) {
            repository.create(note);
        }else{
            repository.update(note);
        }
        intent.putExtra(Constants.NOTE, note);
        startActivity(intent);
        finish();

    }
}



