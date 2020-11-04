import java.awt.Color;
/**
 * class BLock encapsulates a Block abstraction which can be placed into a Gridworld style grid
 * You are expected to comment this class according to the style guide.
 *
 * @author David Feng
 * @version 13 March 2018
 */
public class Block
{
  //Instance variables store information about each instance of the object
  private MyBoundedGrid<Block> grid;
  private Location location;
  private Color color;
  /**
   * constructs a blue block, because blue is the greatest color ever!
   */
  public Block()
  {
    color = Color.BLUE;
    grid = null;
    location = null;
  }

  /**
   * Gets the color of the block
   * @return the color of the block
   */
  public Color getColor()
  {
    return color;
  }

  /**
   * Changes the color of the block
   * @param newColor the new color the block should be set to
   */
  public void setColor(Color newColor)
  {
    color = newColor;
  }

  /**
   * Gets the grid itself
   * @return the grid
   */
  public MyBoundedGrid<Block> getGrid()
  {
    return grid;
  }

  /**
   * Gets the location on the grid
   * @return the location
   */
  public Location getLocation()
  {
    return location;
  }

  /**
   * Removes onself from the grid by setting it to null
   */
  public void removeSelfFromGrid()
  {
    grid.remove(location);
    location = null;
  }

  /**
   * Puts itself in the grid at location loc
   * @param gr  the grid to be used
   * @param loc the location to put self in
   */
  public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
  {
    Block temp = gr.get(loc);
    if(temp != null)
    {
      temp.grid = null;
      temp.location = null;
    }
    grid = gr;
    location = loc;
    gr.put(loc, this);
  }

  /**
   * Move self to another location on the grid
   * @param newLocation the new location to be moved
   */
  public void moveTo(Location newLocation)
  {
    Location old = location;
    putSelfInGrid(grid, newLocation);
    grid.remove(old);
    grid.put(newLocation, this);
  }

  /**
   * returns a string with the location and color of this block
   * @return the string version of the block
   */
  public String toString()
  {
    return "Block[location=" + location + ",color=" + color + "]";
  }
}