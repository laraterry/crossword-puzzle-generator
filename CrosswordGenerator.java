import java.util.*;

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

    public void tryPlacingWord(String word, boolean horizontal){
        for (int index = 0; index < word.length(); index++){
            for (int i = 0; i < grid.length; i ++){
                for (int j = 0; j < grid.length; j++){
                    if (grid[i][j] == word.charAt(index)){
                        if (horizontal) {
                            int row = i;
                            int col = j - index;
                            if (canPlaceWord(word, row, col, horizontal)) {
                                placeWord(word, row, col, horizontal);
                                return; 
                            }
                        } else {
                            int row = i - index;
                            int col = j;
                            if (canPlaceWord(word, row, col, horizontal)) {
                                placeWord(word, row, col, horizontal);
                                return; // stop after placing
                            }
                        }
                        
                    }
                }
            }
        }

        System.out.println("Could not place word: " + word);


    }

    public static void main(String[] args) {
        CrosswordGenerator generator = new CrosswordGenerator();
        generator.initializeGrid(10);
        generator.placeFirstWord("HELLO");
        generator.tryPlacingWord("OLE", false);    // Should cross at 'L' or 'O'
        generator.tryPlacingWord("HELP", true);    // Try another horizontal word
        generator.tryPlacingWord("EEL", false);    // Try vertical again
        generator.printGrid();
    }
    

}






