/**
 * Created by xinweiwang on 11/26/16.
 */
public class PrintNeatly {

    public static final int CANNOTREACH = Integer.MAX_VALUE;

    private static int[][] sumMatrix;

    private static int[][] lastMatrix;

    private static int[][] currentLeftMatrix;

    public static void main(String[] args) {

        String input = "Output format: " +
                "Your program should print its output to the console. " +
                "In the first line of output, print the total penalty (sum of cubes of " +
                "the number of spaces added in each line). The second line of output is blank. " +
                "Starting from the third line of the output, print the input words, " +
                "formatted according to your algorithm. Try to distribute the spaces " +
                "within a line to make the output be left and right justified. In " +
                "the sample output, extra spaces added are shown as plus symbol. For this " +
                "file, penalty with M=72 is 430, M=93 is 280, and, M=132 is 137.";

        int length = input.split(" ").length;
        //System.out.println(length);
        int sum = 0;
        for(int i=0;i<length;i++){
            sum+=input.split(" ")[i].length()+1;
            //System.out.println(input.split(" ")[i]);
        }
        //System.out.println(sum-1);
        count(input.split(" "),132);

        int min = CANNOTREACH;
        int index = -1;
        for(int i=0;i<length;i++){
            if(sumMatrix[length-1][i]<=min){
                min = sumMatrix[length-1][i];
                index = i;
            }
        }
        System.out.println(min);

        System.out.println(index);
        System.out.println(currentLeftMatrix[length-1][index]);
        System.out.println();
        int lastIndex = lastMatrix[length-1][index];

        System.out.println(lastIndex);
        System.out.println(input.split(" ")[index-1]);
        while(lastIndex!=-1){
            System.out.println(currentLeftMatrix[index-1][lastIndex]);
            System.out.println(input.split(" ")[index-1]);
            int temp = lastIndex;
            lastIndex = lastMatrix[index-1][lastIndex];
            index = temp;
            //System.out.println(lastIndex);
        }

        //printlnMatrix(sumMatrix);
    }


    private static void printlnMatrix(int[][] matrix){
        for(int i=0;i<matrix.length;i++){
            for(int j= 0 ;j< matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    private static void init(int length){
        sumMatrix = new int[length][length];
        for(int j = 0;j<length;j++){
            for(int i=0;i<length;i++){
                sumMatrix[j][i] = CANNOTREACH;
            }
        }
        lastMatrix = new int[length][length];
        currentLeftMatrix = new int[length][length];
    }

    private static void count(String[] strs,int m){
        if(strs.length==0){
            return;
        }
        init(strs.length);
        sumMatrix[0][0]=0;
        currentLeftMatrix[0][0] = m - strs[0].length();
        lastMatrix[0][0] = -1;

        for(int j=1;j<strs.length;j++){
            for(int i=0;i<j;i++){
                if(currentLeftMatrix[j-1][i]-1-strs[j].length()>=0) {
                    sumMatrix[j][i] = sumMatrix[j - 1][i];
                    lastMatrix[j][i] = lastMatrix[j - 1][i];
                    currentLeftMatrix[j][i] = currentLeftMatrix[j - 1][i] - strs[j].length() - 1;
                }
            }
            int minIndex = -1;
            int min = CANNOTREACH;
            for(int i=0;i<j;i++){
                if(sumMatrix[j-1][i]<min){
                    min=sumMatrix[j-1][i]+currentLeftMatrix[j-1][i];
                    minIndex = i;
                }
            }
            sumMatrix[j][j] = min;
            lastMatrix[j][j] = minIndex;
            currentLeftMatrix[j][j] = m - strs[j].length();
        }


    }

}
