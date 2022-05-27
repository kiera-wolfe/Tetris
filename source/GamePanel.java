    import java.awt.*;
    import javax.swing.*;
    import java.awt.event.*;
    import java.util.ArrayList;
    import java.util.Random;
    import java.awt.Color;
    import java.lang.*;
    
    /**
     * Write a description of class GamePanel here.
     *
     * @author Greg Johnson, University of Connecticut
     * @version 0.3
     */
    public class GamePanel extends JPanel implements ActionListener
    {
    
        // instance variables - replace the example below with your own
        private PieceProxy _piece;
        private Timer _timer;
        private Random _generator;
    
        private KeyUpListener _upKey;
        private KeyDownListener _downKey;
        private KeyLeftListener _leftKey;
        private KeyRightListener _rightKey;
        private KeyPListener _pauseKey;
        private KeySpaceListener _spaceKey;
        private ArrayList<ArrayList<SmartRectangle>> _rectanglesArray;
    
        /**
         * Constructor for objects of class GamePanel
         */
        public GamePanel()
        {
            // initialise instance variables
            this.setBackground(Color.BLACK);
            this.setSize(new Dimension(TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_WIDTH), TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_HEIGHT)+15));
            this.setPreferredSize(new Dimension(TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_WIDTH), TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_HEIGHT)+15));
            _upKey = new KeyUpListener(this);
            _downKey = new KeyDownListener(this);
            _leftKey = new KeyLeftListener(this);
            _rightKey = new KeyRightListener(this);
            _pauseKey = new KeyPListener(this);
            _spaceKey = new KeySpaceListener(this);
            _rectanglesArray = new ArrayList<ArrayList<SmartRectangle>>();
            for(int i = 0; i  < TetrisConstants.BOARD_HEIGHT;i++){
                _rectanglesArray.add(new ArrayList<SmartRectangle>());
            }
            _generator = new Random();
           
            _piece = new PieceProxy();
            _piece.setPiece(tetriminoFactory());
    
            _timer = new Timer(500, this);
            _timer.start();
        }
    
        public Tetrimino tetriminoFactory()
        /**
         * This method implements the factory method design pattern to build new tetriminos during Tetris game play.
         */
        {
            Tetrimino newPiece;
            int randomNumber;
    
            int x = (TetrisConstants.BOARD_WIDTH/2) * TetrisConstants.BLOCK_SIZE;
            int y = 0;
            randomNumber = (int) (Math.floor(Math.random()*7)+1);
            switch(randomNumber) {
                case 1: newPiece = new Z(x,y);     break;
                case 2: newPiece = new S(x,y);     break;
                case 3: newPiece = new L(x,y);     break;
                case 4: newPiece = new J(x,y);     break;
                case 5: newPiece = new O(x,y);     break;
                case 6: newPiece = new I(x,y);     break;
                default: newPiece = new T(x,y);     break;
            }
            return newPiece;
        }
    
        public void paintComponent (java.awt.Graphics aBrush)
        {
            super.paintComponent(aBrush);
            java.awt.Graphics2D betterBrush = (java.awt.Graphics2D)aBrush;
            _piece.fill(betterBrush);
            _piece.draw(betterBrush);
            for(int i = 0; i < _rectanglesArray.size();i++){
                for(int j =0; j < _rectanglesArray.get(i).size();j++){
                    _rectanglesArray.get(i).get(j).fill(betterBrush);
                    _rectanglesArray.get(i).get(j).draw(betterBrush);
                }
            }
        }
        /**
         * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
         * tetrimino wishes to move. This can be prevented by either the cell being off of the game board (not a valid cell) or by the
         * cell being occupied by another SmartRectangle.
         *
         * @param c The column of the cell in question on the game board.
         * @param r The row of the cell in question on the game board.
         * @return boolean This function returns whether the component rectangle can be moved into this cell.
         */
        public boolean canMove(int c, int r)
        {
            System.out.println("X is " + c);
            System.out.println("Y is" + r);
            return isFree(c,r) && isValid(c,r);
        }
    
        /**
         * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
         * tetrimino wishes to move. This method returns a boolean indicating whether the cell on the game board is empty.
         *
         * @param c The column of the cell in question on the game board.
         * @param r The row of the cell in question on the game board.
         * @return boolean This function returns whether the cell on the game board is free.
         */
        private boolean isFree(int c, int r)
        
        {
            for(int i = 0;i < _rectanglesArray.size();i++){
                for(int j = 0; j < _rectanglesArray.get(i).size();j++){
                    if(_rectanglesArray.get(i).get(j).getX() == c  && _rectanglesArray.get(i).get(j).getY() == r){
                        return false;
                    }
                }
            }
            return true;
        }
        /**
         * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
         * tetrimino wishes to move. This function checks to see if the cell at (c, r) is a valid location on the game board.
         *
         * @param c The column of the cell in question on the game board.
         * @param r The row of the cell in question on the game board.
         * @return boolean This function returns whether the location (c, r) is within the bounds of the game board.
         */
        private boolean isValid(int i, int j)
        {
            int boardWidth = TetrisConstants.BLOCK_SIZE * (TetrisConstants.BOARD_WIDTH - 1);
            int boardHeight = TetrisConstants.BLOCK_SIZE * (TetrisConstants.BOARD_HEIGHT- 1);
            return (i >= 0 && i <= boardWidth && j >= 0 && j < boardHeight);
    }
     /**
     * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
     * tetrimino wishes to move. This can be prevented by either the cell being off of the game board (not a valid cell) or by the
     * cell being occupied by another SmartRectangle.
     *
     * @param r The SmartRectangle to add to the game board.
     *
     */
    public void addToBoard(SmartRectangle r)

    {
        int index = (int) r.getY() / TetrisConstants.BLOCK_SIZE - 1;
        System.out.println("Index is " + index);
        _rectanglesArray.get(index).add(r);
    }
    /**
     * This method takes one integer representing the row of cells on the game board to move down on the screen after a full
     * row of squares has been removed.
     *
     * @param row The row in question on the game board.
     * @return Nothing
     */
    private void moveBlocksDown(int row)
    {
            
            for(int i = 0; i < row;i++){
                for(int j = 0; j < _rectanglesArray.get(i).size();j++){
                    int newY = (int) _rectanglesArray.get(i).get(j).getY() + TetrisConstants.BLOCK_SIZE;
                    int newX = (int) _rectanglesArray.get(i).get(j).getX();
                    _rectanglesArray.get(i).get(j).setLocation(newX,newY);
                }
            }
        }
    
    /**
     * This method checks each row of the game board to see if it is full of rectangles and should be removed. It calls
     * moveBlocksDown to adjust the game board after the removal of a row.
     *
     * @return Nothing
     */
    private void checkRows(){
        ArrayList<Integer> toBeRemoved = new ArrayList<>();
        for(int row = 0;row < _rectanglesArray.size();row++){
             if(_rectanglesArray.get(row).size() == TetrisConstants.BOARD_WIDTH){
                _rectanglesArray.remove(row);
                System.out.println("called");
                moveBlocksDown(row);
                _rectanglesArray.add(0,new ArrayList<SmartRectangle>());
             }   
             repaint();
        }       
    }
    /**
     * This method checks to see if the game has ended.
     *
     * @return boolean This function returns whether the game is over or not.
     */
    private boolean checkEndGame()
    {
       return false;
    }
    public int getNewX(SmartRectangle myRec, String direction){
        int oldX = (int) myRec.getX();
        switch(direction){
           case "DOWN":
            return oldX;
           case "LEFT":
            return oldX - TetrisConstants.BLOCK_SIZE;
           case "RIGHT":
            return oldX + TetrisConstants.BLOCK_SIZE;
           default:
            return oldX;
        }
    }
    public int getNewY(SmartRectangle myRec, String direction){
        int oldY = (int) myRec.getY();
        switch(direction){
           case "DOWN":
            return oldY + TetrisConstants.BLOCK_SIZE;
           default:
            return oldY;
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        SmartRectangle _blockOne = _piece.getBlockOne();
        SmartRectangle _blockTwo = _piece.getBlockTwo();
        SmartRectangle _blockThree = _piece.getBlockThree();
        SmartRectangle _blockFour= _piece.getBlockFour();
        String direction = "DOWN";
        
        if(canMove(getNewX(_blockOne,direction),getNewY(_blockOne,direction)) 
           && canMove(getNewX(_blockTwo,direction),getNewY(_blockTwo,direction))
           && canMove(getNewX(_blockThree,direction),getNewY(_blockThree,direction))  
           && canMove(getNewX(_blockFour,direction),getNewY(_blockFour,direction))){
           _piece.moveDown();  
            repaint();  
        }else{
           addToBoard(_blockOne);
           addToBoard(_blockTwo);
           addToBoard(_blockThree);
           addToBoard(_blockFour);
           checkRows();
           _generator = new Random();
           _piece.setPiece(tetriminoFactory());
           
           repaint();
        
        }
        
        
        
    }
    private class KeyUpListener extends KeyInteractor
    {
        public KeyUpListener(JPanel p)
        {
            super(p,KeyEvent.VK_UP);
        }

        public  void actionPerformed (ActionEvent e) {
            SmartRectangle _blockOne = _piece.getBlockOne();
            SmartRectangle _blockTwo = _piece.getBlockTwo();
            SmartRectangle _blockThree = _piece.getBlockThree();
            SmartRectangle _blockFour= _piece.getBlockFour();
            int newXOne = (int)_blockOne.getX();
            int newYOne = (int)_blockOne.getY();
            int newXTwo = (int)(newXOne - newYOne + _blockTwo.getY());
            int newYTwo = (int)(newYOne + newXOne - _blockTwo.getX());
            int newXThree = (int)(newXOne - newYOne + _blockThree.getY());
            int newYThree = (int)(newYOne + newXOne - _blockThree.getX());
            int newXFour = (int)(newXOne - newYOne + _blockFour.getY());
            int newYFour = (int)(newYOne + newXOne - _blockFour.getX());
            if(canMove(newXOne,newYOne) && canMove(newXTwo,newYTwo)
             && canMove(newXThree,newYThree) && canMove(newXFour,newYFour)
             && _timer.isRunning()){
                 _piece.turnRight();
                 repaint();
            }
     
        }
    }
    private class KeyDownListener extends KeyInteractor
    {
        public KeyDownListener(JPanel p)
        {
            super(p,KeyEvent.VK_DOWN);
        }

        public  void actionPerformed (ActionEvent e) {
            SmartRectangle _blockOne = _piece.getBlockOne();
            SmartRectangle _blockTwo = _piece.getBlockTwo();
            SmartRectangle _blockThree = _piece.getBlockThree();
            SmartRectangle _blockFour= _piece.getBlockFour();
            String direction = "DOWN";
        
        if(canMove(getNewX(_blockOne,direction),getNewY(_blockOne,direction)) 
           && canMove(getNewX(_blockTwo,direction),getNewY(_blockTwo,direction))
           && canMove(getNewX(_blockThree,direction),getNewY(_blockThree,direction))  
           && canMove(getNewX(_blockFour,direction),getNewY(_blockFour,direction))
           && _timer.isRunning()){
           _piece.moveDown();  
            repaint();  
        }
        }
    }
    private class KeyLeftListener extends KeyInteractor
    {
        public KeyLeftListener(JPanel p)
        {
            super(p,KeyEvent.VK_LEFT);
        }

        public  void actionPerformed (ActionEvent e) {
            SmartRectangle _blockOne = _piece.getBlockOne();
            SmartRectangle _blockTwo = _piece.getBlockTwo();
            SmartRectangle _blockThree = _piece.getBlockThree();
            SmartRectangle _blockFour= _piece.getBlockFour();
            String direction = "LEFT";
            
        
        if(canMove(getNewX(_blockOne,direction),getNewY(_blockOne,direction)) 
           && canMove(getNewX(_blockTwo,direction),getNewY(_blockTwo,direction))
           && canMove(getNewX(_blockThree,direction),getNewY(_blockThree,direction))  
           && canMove(getNewX(_blockFour,direction),getNewY(_blockFour,direction))
           && _timer.isRunning()){
           _piece.moveLeft();  
            repaint();  
        }

        }
    }
    private class KeyRightListener extends KeyInteractor
    {
        public KeyRightListener(JPanel p)
        {
            super(p,KeyEvent.VK_RIGHT);
        }

        public  void actionPerformed (ActionEvent e) {
            SmartRectangle _blockOne = _piece.getBlockOne();
            SmartRectangle _blockTwo = _piece.getBlockTwo();
            SmartRectangle _blockThree = _piece.getBlockThree();
            SmartRectangle _blockFour= _piece.getBlockFour();
            String direction = "RIGHT";
        
        if(canMove(getNewX(_blockOne,direction),getNewY(_blockOne,direction)) 
           && canMove(getNewX(_blockTwo,direction),getNewY(_blockTwo,direction))
           && canMove(getNewX(_blockThree,direction),getNewY(_blockThree,direction))  
           && canMove(getNewX(_blockFour,direction),getNewY(_blockFour,direction))
           && _timer.isRunning()){
               _piece.moveRight();  
                repaint();  
        }
        }
    }
    private class KeyPListener extends KeyInteractor
    {
        public KeyPListener(JPanel p)
        {
            super(p,KeyEvent.VK_P);
        }

        public  void actionPerformed (ActionEvent e) {
            if(_timer.isRunning()){
                _timer.stop();
            }
            else
                _timer.start();
        }
    }
        private class KeySpaceListener extends KeyInteractor
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }

        public  void actionPerformed (ActionEvent e) {
            SmartRectangle _blockOne = _piece.getBlockOne();
            SmartRectangle _blockTwo = _piece.getBlockTwo();
            SmartRectangle _blockThree = _piece.getBlockThree();
            SmartRectangle _blockFour= _piece.getBlockFour();
            String direction = "DOWN";
            if(_timer.isRunning()){
                while(canMove(getNewX(_blockOne,direction),getNewY(_blockOne,direction)) 
           && canMove(getNewX(_blockTwo,direction),getNewY(_blockTwo,direction))
           && canMove(getNewX(_blockThree,direction),getNewY(_blockThree,direction))  
           && canMove(getNewX(_blockFour,direction),getNewY(_blockFour,direction))){
               _piece.moveDown();
            }
        }
        repaint();
    }
}
}
