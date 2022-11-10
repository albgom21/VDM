package gdv.ucm.liblogica;

import gdv.ucm.libengine.IGraphics;
import gdv.ucm.libengine.IInput;
import gdv.ucm.libengine.IInterface;

public class Board implements IInterface {
   // Atributos
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

   public Board(int w, int h) {
      width = w;
      height = h;
      int cont = 0;
      // Inicialización del tablero
      board = new Cell [width][height];
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            int valorEntero = (int)Math.floor(Math.random()*(2)); // Poner de forma aleatoria si es o no sol
            if(valorEntero==0)
               board[i][j] = new Cell(i,j,width, height,false, CellState.GRAY);
            else{
               board[i][j] = new Cell(i,j,width, height,true, CellState.GRAY);
               cont++;
            }

         }
      }
      if(cont == 0 || cont == w*h){ //Por si no hay ninguna celda sol, o son todas
         int i = (int)Math.floor(Math.random()*(width));
         int j = (int)Math.floor(Math.random()*(height));
         board[i][j] = new Cell(i,j,width, height,cont == 0, CellState.GRAY);
      }
   }

   @Override
   public boolean handleEvent(IInput.Event e) {
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            if(board[i][j].handleEvent(e))
               return true;
         }
      }
      return false;
   }
   @Override
   public void render(IGraphics g){ }

   @Override
   public void update(Double deltaTime) { }
}