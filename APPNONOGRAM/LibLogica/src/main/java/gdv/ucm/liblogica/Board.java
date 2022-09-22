package gdv.ucm.liblogica;

public class Board {
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
            board[i][j] = new Cell(i,j);
         }
      }
   }

   //HACER CARGA DE RECURSOS EN EL MÓDULO DE LÓGICAS
}