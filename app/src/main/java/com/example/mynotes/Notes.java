package com.example.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Notes implements Parcelable {
    private ArrayList<Note> notes = new ArrayList<>();

    public Notes() {

    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Note note: notes) {
            titles.add(note.getTitle());
        }

        return titles;
    }

    public Note getNote(int index)
    {
        return notes.get(index);
    }

    public int getSize() {
        return notes.size();
    }

    public String getTitle(int index) {
        return notes.get(index).getTitle();
    }

    public String getBody(int index) {
        return notes.get(index).getBody();
    }

    public void add(String title, String body) {
        notes.add(new Note(title, body));
    }

    public void delete(int index) {
        notes.remove(index);
    }

    protected Notes(Parcel in) {
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray((String[]) notes.toArray());
    }
}
