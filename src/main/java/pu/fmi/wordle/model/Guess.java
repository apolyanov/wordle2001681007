package pu.fmi.wordle.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Guess {
    private static final char PLACE_MATCH = 'P';
    private static final char LETTER_MATCH = 'L';
    private static final char NO_MATCH = 'N';

    String word;
    LocalDateTime madeOn;
    String matches;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public LocalDateTime getMadeOn() {
        return madeOn;
    }

    public void setMadeAt(LocalDateTime madeOn) {
        this.madeOn = madeOn;
    }

    public String getMatches() {
        return matches;
    }

    public void checkGuess(String guessWord, String gameWord) {
        word = guessWord;
        madeOn = LocalDateTime.now();
        char[] matchedWord = new char[guessWord.length()];
        char[] guessWordArr = guessWord.toCharArray();
        char[] gameWordArr = gameWord.toCharArray();

        int count = 0;

        for (int i = 0; i < guessWord.length(); i++) {
            count++;
            if (guessWordArr[i] == gameWordArr[i]) {
                matchedWord[i] = PLACE_MATCH;
            } else if(hasLetterMatch(gameWord, guessWordArr, i)) {
                matchedWord[i] = LETTER_MATCH;
            } else {
                matchedWord[i] = NO_MATCH;
            }
        }

        matches = new String(matchedWord);
    }

    private boolean hasLetterMatch(String gameWord, char[] guessWordArr, int index) {
        return gameWord.chars().mapToObj(c -> (char) c).toList().contains(guessWordArr[index]);
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }
}
