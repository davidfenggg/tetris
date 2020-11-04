import java.util.*;

/**
 * Created by DavidFeng on 3/7/18. This class is a bounded grid that implements a 2d array
 *
 * @author David Feng
 * @version 13 March 2018
 * @param <E> the type of object in the MyBoundedGrid
 *
 */
public class MyBoundedGrid <E>
{

  // Private instance variable stores the grid
  private Object[][] mainArray;

  /**
   * Constructs a MyBoundedGrid Object
   * @param rows  the amount of rows in the grid
   * @param cols  the amount of columns in the grid
   */
  public MyBoundedGrid(int rows, int cols)
  {
    mainArray = new Object[rows][cols];
  }

  /**
   * Gets the number of rows
   * @return  the number of rows
   */
  public int getNumRows()
  {
    return mainArray.length;
  }

  /**
   * Gets the number of columns in the grid
   * @return  the number of columns
   */
  public int getNumCols()
  {
    return mainArray[0].length;
  }

  /**
   * Ceh;cks if the position (r,c) is valid on the grid or in bounds
   * @param loc the location of the objet
   * @return  true  if the location has valid coordinates
   *          false otherwise
   */
  public boolean isValid(Location loc)
  {
    if(loc == null)
    {
      return false;
    }
    int row = loc.getRow();
    int col = loc.getCol();
    if(row < 0 || row > getNumRows() - 1)
    {
      return false;
    }
    if(col < 0 || col > getNumCols() - 1)
    {
      return false;
    }
    return true;
  }

  /**
   * Puts an object at location loc
   * @param loc the location the object should be put in
   * @param obj the object itself
   * @return  the old value at that location
   */
  public E put(Location loc, E obj)
  {
    if(isValid(loc))
    {
      Object meme = mainArray[loc.getRow()][loc.getCol()];
      mainArray[loc.getRow()][loc.getCol()] = obj;
      return (E) meme;
    }
    return null;
  }

  /**
   * Removes the object at the location
   * @param loc the location in which an object will be removed
   * @return  the object at the location
   */
  public E remove(Location loc)
  {
    if(isValid(loc))
    {
      Object meme = mainArray[loc.getRow()][loc.getCol()];
      mainArray[loc.getRow()][loc.getCol()] = null;
      return (E) meme;
    }
    return null;
  }

  /**
   * Gets the object at the location
   * @param loc the location in which an object is retrieved
   * @return  the object at the location
   */
  public E get(Location loc)
  {
    if(isValid(loc))
    {
      Object meme = mainArray[loc.getRow()][loc.getCol()];
      return (E) meme;
    }
    return null;
  }

  /**
   * Returns a list of the occupied positions on the grid
   * @return  the list of the occupied positions
   */
  public ArrayList<Location> getOccupiedLocations()
  {
    ArrayList<Location> lol = new ArrayList<Location>();
    for(int i = 0; i < getNumRows(); i++)
    {
      for(int j = 0; j < getNumCols(); j++)
      {
        if(mainArray[i][j] != null)
        {
          Location location = new Location(i, j);
          lol.add(location);
        }
      }
    }
    return lol;
  }

}
