package com.example.newsportal.repository;

import java.util.List;

public class CategoryWordsStorage {
    //TODO  add more words up to Maxsize
    public static final int categoryWordsSize = 20;
    public static final List<String> MUSIC = List.of("music", "rock", "blues", "author", "guitar");
    public static final List<String> THEATRE = List.of("theatre", "arena", "artist", "comedy", "drama");
    public static final List<String> CINEMA = List.of("cinema", "actor", "cartoon", "character", "director");
}
