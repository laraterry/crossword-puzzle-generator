import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * javac CrosswordGenerator.java
    java CrosswordGenerator
 */

public class CrosswordGenerator{
    char[][] grid;

    public void initializeGrid(int size){
        /*initalizes a size x size puzzle grid with '-' */
        grid = new char[size][size];
        for (int i = 0; i < size; i ++){
            for (int j = 0; j < size; j++){
                grid[i][j] = '-';
            }
        }
    }

    public void printGrid(){
        for (int i = 0; i < grid.length; i ++){
            for (int j = 0; j < grid.length; j++){
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
        
    }

    public List<String> loadWordsFromFile(String file){
        List<String> words = new ArrayList<>();

        try{
            Scanner scan = new Scanner(new File(file));
            while(scan.hasNextLine()){
                String word = scan.nextLine().trim().toUpperCase();
                words.add(word);
            }

            scan.close();

        } catch(FileNotFoundException error){
            System.out.println("File not found: " + file);
        }
        

        return words;
    }

    public void placeFirstWord(String word){
        /*places the first word in the grid in the middle */
        int row = grid.length / 2;
        int col = (grid.length - word.length()) / 2;
        for (char ch: word.toCharArray()){
            grid[row][col] = ch;
            col ++;
        }


    }

    public boolean canPlaceWord(String word, int row, int col, boolean horizontal){
        int r;
        int c;
        for (int i = 0; i < word.length(); i ++){
            if (horizontal){
                r = row;
                c = col + i;
            } else{
                r = row + i;
                c = col;
            }

            if (r < 0 || r >= grid.length || c < 0 || c >= grid.length) {
                return false;
            }

            char gridCharacter = grid[r][c];
            char wordCharacter = word.charAt(i);

            if (gridCharacter != '-' && gridCharacter != wordCharacter){
                return false;
            }

            //adjacent checks
            if (gridCharacter == '-'){
                if (horizontal) {
                    //check above and below
                    if (r > 0 && grid[r - 1][c] != '-') return false;
                    if (r < grid.length - 1 && grid[r + 1][c] != '-') return false;
                } else {
                    //check left and right
                    if (c > 0 && grid[r][c - 1] != '-') return false;
                    if (c < grid.length - 1 && grid[r][c + 1] != '-') return false;
                }
            } else if (gridCharacter == wordCharacter) {
                //valid overlap
                continue;
            } else {
                //conflict with letter already there
                return false;
            }


        }


        //check cell before and after the word to prevent merging words
        if (horizontal) {
            if (col > 0 && grid[row][col - 1] != '-') return false; //check before word
            if (col + word.length() < grid.length && grid[row][col + word.length()] != '-') return false; // Check after word
        } else {
            if (row > 0 && grid[row - 1][col] != '-') return false; //check above word
            if (row + word.length() < grid.length && grid[row + word.length()][col] != '-') return false; // Check below word
        }
            return true;
        }

    
        public void placeWord(String word, int row, int col, boolean horizontal){
        int r;
        int c;

        for (int i = 0; i < word.length(); i++){
            if (horizontal){
                r = row;
                c = col + i;
            } else{
                r = row + i;
                c = col;
            }

            grid[r][c] = word.charAt(i);
        }
    }

    public boolean tryPlacingWord(String word, boolean horizontal){
        for (int index = 0; index < word.length(); index++){
            for (int i = 0; i < grid.length; i ++){
                for (int j = 0; j < grid.length; j++){
                    if (grid[i][j] == word.charAt(index)){
                        if (horizontal) {
                            int row = i;
                            int col = j - index;
                            if (canPlaceWord(word, row, col, horizontal)) {
                                placeWord(word, row, col, horizontal);
                                return true; 
                            }
                        } else {
                            int row = i - index;
                            int col = j;
                            if (canPlaceWord(word, row, col, horizontal)) {
                                placeWord(word, row, col, horizontal);
                                return true; // stop after placing
                            }
                        }
                        
                    }
                }
            }
        }

        System.out.println("Could not place word: " + word);
        return false;


    }

    public static void main(String[] args) {
        CrosswordGenerator generator = new CrosswordGenerator();

        List<String> words = generator.loadWordsFromFile("words.txt");



        generator.initializeGrid(20);

        Random rand = new Random();
        int randomIndex = rand.nextInt(words.size());
        generator.placeFirstWord(words.get(randomIndex));
        words.remove(randomIndex);

        boolean horizontal = false;
        for(int i = 0; i < words.size(); i++){
            String word = words.get(i);
            boolean placed = generator.tryPlacingWord(word, horizontal);
            if (!placed) {
                placed = generator.tryPlacingWord(word, !horizontal); // Try other direction
                }

            if (!placed) {
                System.out.println("Could not place word: " + word);
            }

        }

        generator.printGrid();
    }
    

}






