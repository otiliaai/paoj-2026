package com.pao.laboratory05.playlist;

import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.reflect.Array.*;

public class Playlist {
    private String name;
    private Song[] songs;

    Playlist (String name) {
        this.name = name;
        songs = new Song[0];
    }

    String getName() {
        return this.name;
    }
    void addSong(Song song) {
        Song[] newSongs = new Song[songs.length + 1];
        System.arraycopy(songs, 0, newSongs,0,songs.length);
        newSongs[songs.length] = song;
        songs = newSongs;
    }

    void printSortedByTitle() {
        Song[] copy = songs.clone();
        Arrays.sort(copy);
        for (var s: copy) {
            System.out.println(s);
        }
    }

    void printSortedByDuration() {
        Song[] copy = songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        for (var s: copy) {
            System.out.println(s);
        }
    }

    int getTotalDuration() {
        int suma = 0;
        for ( var s: songs) {
            suma += s.durationSeconds();
        }
        return suma;
    }
}
