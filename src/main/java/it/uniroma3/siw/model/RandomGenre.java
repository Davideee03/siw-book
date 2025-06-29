package it.uniroma3.siw.model;

import java.util.Random;

public class RandomGenre {
    public static Genre getRandomGenre() {
        Genre[] genres = Genre.values();
        Random random = new Random();
        return genres[random.nextInt(genres.length)];
    }
}
