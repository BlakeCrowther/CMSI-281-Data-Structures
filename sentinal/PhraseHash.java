//Blake Crowther
package sentinal;

import java.util.LinkedList;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private final static int BUCKET_COUNT = 1000;
    private int size, longest;
    private LinkedList<String>[] buckets;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    @SuppressWarnings("unchecked") // Don't worry about this >_>
    PhraseHash () {
        size = 0;
        longest = 0;
        buckets = new LinkedList[BUCKET_COUNT];
    }
    
    
    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------
    
    public int size () {
        return size;
    }
    
    public boolean isEmpty () {
        return size==0;
    }
    
    public void put (String s) {
        int position = hash(s);
        if(buckets[position] == null) {
            buckets[position] = new LinkedList<String>();
        } else if (buckets[position].contains(s)) {
            return;
        }
        buckets[position].add(s);
        size++;
        if(longest < getPhraseLength(s)) {
            longest = getPhraseLength(s);
        }
    }
    
    public String get (String s) {
        int position = hash(s); 
        if(buckets[position] == null || !buckets[position].contains(s)) {
            return null;
        } else {
            return s;
        }
    }
    
    public int longestLength () {
        return longest;
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private int hash (String s) {
        int asckey = 0;                             //get it? 
        for(int i = 0; i < s.length(); i++) {
            asckey += s.charAt(i); 
        }
        return asckey % BUCKET_COUNT;
    }
    
    private int getPhraseLength (String s) {
        return s.split(" ").length;
    }
    
}
