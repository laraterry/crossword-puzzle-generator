import java.util.*;

public class CrosswordGenerator{
    char[][] grid;

    public void initializeGrid(int size){
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
                System.out.println(grid[i][j]);
            }
        }
        
    }

}



