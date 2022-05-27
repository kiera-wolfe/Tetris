
/**
 * Write a description of class O here.
 *
 * @author Greg Johnson, University of Connecticut
 * @version 0.3
 */
public class O extends Tetrimino
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class O
     * @param  x  the x-coordinate in the JPanel to which to move this object
     * @param  y  the y-coordinate in the JPanel to which to move this object
     */
    public O(int x, int y)
    {
        super(java.awt.Color.YELLOW);
        // initialise instance variables
        this.setLocation(x,y);
    }

    /**
     * Sets the location of the composite Tetrimino object
     *
     * @param  x  the x-coordinate in the JPanel to which to move this object
     * @param  y  the y-coordinate in the JPanel to which to move this object
     *
     */
    public void setLocation(int x, int y)
    {
        _x = x;
        _y = y;
        _block4.setLocation(x,y);
        _block2.setLocation(x,y+TetrisConstants.BLOCK_SIZE);
        _block3.setLocation(x+TetrisConstants.BLOCK_SIZE,y);
        _block1.setLocation(x+TetrisConstants.BLOCK_SIZE,y+TetrisConstants.BLOCK_SIZE);
    }
    public boolean turnLeft(){return true;}
    public boolean turnRight(){return true;}
}
