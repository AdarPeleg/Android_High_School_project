package com.example.oneapptorulethemall.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.oneapptorulethemall.dao.NoteDao;
import com.example.oneapptorulethemall.entities.Note;



@Database( entities = Note.class, version = 1, exportSchema = false)
// inherits from Room database
public abstract class NotesDatabase extends RoomDatabase {


    private static NotesDatabase notesDatabase;

    public static synchronized NotesDatabase getDataBase(Context context){
        if(notesDatabase == null){
            notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase.class,
                    "notes_db"
            ).build();
        }
        return notesDatabase;
    }

    public abstract NoteDao noteDao();

}
