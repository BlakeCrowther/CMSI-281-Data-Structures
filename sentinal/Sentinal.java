package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private PhraseHash posHash, negHash;

    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    Sentinal (String posFile, String negFile) throws FileNotFoundException {
        posHash = new PhraseHash();
        negHash = new PhraseHash();
        loadSentimentFile(posFile, true);
        loadSentimentFile(negFile, false);
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public void loadSentiment (String phrase, boolean positive) {
        if(positive) {
            posHash.put(phrase);
        } else {
            negHash.put(phrase);
        }
    }
    
    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(filename));
        while(fileReader.hasNextLine()) {
            String currentPhrase = fileReader.nextLine();
            loadSentiment(currentPhrase, positive);
        }
        fileReader.close();
    }
    
    public String sentinalyze (String filename) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new File(filename));
        int sentiment = 0;
        int largest;
        if (posHash.longestLength() > negHash.longestLength()) {
            largest = posHash.longestLength();
        } else {
            largest = negHash.longestLength();
        }
        while (fileReader.hasNextLine()) {

            String currentPhrase = fileReader.nextLine();
            String[] wordsInPhrase = currentPhrase.split(" ");
            int i = 0;
            while (i < wordsInPhrase.length) {
                String selection = wordsInPhrase[i];
                for(int selectionSize = 0; selectionSize < largest; selectionSize++) {
                    sentiment += isSentimental(selection);
                    if(i == wordsInPhrase.length - 1) {
                        break;
                    }
                    if(i + selectionSize < wordsInPhrase.length - 1) {
                        selection = selection + " " + wordsInPhrase[i + selectionSize + 1]; 
                    }
                }
                i++;
            }
        }
        fileReader.close();
        if(sentiment > 0) {
            return "positive";
        } else if (sentiment < 0) {
            return "negative";
        } else {
            return "neutral";
        }
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private int isSentimental (String selection) {
        int sentiment = 0;
        if(posHash.get(selection) != null) {
            sentiment++;
        } else if(negHash.get(selection) != null ) {
            sentiment--;
        }
        return sentiment;
    }
}

