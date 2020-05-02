
import java.util.*;
import java.io.*;

public class LexiconTester{

	static class WordNode {
		String word;
		int frequency;
		List<String> neighbors = new ArrayList<String>();
		WordNode next;
	}
	
	public static void main (String[] args)throws Exception {
		//Read all the valid words in the file and store them in StringBuffer
		StringBuffer wordsBuffer = new StringBuffer("");
		String inputFileName1 = args[0]; //Get input file name
		String inputFileName2 = args[1];
		String outputFileName = args[2];//Get output file name
		//Read data from input file
		loadData(inputFileName1,wordsBuffer);
		loadData(inputFileName2,wordsBuffer);
		//Split words
		String[] words = wordsBuffer.toString().split("\\s+");
		//Construct a word as an ordered WordNode single-linked list
		WordNode wordsHead = new WordNode();
		createOrderedWordsList(words,wordsHead);
		reateNeighbors(wordsHead);
		writeData(outputFileName,wordsHead);
	}

	public static void loadData(String fileName1,StringBuffer wordsBuffer)throws IOException {
		//read file1
		Scanner infile1 = new Scanner(new File(fileName1));
		while(infile1.hasNext()) {
			String str = infile1.nextLine();
			String str1 = str.replaceAll("[\\pP\\p{Punct}]","");//clear all punctuations
			String eachLine1[] = str1.split("\\s+");
			for(String word: eachLine1) {
				//Words are not empty and do not contain numbers
				if(!isEmpty(word) && !isContainNumber(word))
					wordsBuffer.append(word.toLowerCase()+" ");
			}
			//infile1.nextLine();
		}
		infile1.close();
	}

	//Construct a word as an ordered WordNode single-linked list
    private static void createOrderedWordsList(String[] words,WordNode wordsHead){
		for(String word:words){
			if(word==null) continue;
			WordNode p = wordsHead;
			if(p.next==null){
				WordNode node = new WordNode();
				node.word = word;
				node.frequency = 1;
				p.next = node;
			}else{
				WordNode q = p.next;
				while(q!=null){
                    //sort string
					int result = q.word.compareToIgnoreCase(word);
					if(result<0){
						p = q;
						q = q.next;
					}else if(result==0){
						q.frequency+=1; break;
					}else{
						WordNode node = new WordNode();
						node.word = word;
						node.frequency = 1;
						p.next = node;
						node.next = q;
						break;
					}
				}
				if(q==null){
					WordNode node = new WordNode();
					node.word = word;
					node.frequency = 1;
					p.next = node;
				}
			}
		}
	}
	//Construct neighbors
	private static void reateNeighbors(WordNode wordsHead){
		WordNode q = wordsHead.next;
		while(q!=null){
			WordNode p = wordsHead;
			WordNode curNode = q;
			String word = curNode.word;
			while(p.next!=null){
				WordNode wn = p.next;
				if(wn==curNode) {
					p = p.next;
					continue;
				}
				if(word.length()!=wn.word.length()) {
					p = p.next;
					continue;
				}
				if(isNeighbors(word,wn.word)){
					curNode.neighbors.add(wn.word);
				}
				p = p.next;
			}
			q = q.next;
		}
	}

	//Determine whether the two strings are neighbors
	private static boolean isNeighbors(String word1,String word2){
		char[] char1 =word1.toCharArray();
		char[] char2 =word2.toCharArray();
		char1[0] = '*';//change both prefix to *, check if the remainings are the same
		char2[0] = '*';
		String prefix1 = new String(char1);
		String prefix2 = new String(char2);
		boolean prefix = prefix1.equals(prefix2);

		char[] char3 =word1.toCharArray();
		char[] char4 =word2.toCharArray();
		char3[char3.length-1] = '*';//change both suffix to *, check if the remainings are the same
		char4[char4.length-1] = '*';
		String suffix1 = new String(char3);
		String suffix2 = new String(char4);
		boolean suffix = suffix1.equals(suffix2);

		return prefix||suffix;
	}

	//Determine if the word is empty
	private static boolean isEmpty(String word){
		if(word==null) return true;
		if("".equals(word.trim())) return true;
		return false;
	}

	//Determine if the word contains numbers
	private static boolean isContainNumber(String word){
	    boolean containNumber = false;
		if(word==null) return false;
		for(int i=0;i<word.length();i++) {
			if (Character.isDigit(word.charAt(i))) {
				containNumber = true;
				break;
			}
		}
		return containNumber;
	}
    //Write the results to a file
	public static void writeData(String fileName, WordNode wordsHead)throws IOException {
		try {
			PrintWriter outFile = new PrintWriter(new File(fileName));
			WordNode q = wordsHead.next;
			while (q!=null){
				outFile.println(q.word + " " + q.frequency + " " + getNeighbors(q));
				q = q.next;
			}
			outFile.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	//Get the neighbors elements
	private static String getNeighbors(WordNode wordNode){
		boolean hasNeighbors = false;
		 StringBuffer neighbors = new StringBuffer("[");
		 for(String data:wordNode.neighbors){
		 	neighbors.append(data + ",");
			 hasNeighbors = true;
		 }
		if(hasNeighbors) neighbors.deleteCharAt(neighbors.length()-1);
		 neighbors.append("]");
		 return neighbors.toString();
	}
}