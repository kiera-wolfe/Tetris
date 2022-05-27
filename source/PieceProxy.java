

/**
 * Write a description of class TetriminoProxy here.
 *
 * @author Greg Johnson, University of Connecticut
 * @version 0.3
 */
public class PieceProxy implements Animatable
{
    // instance variables - replace the example below with your own
    private Tetrimino _p;

    /**
     * Sets the current piece to a newly created Tetrimino
     *
     * @param  m  the new Tetrimino for the user to control
     *
     */
    public void setPiece(Tetrimino m)
    {
        _p = m;
    }

    /**
     * Fills the currently animated Tetrimino on the screen
     * @param aBrush the Graphics2D brush used to fill the shapes
     *
     */
    public void fill (java.awt.Graphics2D aBrush){
    _p.fill(aBrush);
    }

    /**
     * Draws the currently animated Tetrimino on the screen
     * @param aBrush the Graphics2D brush used to draw the shapes
     *
     */
     public void draw (java.awt.Graphics2D aBrush) {
    _p.draw(aBrush);
    }

    /**
     * Sends a move up message to the currently animated Tetrimino
     * @return boolean Whether the move was successful
     *
     */
    public boolean moveUp()
    {
        return _p.moveUp();
    }

    /**
     * Moves the currently animated Tetrimino down an extra row
     * @return boolean Whether the move was successful
     *
     */
    public boolean moveDown()
    {
        return _p.moveDown();
    }

    /**
     * Moves the currently animated Tetrimino left one column
     * @return boolean Whether the move was successful
     *
     */
    public boolean moveLeft()
    {
        return _p.moveLeft();
    }

    /**
     * Moves the currently animated Tetrimino right one column
     * @return boolean Whether the move was successful
     *
     */
    public boolean moveRight()
    {
        return _p.moveRight();
    }

    /**
     * Rotates the currently animated Tetrimino left on the screen
     * @return boolean Whether the rotation was successful
     *
     */
    public boolean turnLeft()
    {
        return _p.turnLeft();
    }

    /**
     * Rotates the currently animated Tetrimino right on the screen
     * @return boolean Whether the rotation was successful
     *
     */
    public boolean turnRight()
    {
        return _p.turnRight();
    }
    public SmartRectangle getBlockOne() {
        return _p.getBlockOne();
    }
    public SmartRectangle getBlockTwo() {
        return _p.getBlockTwo();
    }
    public SmartRectangle getBlockThree() {
        return _p.getBlockThree();
    }
    public SmartRectangle getBlockFour() {
        return _p.getBlockFour();
    }

}
