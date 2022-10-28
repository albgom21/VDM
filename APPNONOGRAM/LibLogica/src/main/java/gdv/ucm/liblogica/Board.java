package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInterface;

public class Board implements IInterface {
   // Attributes
   private int width, height;
   private Cell [] [] board;

   // Getters
   public int getWidth() {
      return width;
   }
   public int getHeight() {
      return height;
   }
   public Cell getCell(int x, int y) { return board[x][y]; }

   // Setters
   public void setHeight(int height) {
      this.height = height;
   }
   public void setWidth(int width) {
      this.width = width;
   }

   public Board(int w, int h) {
      width = w;
      height = h;

      // Initialization of the board
      board = new Cell [width][height];
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            int valorEntero = (int)Math.floor(Math.random()*(1-0+1)+0);  // Valor entre M y N, ambos incluidos.
            if(valorEntero==0)
               board[i][j] = new Cell(i,j,false, CellState.GRAY);
            else
               board[i][j] = new Cell(i,j,true, CellState.RED);

         }
      }
   }
   
   @Override
   public void render(IGraphics g){
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            board[i][j].render(g);
         }
      }
   }

   @Override
   public void update(Double deltaTime) {

   }
   //HACER CARGA DE RECURSOS EN EL MÓDULO DE LÓGICAS
}