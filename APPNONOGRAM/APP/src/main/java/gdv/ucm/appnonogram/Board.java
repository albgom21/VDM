package gdv.ucm.appnonogram;


import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.InputA;
import com.example.libenginea.ReadA;

public class Board implements InterfaceA {
   // Atributos
   private int width, height;
   private Cell[][] board;
   ReadA read;

   public Board(String filename, ReadA read) //filename = "name.txt"
   {
      this.read = read;
      int[][] a = this.read.newBoard(filename);
      this.width = a.length;
      this.height = a[0].length;
      this.board = new Cell[this.width][this.height];

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            if (a[i][j] == 0) {
               board[i][j] = new Cell(i, j, width, height, false, CellState.GRAY);
            } else if (a[i][j] == 1) {
               board[i][j] = new Cell(i, j, width, height, true, CellState.GRAY);
            }
         }
      }
   }

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
   public boolean handleEvent(InputA.Event e) {
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            if(board[i][j].handleEvent(e))
               return true;
         }
      }
      return false;
   }

   // Getters
   public int getWidth() {
      return width;
   }
   public int getHeight() {
      return height;
   }
   public Cell getCell(int x, int y) { return board[x][y]; }


   @Override
   public void render(GraphicsA g){ }

   @Override
   public void update(Double deltaTime) {}
}