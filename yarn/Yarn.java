//Blake Crowther
package yarn;

public class Yarn implements YarnInterface {
	private Strand[] items;
	private int size;
	private int uniqueSize;
	private final int MAX_SIZE = 100;

	// -----------------------------------------------------------
	// Constructors
	// -----------------------------------------------------------
	Yarn() {
		items = new Strand[MAX_SIZE];
		this.size = 0;
		this.uniqueSize = 0;
	}

	Yarn(Yarn other) {
		this.items = new Strand[MAX_SIZE];
		for (int i = 0; i < other.getUniqueSize(); i++) {
			this.items[i] = new Strand(other.items[i].text, other.items[i].count);
		}
		this.size = other.getSize();
		this.uniqueSize = other.getUniqueSize();
	}

	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------
	public boolean isEmpty() {
		return this.size == 0;
	}

	public int getSize() {
		return this.size;
	}

	public int getUniqueSize() {
		return this.uniqueSize;
	}

	public boolean insert(String toAdd) {
		if(this.uniqueSize == 100) {
			return false;
		}
		for(int i = 0; i < this.uniqueSize; i++) {
			if(this.items[i].text.equals(toAdd)) {
				this.items[i].count++;
				this.size++;
				return true;
			} 
		}
		this.items[uniqueSize] = new Strand(toAdd, 1);
		this.uniqueSize++;
		this.size++;
		return true;
	}

	public int remove(String toRemove) {
		for(int i = 0; i < this.uniqueSize; i++) {
			if(this.items[i].text.equals(toRemove)) {
				if(this.items[i].count == 1) {
					this.removeAll(toRemove);
					return 0;
				}
				this.items[i].count--;
				this.size--;
				return this.items[i].count;
			}
		}
		return 0;
	}

	public void removeAll(String toNuke) {
		for(int i = 0; i < this.uniqueSize; i++) {
			if(this.items[i].text.equals(toNuke)) {
				this.size -= this.items[i].count;
				this.uniqueSize--;
				for(int j = i; j < this.uniqueSize; j++)  {
					this.items[j] = this.items[j+1];
				}
			}
		}
	}

	public int count(String toCount) {
		for (int i = 0; i < this.uniqueSize; i++) {
			if (toCount.equals(this.items[i].text)) {
				return this.items[i].count;
			}
		} 
		return 0;
	}

	public boolean contains(String toCheck) {
		for (int i = 0; i < this.uniqueSize; i++) {
			if (toCheck.equals(this.items[i].text)) {
				return true;
			}
		}
		return false;
	}

	public String getNth(int n) {
		int iteration = 0; 
		for(int i = 0; i < this.uniqueSize; i++) {
			for(int j = 0; j < this.items[i].count; j++) {
				if(iteration == n) {
					return this.items[i].text;
				}
				iteration++;
			}
		}
		return "";
	}

	public String getMostCommon() {
		if(this.isEmpty() == true) {
			return null;
		}
		int mostCommon = 0;
		for(int i = 0; i < this.uniqueSize; i++) {
			if(this.items[mostCommon].count <= this.items[i].count) {
				mostCommon = i;
			}
		}
		return this.items[mostCommon].text;
	}

	public void swap(Yarn other) {
		Strand[] reference = other.items;
		int referenceSize = other.getSize();
		int referenceUniqueSize = other.getUniqueSize();
		other.items = this.items;
		other.size = this.size;
		other.uniqueSize = this.uniqueSize;
		this.items = reference;
		this.size = referenceSize;
		this.uniqueSize = referenceUniqueSize;
	}

	public String toString() {
		String result = "{ ";
		if(this.isEmpty() == true) {
			result += " }";
			return result;
		}
		for(int i = 0; i < this.uniqueSize; i++) {
			if( i == this.uniqueSize - 1) {
				result += "\"" + this.items[i].text + "\": " + this.items[i].count + " ";
				break;
			}
			result += "\"" + this.items[i].text + "\": " + this.items[i].count + ", ";
		}
		result += "}";
		return result;
	}

	// -----------------------------------------------------------
	// Static methods
	// -----------------------------------------------------------

	public static Yarn knit(Yarn y1, Yarn y2) {
		Yarn knitYarn = new Yarn();
		knitYarn.size = y1.getSize();
		knitYarn.uniqueSize = y1.getUniqueSize();
        for(int i = 0; i < y1.getUniqueSize(); i++) {
        	knitYarn.items[i] = new Strand(y1.items[i].text, y1.items[i].count);
        }
        for(int i = 0; i < y2.getSize(); i++) {
        	knitYarn.insert(y2.getNth(i));
        	
        }
        return knitYarn;
	}

	public static Yarn tear(Yarn y1, Yarn y2) {
		Yarn tornYarn = new Yarn();
		tornYarn.size = y1.getSize();
		tornYarn.uniqueSize = y1.getUniqueSize();
        for(int i = 0; i < y1.getUniqueSize(); i++) {
        	tornYarn.items[i] = new Strand(y1.items[i].text, y1.items[i].count);
        }
        for(int i = 0; i < y2.getSize(); i++) {
        	if(tornYarn.contains(y2.getNth(i))) {
        		tornYarn.remove(y2.getNth(i));
        	}
        }
        return tornYarn;
	}

	public static boolean sameYarn(Yarn y1, Yarn y2) {
        if (y1.getSize() != y2.getSize() || y1.getUniqueSize() != y2.getUniqueSize()) {
        	return false;
        }
        for (int i = 0; i < y2.getUniqueSize(); i++) {
    		if (!y1.contains(y2.items[i].text) || y1.count(y2.items[i].text) != y2.items[i].count) {
    			return false;
    		}
    	}
        return true;
	}

	// -----------------------------------------------------------
	// Private helper methods
	// -----------------------------------------------------------
	// Add your own here!

}

class Strand {
	String text;
	int count;

	Strand(String s, int c) {
		text = s;
		count = c;
	}

}
