import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by xinweiwang on 11/26/16.
 */
public class PrintNeatly {

    public static final int CANNOTREACH = Integer.MAX_VALUE;

    private static int[][] sumMatrix;

    private static int[][] lastMatrix;

    private static int[][] currentLeftMatrix;

    public static void main(String[] args) throws FileNotFoundException {
        /*
        String input = "Output format: " +
                "Your program should print its output to the console. " +
                "In the first line of output, print the total penalty (sum of cubes of " +
                "the number of spaces added in each line). The second line of output is blank. " +
                "Starting from the third line of the output, print the input words, " +
                "formatted according to your algorithm. Try to distribute the spaces " +
                "within a line to make the output be left and right justified. In " +
                "the sample output, extra spaces added are shown as plus symbol. For this " +
                "file, penalty with M=72 is 430, M=93 is 280, and, M=132 is 137.";
                */

//        String[] strs = getStringArray("input.txt");
        /*
        int length = input.split(" ").length;
        //System.out.println(length);
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += input.split(" ")[i].length() + 1;
            //System.out.println(input.split(" ")[i]);
        }
        //System.out.println(sum-1);
        */


        Scanner in;
        int M = 72;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
            if (args.length > 1) {
                M = Integer.parseInt(args[1]);
            }

        } else {
            in = new Scanner(System.in);
        }
        String paragraph = "";
        while (in.hasNextLine()) {
            String newline = in.nextLine();
            if (newline.equals("")) break;
            paragraph += " " + newline;
        }
        paragraph = paragraph.trim();
        String[] strs = paragraph.split("\\s+");

        count(strs, M);
        print(strs);
    }


    private static void printlnMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void init(int length) {
        sumMatrix = new int[length][length];
        for (int j = 0; j < length; j++) {
            for (int i = 0; i < length; i++) {
                sumMatrix[j][i] = CANNOTREACH;
            }
        }
        lastMatrix = new int[length][length];
        currentLeftMatrix = new int[length][length];
    }

    private static void count(String[] strs, int m) {
        if (strs.length == 0) {
            return;
        }
        init(strs.length);
        sumMatrix[0][0] = 0;
        currentLeftMatrix[0][0] = m - strs[0].length();
        lastMatrix[0][0] = -1;

        for (int j = 1; j < strs.length; j++) {
            for (int i = 0; i < j; i++) {
                if (currentLeftMatrix[j - 1][i] - 1 - strs[j].length() >= 0) {
                    sumMatrix[j][i] = sumMatrix[j - 1][i];
                    lastMatrix[j][i] = lastMatrix[j - 1][i];
                    currentLeftMatrix[j][i] = currentLeftMatrix[j - 1][i] - strs[j].length() - 1;
                }
            }
            int minIndex = -1;
            int min = CANNOTREACH;
            for (int i = 0; i < j; i++) {
                if ((sumMatrix[j - 1][i] + (int)Math.pow(currentLeftMatrix[j - 1][i],3)) < min) {
                    min = (sumMatrix[j - 1][i] + (int)Math.pow(currentLeftMatrix[j - 1][i],3));
                    minIndex = i;
                }
            }
            sumMatrix[j][j] = min;
            lastMatrix[j][j] = minIndex;
            currentLeftMatrix[j][j] = m - strs[j].length();
        }
    }


//    private static String[] getStringArray(String fileName){
//        List<String> list = new ArrayList<>();
//        FileReader fileReader = null;
//        BufferedReader bufferedReader = null;
//        try {
//            fileReader = new FileReader(fileName);
//            bufferedReader = new BufferedReader(fileReader);
//            String line = null;
//            while((line = bufferedReader.readLine())!=null){
//                String[] strs = line.split("\\s+");
//                for(int i=0;i<strs.length;i++){
//                    list.add(strs[i]);
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(bufferedReader!=null){
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return list.toArray(new String[list.size()]);
//
//    }

    private static void print(String[] strs){
        int length = strs.length;
        int min = CANNOTREACH;
        int lastIndex = -1;
        for (int i = 0; i < length; i++) {
            if (sumMatrix[length - 1][i] <= min) {
                min = sumMatrix[length - 1][i];
                lastIndex = i;
            }
        }
        System.out.println(min);
        //System.out.println(index);
        int index = length;
        //System.out.println(currentLeftMatrix[index-1][lastIndex]);
        //System.out.println(input.split(" ")[lastIndex - 1]);

        //lastIndex = lastMatrix[index - 1][lastIndex];

        //System.out.println(lastIndex);
        //System.out.println(input.split(" ")[index - 1]);
        ArrayList<Integer> newLine = new ArrayList<>();
        ArrayList<Integer> numSpace = new ArrayList<>();



        while (lastIndex != -1) {
            newLine.add(index-1);
            //System.out.println(index-1);
            numSpace.add(currentLeftMatrix[index - 1][lastIndex]);
            //System.out.println(strs[index - 1]);
            int temp = lastIndex;
            lastIndex = lastMatrix[index - 1][lastIndex];
            index = temp;
        }
        numSpace.remove(0);
        numSpace.add(0,0);
        /*
        int sum = 0;
        for(Integer n:numSpace){
            sum+= Math.pow(n,3);
        }
        */
        //System.out.println(numSpace.size());
        //System.out.println(newLine.size());
        //System.out.println(sum);
        System.out.println();
        int j = newLine.size()-1;
        int num = numSpace.get(j);
        Set<Integer> ramdonSet = new HashSet<>();
        while(ramdonSet.size()<num){
            ramdonSet.add((int)(Math.random()*(newLine.get(j))));
        }
        for(int i =0;i<strs.length;i++){
            if(i == newLine.get(j)){

                System.out.print(strs[i]);

                System.out.println();
                //System.out.println(i);


                j--;
                if(j>=0) {
                    num = numSpace.get(j);
                    //ramdonSet = new HashSet<>();

                    while(ramdonSet.size()<num){
                        ramdonSet.add((int)(Math.random()*(newLine.get(j)-newLine.get(j+1)-1))+newLine.get(j+1)+1);
                    }
                    /*
                    System.out.println(ramdonSet.size());
                    for(Integer x:ramdonSet){
                        System.out.println(x);
                    }
                    */
                }
            }else {
                if(ramdonSet.contains(i)) {
                    System.out.print(strs[i] + " +");
                    ramdonSet.remove(i);
                }else{
                    System.out.print(strs[i]+" ");
                }
            }
        }
    }
}
