//Blake Crowther
package linked_yarn;

import java.util.NoSuchElementException;

public class LinkedYarn implements LinkedYarnInterface {

 // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    private Node head;
    private int size, uniqueSize, modCount;
    
    
    // -----------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------
    LinkedYarn () {
        size = 0;
        uniqueSize = 0;
        modCount = 0;
    }
    
    LinkedYarn (LinkedYarn other) {
        this.modCount = other.modCount;
        for(Node current = other.head; current != null; current = current.next) {
            this.addNode(current.text, current.count);
        }
        
    }
    
    
    // -----------------------------------------------------------
    // Methods
    // -----------------------------------------------------------
    public boolean isEmpty () {
        return this.head == null;
    }
    
    public int getSize () {
        return this.size;
    }
    
    public int getUniqueSize () {
        return this.uniqueSize;
    }
    
    public void insert (String toAdd) {
        Node possibleOccurrence = this.findNode(toAdd);
        if( possibleOccurrence == null) {
            this.addNode(toAdd, 1);
        } else {
            this.findNode(toAdd).count++;
            this.size++;
        }
        this.modCount++;
    }
    
    public int remove(String toRemove) {
        if(this.contains(toRemove)) {
            if(this.findNode(toRemove).count == 1) {
                this.removeAll(toRemove);
                return 0;
            } else {
                this.findNode(toRemove).count--;
                this.size--;
                this.modCount++;
                return this.findNode(toRemove).count;
            }
        } else {
            return 0;
        }
    }

    public void removeAll(String toNuke) {
        if(this.contains(toNuke)) {
            Node nextNode = this.findNode(toNuke).next;
            Node prevNode = this.findNode(toNuke).prev;
            this.size -= this.findNode(toNuke).count;
            if(this.uniqueSize == 1) {
                this.head = null;
            } else if(this.findNode(toNuke) == this.head) {
                this.head = nextNode;
                nextNode.prev = null;
            } else if (nextNode == null) {
                prevNode.next = null;
            } else {
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }
            this.uniqueSize--;
            this.modCount++;
        } else {
            return;
        }
    }
    
    public int count (String toCount) {
        if(this.findNode(toCount) == null) {
            return 0;
        } else {
            return this.findNode(toCount).count;
        }
    }
    
    public boolean contains (String toCheck) {
        if(this.findNode(toCheck) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getMostCommon () {
        if(this.isEmpty() == true) {
            return null;
        }
        int mostCommon = 0;
        String result = "";
        for(Node current = this.head; current != null; current = current.next) {
            if(current.count > mostCommon) {
                mostCommon = current.count;
                result = current.text;
            }
        }
        return result;
        
    }
    
    public void swap (LinkedYarn other) {
        Node otherHead = other.head;
        int otherSize = other.size;
        int otherUniqueSize = other.uniqueSize;
        other.size = this.size;
        other.uniqueSize = this.uniqueSize;
        this.size = otherSize;
        this.uniqueSize = otherUniqueSize;
        other.head = this.head;
        this.head = otherHead;
        this.modCount++;
        other.modCount++;
    }
    
    public LinkedYarn.Iterator getIterator () {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        } else {
            return new Iterator(this);
        }
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedYarn knit (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn knitYarn = new LinkedYarn(y1);
        for(Node current = y2.head; current != null; current = current.next ) {
            if(knitYarn.contains(current.text)) {
                knitYarn.findNode(current.text).count += current.count;
                knitYarn.size += current.count;
            } else {
                knitYarn.addNode(current.text, current.count);
            }
        }
        return knitYarn;
        
    }
    
    public static LinkedYarn tear (LinkedYarn y1, LinkedYarn y2) {
        LinkedYarn tornYarn = new LinkedYarn(y1);
        for(Node current = y2.head; current != null; current = current.next ) {
            if(tornYarn.contains(current.text)) {
                if (tornYarn.findNode(current.text).count > current.count) {
                   tornYarn.findNode(current.text).count -= current.count; 
                   tornYarn.size -= current.count;
                } else {
                    tornYarn.removeAll(current.text);
                }
            }
        }
        return tornYarn;
    }
    
    public static boolean sameYarn (LinkedYarn y1, LinkedYarn y2) {
        return LinkedYarn.tear(y1, y2).isEmpty() && LinkedYarn.tear(y2, y1).isEmpty();
    }
    
    // -----------------------------------------------------------
    // Private Methods
    // -----------------------------------------------------------
    
    //prepends a new node to LinkedYarn and changes variables accordingly
    private void addNode (String t, int c) {
        Node addedNode = new Node(t, c);
        Node originalNode = this.head;
        this.size += c;
        this.uniqueSize++;
        if(!this.isEmpty()) {
            addedNode.next = originalNode;
            originalNode.prev = addedNode;
        }
        this.head = addedNode;
    }
    
    
    //returns the Node which contains String toFind
    private Node findNode (String toFind) {
        for( Node currentNode = this.head; currentNode!=null; currentNode = currentNode.next) {
            if(toFind.equals(currentNode.text)) {
                return currentNode;
            }
        }
        return null;
    }
    
    
    // -----------------------------------------------------------
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedYarnIteratorInterface {
        private LinkedYarn owner;
        private Node current;
        private int itModCount;
        private int stringOccurrence;
        
        Iterator (LinkedYarn y) {
            owner = y;
            current = owner.head;
            itModCount = owner.modCount;
            stringOccurrence = 1;
        }
        
        public boolean hasNext () {
            return (isValid() && (stringOccurrence < current.count || current.next != null));
        }
        
        public boolean hasPrev () {
            return (isValid() && (stringOccurrence > 1 || current.prev != null));
        }
        
        public boolean isValid () {
            return itModCount == owner.modCount;
        }
        
        public String getString () {
            if(isEmpty()) {
                return null;
            } else {
                return current.text;
            }
        }

        public void next () {
            if (!hasNext()) throw new NoSuchElementException();
            if (!isValid()) throw new IllegalStateException();
            if(current.count > stringOccurrence) {
                stringOccurrence++;
            } else {
                current = current.next;
                stringOccurrence = 1;
            }
        }
        
        public void prev () {
            if (!hasPrev()) throw new NoSuchElementException();
            if (!isValid()) throw new IllegalStateException();
            if(stringOccurrence > 1) {
                stringOccurrence--;
            } else {
                current = current.prev;
                stringOccurrence = current.count;
            }
        }
        
        public void replaceAll (String toReplaceWith) {
            if (!isValid()) throw new IllegalStateException();
            current.text = toReplaceWith;
            itModCount++;
            owner.modCount++;
        }
        
    }
    
    class Node {
        Node next, prev;
        String text;
        int count;
        
        Node (String t, int c) {
            text = t;
            count = c;
        }
    }
    

}
