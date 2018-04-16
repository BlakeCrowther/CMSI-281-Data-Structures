//Blake Crowther
//04-15-2018
package autocompleter;


import java.util.ArrayList;

public class Autocompleter implements AutocompleterInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    Autocompleter () {
        root = null;
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        return this.root == null;
    }
    
    public void addTerm (String toAdd) {
        char[] toInsert = normalizeTerm(toAdd).toCharArray();
        if (root == null) {
            root = new TTNode(toInsert[0], lastChar(toInsert, 0));
        }    
        addTerm(toInsert, root, 0);
    } 
    
    
    public boolean hasTerm (String query) {
        return hasTerm(normalizeTerm(query).toCharArray(), root, 0);
    }
    
    public String getSuggestedTerm (String query) {
        char[] givenChars = normalizeTerm(query).toCharArray();
        TTNode toCompare = findQuery(givenChars);
        if(toCompare == null) {
            return null;
        }
        String suggestedTerm = new String(getSuggestedTerm(givenChars, toCompare));
        return suggestedTerm;
    }
    
    public ArrayList<String> getSortedTerms () {
        if (root == null) {
            return new ArrayList<String>();
        }
        return sortTerms(addLastLetter(new char[0], root.letter), root, new ArrayList<String>());    
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeTerm (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    
    //Adds given term appropriately into ternary search tree 
    private void addTerm (char[] toAdd, TTNode toCompare, int ptr) {
        int comparison = compareChars(toAdd[ptr], toCompare.letter);
        if(comparison == 0) {
            if(lastChar(toAdd, ptr)) {
                toCompare.wordEnd = true;
                return;
            }
            if(toCompare.mid == null) {
                toCompare.mid = new TTNode(toAdd[ptr + 1], lastChar(toAdd, ptr));
            }
            addTerm(toAdd, toCompare.mid, ptr + 1);
        } else if ( comparison < 0) {
            if(toCompare.left == null) {
                toCompare.left = new TTNode(toAdd[ptr], lastChar(toAdd, ptr));
                if(lastChar(toAdd, ptr)) {
                    toCompare.left.wordEnd = true;
                    return;
                }
            }
            addTerm(toAdd, toCompare.left, ptr);
        } else {
            if(toCompare.right == null) {
                toCompare.right = new TTNode(toAdd[ptr], lastChar(toAdd, ptr));
                if(lastChar(toAdd, ptr)) {
                    toCompare.right.wordEnd = true;
                    return;
                }
            }
            addTerm(toAdd, toCompare.right, ptr);
        }
    }
    
    //Checks for a given term in the search tree and returns true if it exists. 
    private boolean hasTerm (char[] toCheck, TTNode toCompare, int ptr) {
        if(toCompare == null) {
            return false;
        } 
        int comparison = compareChars(toCheck[ptr], toCompare.letter);
        if(comparison == 0) {
            if(lastChar(toCheck, ptr) == true) {
                return toCompare.wordEnd;
            } else {
                return hasTerm(toCheck, toCompare.mid, ptr + 1);
            }
        } else if(comparison < 0) {
            return hasTerm(toCheck, toCompare.left, ptr);
        } else {
            return hasTerm(toCheck, toCompare.right, ptr);
        }
    }
    
    //Returns true if the pointer is at the last character in the Character Array
    private boolean lastChar (char[] toCheck, int ptr) {
        return (toCheck.length - 1) == ptr;
    }
    
    //Returns a possible search term in the tree that completes the given query
    private char[] getSuggestedTerm(char[] query, TTNode toCompare) {
        if(toCompare.wordEnd) {
            return query;
        }
        if(toCompare.mid != null) {
            query = getSuggestedTerm(addLastLetter(query, toCompare.mid.letter), toCompare.mid);
        } else if(toCompare.left != null) {
            query = getSuggestedTerm(addLastLetter(query, toCompare.left.letter), toCompare.left);
        } else if(toCompare.right != null) {
            query = getSuggestedTerm(addLastLetter(query, toCompare.right.letter), toCompare.right);
        }
        return query;
    }
    
    //Returns the TTNode of the last character of the query
    //Returns null if the query does not exist in the tree
    private TTNode findQuery(char[] query) {
        if(root == null) {
            return null;
        }
        int ptr = 0;
        TTNode toCompare = root;
        while(ptr < query.length) {
            int comparison = compareChars(query[ptr], toCompare.letter);
            if(comparison == 0) {
                ptr++;
                if(ptr < query.length) {

                    if(toCompare.mid == null) {
                        return null;
                    }
                    toCompare = toCompare.mid;
                }
            } else if(comparison < 0) {
                if(toCompare.left == null) {
                    return null;
                }
                toCompare = toCompare.left;

            } else {
                if(toCompare.right == null) {
                    return null;
                }
                toCompare = toCompare.right;

            }
        }
        return toCompare;
    }
    
    //Returns a copy of the given character array with the given letter added to the end
    private char[] addLastLetter(char[] toAddTo, char letter) {
        char[] added = new char[toAddTo.length + 1];
        for(int i = 0; i < toAddTo.length; i++) {
            added[i] = toAddTo[i];
        }
        added[toAddTo.length] = letter;
        return added;
    }
    
    //Returns an ArrayList of Strings composed of all the search terms in alphabetical order
    private ArrayList<String> sortTerms (char[] toSort, TTNode toCompare, ArrayList<String> sortedList) {
        if (toCompare.left != null) {
            sortTerms(replaceLastLetter(toSort, toCompare.left.letter), toCompare.left, sortedList);
        }
        if (toCompare.wordEnd) {
            sortedList.add(new String(toSort));
        }
        if (toCompare.mid != null) {
            sortTerms(addLastLetter(toSort, toCompare.mid.letter), toCompare.mid, sortedList);
        }
        if (toCompare.right != null) {
            sortTerms(replaceLastLetter(toSort, toCompare.right.letter), toCompare.right, sortedList);
        }
        return sortedList;
    }
    
    //Returns a copy of the given character array with the last letter replaced with the given letter
    private char[] replaceLastLetter (char[] toReplace, char letter) {
        char[] replaced = toReplace.clone();
        replaced[replaced.length -1] = letter;
        return replaced;
    }
    
    // -----------------------------------------------------------
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of autocompleter search terms
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
            left    = null;
            mid     = null;
            right   = null;
        }
        
    }
    
}
