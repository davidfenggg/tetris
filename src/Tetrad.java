import java.awt.Color;
import java.util.concurrent.Semaphore;

/**
 * The class that represents a 4 block tetrad in the game of Tetris
 *
 * @author David Feng
 * @version April 03, 2018
 */
public class Tetrad {

  //Instance variables contain the info about the Tetrad
  private Block[] blocks;
  private MyBoundedGrid<Block> grid;
  private Semaphore lock;

  /**
   * Constructs a Tetrad object
   * @param gr  the grid the tetrad is in
   */
  public Tetrad(MyBoundedGrid<Block> gr)
  {
    blocks = new Block[4];
    lock = new Semaphore(1, true);
    for(int i = 0; i < 4; i++)
    {
      blocks[i] = new Block();
    }
    grid = gr;
    Color random = getRandomColor();
    Location[] locs = new Location[4];
    for(int i = 0; i < 4; i++)
    {
      blocks[i].setColor(random);
    }
    if(random.equals(Color.RED))
    {
      locs[0] = new Location(0, 4);
      locs[1] = new Location(0, 5);
      locs[2] = new Location(0, 3);
      locs[3] = new Location(0, 6);
      addToLocations(grid, locs);
    }
    else if(random.equals(Color.GRAY))
    {
      locs[1] = new Location(0, 4);
      locs[0] = new Location(0, 5);
      locs[2] = new Location(0, 6);
      locs[3] = new Location(1, 5);
      addToLocations(grid, locs);
    }
    else if(random.equals(Color.CYAN))
    {
      locs[0] = new Location(0, 4);
      locs[1] = new Location(0, 5);
      locs[2] = new Location(1, 4);
      locs[3] = new Location(1, 5);
      addToLocations(grid, locs);
    }
    else if(random.equals(Color.YELLOW))
    {
      locs[0] = new Location(0, 5);
      locs[1] = new Location(0, 4);
      locs[2] = new Location(0, 6);
      locs[3] = new Location(1, 4);
      addToLocations(grid, locs);
    }
    else if(random.equals(Color.MAGENTA))
    {
      locs[0] = new Location(0, 5);
      locs[1] = new Location(0, 4);
      locs[2] = new Location(0, 6);
      locs[3] = new Location(1, 6);
      addToLocations(grid, locs);
    }
    else if(random.equals(Color.BLUE))
    {
      locs[0] = new Location(0, 4);
      locs[1] = new Location(0, 5);
      locs[2] = new Location(1, 4);
      locs[3] = new Location(1, 3);
      addToLocations(grid, locs);
    }
    else
    {
      locs[0] = new Location(0, 4);
      locs[1] = new Location(0, 3);
      locs[2] = new Location(1, 4);
      locs[3] = new Location(1, 5);
      addToLocations(grid, locs);
    }
  }

  /**
   * Gets all the locations of the Tetrad
   * @return an array of the locations of the blocks
   */
  public Location[] getLocations()
  {
    Location[] temp = new Location[4];
    for(int i = 0; i < 4; i++)
    {
      temp[i] = blocks[i].getLocation();
    }
    return temp;
  }

  /**
   * Adds the blocks to the locations specified
   * @param grid  the grid to be added to
   * @param locs  the locations to be added to on the grid
   */
  private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs)
  {
    for(int i = 0; i < locs.length; i++)
    {
      grid.put(locs[i], blocks[i]);
      if(blocks[i].getLocation() == null)
      {
        blocks[i].putSelfInGrid(grid, locs[i]);
      }
      else
      {
        blocks[i].moveTo(locs[i]);
      }
    }
  }

  /**
   * Gets a random color
   * @return  a color for the tetrad
   */
  private Color getRandomColor()
  {
    int x = (int) (Math.random() * 7);
    if(x == 0)
    {
      return Color.RED;
    }
    if(x == 1)
    {
      return Color.GRAY;
    }
    if(x == 2)
    {
      return Color.CYAN;
    }
    if(x == 3)
    {
      return Color.YELLOW;
    }
    if(x == 4)
    {
      return Color.MAGENTA;
    }
    if(x == 5)
    {
      return Color.BLUE;
    }
    if(x == 6)
    {
      return Color.GREEN;
    }
    else
    {
      throw new NullPointerException("There is something wrong "
          + "with getting colors LOL");
    }
  }

  /**
   * Removes the blocks and returns the original positions
   * @return returns the original locations of the blocks in an array
   */
  private Location[] removeBlocks()
  {
    Location[] old = new Location[4];
    for (int i = 0; i < 4; i++)
    {
      old[i] = blocks[i].getLocation();
      blocks[i].removeSelfFromGrid();
    }
    for(int i = 0; i < 4; i++)
    {
      grid.put(old[i], null);
    }
    return old;
  }

  /**
   * Checks if the locations specified are empty
   * @param grid  the grid to be checked
   * @param locs  the locations on the grid to be checked
   * @return  true if the locations are empty; false otherwise
   */
  private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
  {
    for(int i = 0; i < locs.length; i++)
    {
      if(locs[i] == null)
      {
        return false;
      }
      Block block = grid.get(locs[i]);
      if(block != null)
      {
        return false;
      }
      Location temp = locs[i];
      int row = locs[i].getRow();
      int col = locs[i].getCol();
      if(row < 0 || row > 19 || col < 0 || col > 9)
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Moves the tetrad deltaRow down and deltaCol right
   * @param deltaRow  the number of rows down to be moved
   * @param deltaCol  the number of rows right to be moved
   * @return true if could be translated, false otherwise
   */
  public boolean translate(int deltaRow, int deltaCol)
  {
    try
    {
      lock.acquire();
      Location[] temp = new Location[4];
      for(int i = 0; i < 4; i++)
      {
        temp[i] = blocks[i].getLocation();
        if(blocks[i] == null)
        {
          return false;
        }
        if(temp[i] == null)
        {
          return false;
        }
        int row = temp[i].getRow();
        int col = temp[i].getCol();
        row = row + deltaRow;
        col = col + deltaCol;
        Location newLoc = new Location(row, col);
        temp[i] = newLoc;
      }
      Location[] current = removeBlocks();
      if(areEmpty(grid, temp))
      {
        addToLocations(grid, temp);
        return true;
      }
      addToLocations(grid, current);
      return false;
    }
    catch(InterruptedException e)
    {
      return false;
    }
    finally
    {
      lock.release();
    }
  }

  /**
   * Rotates the tetrad about a point
   * @return  true if could be rotated, false otherwise
   */
  public boolean rotate() {
    try
    {
      lock.acquire();
      Location[] temp = new Location[4];
      for(int i = 0; i < 4; i++)
      {
        temp[i] = blocks[i].getLocation();
        int row = temp[i].getRow();
        int col = temp[i].getCol();
        int newRow = 0;
        int newCol = 0;
        int pRow = blocks[0].getLocation().getRow();
        int pCol = blocks[0].getLocation().getCol();
        newRow = pRow - pCol + col;
        newCol = pRow + pCol - row;
        Location newLoc = new Location(newRow, newCol);
        temp[i] = newLoc;
      }
      Location[] current = removeBlocks();
      if(areEmpty(grid, temp))
      {
        addToLocations(grid, temp);
        return true;
      }
      addToLocations(grid, current);
      return false;
    }
    catch(InterruptedException e)
    {
      return false;
    }
    finally
    {
      lock.release();
    }
  }

}
