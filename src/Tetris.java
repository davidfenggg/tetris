import java.util.ArrayList;
import java.util.Scanner;

/**
 * The tetris class allows us to play a game of Tetris!
 *
 * @author David Feng
 * @version April 03, 2018
 */
public class Tetris implements ArrowListener{

  //Instance variables keep track of the screen
  private MyBoundedGrid<Block> grid;
  private BlockDisplay display;
  private Tetrad tetrad;
  private int score;

  /**
   * Construts a tetris object
   */
  public Tetris()
  {
    Scanner scan = new Scanner(System.in);
    int rows = 20;
    int cols = 10;
    score = 0;
    grid = new MyBoundedGrid<Block>(rows,cols);
    display = new BlockDisplay(grid, this);
    display.setArrowListener(this);
    display.setTitle("Tetris");
    tetrad = new Tetrad(grid);
    display.showBlocks();
  }

  /**
   * The tetrad rotates when the up button is pressed
   */
  public void upPressed()
  {
    tetrad.rotate();
    display.showBlocks();
  }

  /**
   * The tetrad moves down when the down button is pressed
   */
  public void downPressed()
  {
    tetrad.translate(1, 0);
    display.showBlocks();
  }

  /**
   * The tetrad moves left when the left button is pressed
   */
  public void leftPressed()
  {
    tetrad.translate(0, -1);
    display.showBlocks();
  }

  /**
   * The tetrad moves right when the right button is pressed
   */
  public void rightPressed()
  {
    tetrad.translate(0, 1);
    display.showBlocks();
  }

  /**
   * Hard drops the tetrad
   */
  public void spacePressed()
  {
    System.out.println("space");
    while(tetrad.translate(1,0))
    display.showBlocks();
  }

  /**
   * Gets the score of the game
   * @return  the score
   */
  public int getScore()
  {
    return score;
  }

  /**
   * Plays a game of Tetris!
   */
  public void play()
  {
    int x = 0;
    while(!false)
    {
      try
      {
        Thread.sleep(1000 - x);
        boolean createNew = tetrad.translate(1,0);
        if(createNew == false)
        {
          clearCompletedRows();
          tetrad = new Tetrad(grid);
        }
        display.showBlocks();
        if(x < 500)
        {
          x += 10;
        }
      }
      catch(InterruptedException e)
      {
        //ignore
      }
    }
  }

  /**
   * Checks whether or not the row is completed
   *
   * @param row the row to check
   * @return  true if the row is completed; false otherwise
   */
  private boolean isCompletedRow(int row)
  {
    for(int i = 0; i < grid.getNumCols(); i++)
    {
      Location loc = new Location(row, i);
      if(grid.get(loc) == null)
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Clears the row that is completed
   * @param row  the row to be cleared
   */
  private void clearRow(int row)
  {
    for(int i = 0; i < grid.getNumCols(); i++)
    {
      Location loc = new Location(row, i);
      grid.get(loc).removeSelfFromGrid();
    }
    ArrayList<Location> locs = new ArrayList<>();
    for(int i = row - 1; i >= 0; i--)
    {
      for(int j = 0; j < grid.getNumCols(); j++)
      {
        Location loc = new Location(i, j);
        if(loc != null)
        {
          locs.add(loc);
        }
      }
    }
    for(int i = 0; i < locs.size(); i++)
    {
      Location loc = locs.get(i);
      int r = loc.getRow();
      int c = loc.getCol();
      Location newLoc = new Location(r + 1, c);
      Block block = grid.get(loc);
      if(block != null)
      {
        block.moveTo(newLoc);
      }
    }
    display.showBlocks();
  }

  /**
   * Clears all the completed rows in the grid
   */
  private void clearCompletedRows()
  {
    for(int i = 0; i < grid.getNumRows(); i++)
    {
      if(isCompletedRow(i))
      {
        clearRow(i);
        score += 10;
      }
    }
  }

  private boolean isGameOver()
  {
    for(int i = 0; i < grid.getNumCols(); i++)
    {
      if(grid.get(new Location(0, i)) != null)
      {
        return true;
      }
    }
    return false;
  }




}
