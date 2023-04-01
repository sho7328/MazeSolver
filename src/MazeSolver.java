/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */
// SOPHIE HO 3/31/2023
// Methods written by Sophie in MazeSolver class:
    // getSolution()
    // solveMazeDFS()
    // solveMazeBFS()
// These methods help solve the given maze through either depth-first search or breadth-first search.

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Create a stack for the parent cells
        Stack<MazeCell> cells = new Stack<MazeCell>();
        // Start with the end (goal) cell
        MazeCell mc = maze.getEndCell();
        // While the start cell hasn't been reached:
        do
        {
            // Add the cell to the stack.
            cells.push(mc);
            // Set it as its parent.
            mc = mc.getParent();
        }
        while (!(mc == maze.getStartCell()));
        // Create an arrayList so that the cells can be stored in order.
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        // While not all the parent cells have been added yet:
        while(!cells.empty())
        {
            // Pop a cell from the stack and add it to the ArrayList.
            solution.add(cells.pop());
        }
        // Add the starting cell at teh very beginning of the ArrayList.
        solution.add(0, maze.getStartCell());
        // Return the in-order ArrayList.
        return solution;
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Create a stack to store MazeCells
        Stack<MazeCell> dfs = new Stack<MazeCell>();
        // The first cell in the stack is the starting cell. Set it as explored so that it is not explored again.
        MazeCell cell = maze.getStartCell();
        dfs.push(cell);
        cell.setExplored(true);
        // While the end cell has not been reached:
        while(!dfs.empty())
        {
            // cell is the next mazeCell that is explored
            cell = dfs.pop();
            // Check the neighboring cells to see if they can be visited or not (if they are method or not) using the method getNeighbors()
            ArrayList<MazeCell> neighbors = getNeighbors(cell);
            for(int i = 0; i < neighbors.size(); i++)
            {
                // Add the valid neighbor cells to the stack in order of NESW
                dfs.push(neighbors.get(i));
                // Set them as explored, so they aren't added to the stack again.
                neighbors.get(i).setExplored(true);
                // Set the neighbors' parent cell as the original cell.
                neighbors.get(i).setParent(cell);
            }
        }
        // Return an ArrayList of all the parent cells that lead to the solution using the method getSolution().
        return getSolution();
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST
        // Create a queue to store MazeCells
        Queue<MazeCell> bfs = new LinkedList<MazeCell>();
        /// The first cell in the queue is the starting cell. Set it as explored so that it is not explored again.
        MazeCell cell = maze.getStartCell();
        bfs.add(cell);
        cell.setExplored(true);
        // While the end cell has not been reached:
        while(!bfs.isEmpty())
        {
            // cell is the next mazeCell that is explored
            cell = bfs.remove();
            // Check the neighboring cells to see if they can be visited or not (if they are method or not) using the method getNeighbors()
            ArrayList<MazeCell> neighbors = getNeighbors(cell);
            for(int i = 0; i < neighbors.size(); i++)
            {
                // Add the valid neighbor cells to the stack in order of NESW
                bfs.add(neighbors.get(i));
                // Set them as explored, so they aren't added to the stack again.
                neighbors.get(i).setExplored(true);
                // Set the neighbors' parent cell as the original cell.
                neighbors.get(i).setParent(cell);
            }
        }
        // Return an ArrayList of all the parent cells that lead to the solution using the method getSolution().
        return getSolution();
    }

    // getNeighbors checks all the neighbors of a given cell to see if they are valid or not.
    // This method returns an ArrayList of mazeCell objects.
    public ArrayList<MazeCell> getNeighbors(MazeCell cell)
    {
        // Create an ArrayList to put valid neighboring mazeCells in.
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
        // North first: check if the cell is valid. If so, add it to the ArrayList.
        if(maze.isValidCell(cell.getRow() - 1, cell.getCol()))
        {
            neighbors.add(maze.getCell(cell.getRow() - 1, cell.getCol()));
        }
        // Same for East,
        if(maze.isValidCell(cell.getRow(), cell.getCol() + 1))
        {
            neighbors.add(maze.getCell(cell.getRow(), cell.getCol() + 1));
        }
        // And South,
        if(maze.isValidCell(cell.getRow() + 1, cell.getCol()))
        {
            neighbors.add(maze.getCell(cell.getRow() + 1, cell.getCol()));
        }
        // And West.
        if(maze.isValidCell(cell.getRow(), cell.getCol() - 1))
        {
            neighbors.add(maze.getCell(cell.getRow(), cell.getCol() - 1));
        }
        // Return the final ArrayList with all the valid neighbors.
        return neighbors;
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
