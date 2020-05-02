import java.util.*;
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import java.io.*;

public class WordMatchTester{

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
        //Please enter pattern
        //System.out.println("Please enter pattern -->");
        Scanner kb = new Scanner(System.in);
        String pattern = kb.next();
        //Map<String,Integer> result = Helper.regex(wordsBuffer.toString(),pattern);
        List<Task2Result> result = Helper.regex2(wordsBuffer.toString(),pattern);
        createOrderedWordsList(result);
        writeData(outputFileName,result);//covered
        //writeData(outputFileName,result,pattern);//not covered
    }

    public static void loadData(String fileName1,StringBuffer wordsBuffer)throws IOException {
        //Read File1
        Scanner infile1 = new Scanner(new File(fileName1));
        while(infile1.hasNext()) {
            String str = infile1.nextLine();
            String str1 = str.replaceAll("[\\pP\\p{Punct}]","");//clear punctuations
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

  //Write the results to a file (covered)
    public static void writeData(String fileName,List<Task2Result> result){
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(new File(fileName));
            if(result.isEmpty()){
                outFile.print("No words in the lexicon match the pattern");
                System.out.println("No words in the lexicon match the pattern");
            }else {
                for (Task2Result tr : result) {
                    outFile.println(tr.key + "  " + tr.count);
                    System.out.println(tr.key + "  " + tr.count);
                }
            }
            outFile.flush();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(outFile!=null) outFile.close();
        }
    }
  
    //Write the results to a file (not covered)
    /*public static void writeData(String fileName,List<Task2Result> result,String pattern){
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(new FileWriter(fileName,true));
            //The input pattern
            outFile.println(pattern);
            System.out.println(pattern);
            //Matching result
            if(result.isEmpty()){
                outFile.println("\t No words in the lexicon match the pattern");
                System.out.println("\t No words in the lexicon match the pattern");
            }else {
                for (Task2Result tr : result) {
                    outFile.println("\t" + tr.key + "  " + tr.count);
                    System.out.println("\t" + tr.key + "  " + tr.count);
                }
            }
            outFile.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(outFile!=null) outFile.close();
        }
    }*/
    
    //show list in order (bubble sort)
    private static void createOrderedWordsList(List<Task2Result> result) {
    	int left = 0;
        int right = result.size()-1;
        for(int i=right; i>left; i--) { 
            for (int j=left; j<i; j++) {
                if (result.get(j).key.compareTo(result.get(j+1).key)>0) {
                    swap(result, j, j+1);
                }
            }
        }
    }
    //swap function
    private static void swap(List<Task2Result> result,int i,int j) {
    	Task2Result temp = result.get(i);
        result.set(i, result.get(j));
        result.set(j, temp);
    }
}