//Blake Crowther
package yarn;

public class YarnTests {
	public static void main(String[] args) {
		 
		//test Yarn()
		Yarn testYarn = new Yarn();
		
		
		//test isEmpty()
		System.out.println("\nTest isEmpty()");
		Yarn isEmpty = new Yarn();
		System.out.println(isEmpty.isEmpty());
		Yarn notEmpty = new Yarn();
		notEmpty.insert("not empty");
		System.out.println(notEmpty.isEmpty());
		
		
		//test getSize()
		System.out.println("\nTest getSize()");
		Yarn getSize = new Yarn();
		System.out.println(getSize.getSize());
		getSize.insert("one");
		getSize.insert("two");
		System.out.println(getSize.getSize());
		
		
		//test insert()
		System.out.println("\nTest insert()");
		Yarn insert = new Yarn();
		insert.insert("word1");
		insert.insert("word1");
		insert.insert("word2");
		System.out.println(insert.toString());
		
		
		//test remove()
		System.out.println("\nTest remove()");
		Yarn remove = new Yarn();
		remove.insert("word1");
		remove.insert("word1");
		remove.insert("word2");
		System.out.println(remove.toString());
		remove.remove("word1");
		System.out.println(remove.toString());
		
		
		//test removeAll()
		System.out.println("\nTest removeAll()");
		Yarn removeAll = new Yarn();
		removeAll.insert("word1");
		removeAll.insert("word1");
		removeAll.insert("word2");
		System.out.println(removeAll.toString());
		removeAll.removeAll("word1");
		System.out.println(removeAll.toString());
		
		
		//test count()
		System.out.println("\nTest count()");
		Yarn count = new Yarn();
		count.insert("word1");
		count.insert("word1");
		count.insert("word2");
		System.out.println(count.count("word1"));
		System.out.println(count.count("word2"));

		
		//test contains()
		System.out.println("\nTest contains()");
		Yarn contains = new Yarn();
		contains.insert("word1");
		contains.insert("word1");
		contains.insert("word2");
		System.out.println(contains.contains("word1"));
		System.out.println(contains.contains("word2"));
		System.out.println(contains.contains("word3"));
		
		
		//test getNth()
		System.out.println("\nTest getNth()");
		Yarn getNth = new Yarn();
		getNth.insert("word1");
		getNth.insert("word1");
		getNth.insert("word2");
		System.out.println(getNth.getNth(0));
		System.out.println(getNth.getNth(1));
		System.out.println(getNth.getNth(2));
		System.out.println(getNth.getNth(3));
		
		
		//test getMostCommon()
		System.out.println("\nTest getMostCommon()");
		Yarn mostCommon = new Yarn();
		mostCommon.insert("word1");
		mostCommon.insert("word1");
		mostCommon.insert("word2");
		System.out.println(mostCommon.getMostCommon());
		
		
		//test swap()
		System.out.println("\nTest swap()");
		Yarn swap1 = new Yarn();
		swap1.insert("word1");
		swap1.insert("word1");
		swap1.insert("word2");
		System.out.println(swap1.toString());
		Yarn swap2 = new Yarn();
		swap2.insert("word3");
		swap2.insert("word3");
		swap2.insert("word4");
		System.out.println(swap2.toString());
		swap1.swap(swap2);
		System.out.println(swap1.toString());
		System.out.println(swap2.toString());

		
		//test toString()
		System.out.println("\nTest toString()");
		Yarn fullString = new Yarn();
		fullString.insert("word1");
		fullString.insert("word1");
		fullString.insert("word2");
		System.out.println(fullString.toString());
		Yarn emptyString = new Yarn();
		System.out.println(emptyString.toString());
		
		
		//test knit()
		System.out.println("\nTest knit()");
		Yarn knit1 = new Yarn();
		knit1.insert("word1");
		knit1.insert("word1");
		knit1.insert("word2");
		System.out.println(knit1.toString());
		Yarn knit2 = new Yarn();
		knit2.insert("word1");
		knit2.insert("word2");
		knit2.insert("word3");
		System.out.println(knit2.toString());
		Yarn knitYarn = Yarn.knit(knit1, knit2);
		System.out.println(knitYarn.toString());
		
		
		//test tear()
		System.out.println("\nTest tear()");
		Yarn tear1 = new Yarn();
		tear1.insert("word1");
		tear1.insert("word1");
		tear1.insert("word2");
		System.out.println(tear1.toString());
		Yarn tear2 = new Yarn();
		tear2.insert("word1");
		tear2.insert("word2");
		tear2.insert("word3");
		System.out.println(tear2.toString());
		Yarn tornYarn = Yarn.tear(tear1, tear2);
		System.out.println(tornYarn.toString());
		
		
		//test sameYarn()
		System.out.println("\nTest sameYarn()");
		Yarn sameYarn1 = new Yarn();
		sameYarn1.insert("word1");
		sameYarn1.insert("word1");
		sameYarn1.insert("word2");
		System.out.println(sameYarn1.toString());
		Yarn sameYarn2 = new Yarn();
		sameYarn2.insert("word1");
		sameYarn2.insert("word1");
		sameYarn2.insert("word2");
		System.out.println(sameYarn2.toString());
		System.out.println(Yarn.sameYarn(sameYarn1, sameYarn2));
		Yarn differentYarn1 = new Yarn();
		differentYarn1.insert("word1");
		differentYarn1.insert("word2");
		differentYarn1.insert("word3");
		System.out.println(differentYarn1.toString());
		System.out.println(Yarn.sameYarn(sameYarn1, differentYarn1));

		

	}
}
