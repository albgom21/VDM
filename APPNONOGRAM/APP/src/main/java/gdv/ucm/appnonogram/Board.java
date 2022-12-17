package gdv.ucm.appnonogram;

import com.example.libenginea.EngineA;
import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.InputA;
import com.example.libenginea.ReadA;

public class Board implements InterfaceA {
   // Atributos
   private int width, height;
   private Cell[][] board;
   ReadA read;

   public EngineA getEngine() {
      return engine;
   }

   private EngineA engine; //Preguntar si pasar engine a board -> cell (para sonidos e imagenes)
   private int lives;

   public Board(String filename, ReadA read, EngineA engine) //filename = "name.txt"
   {
      this.read = read;
      this.engine = engine;
      this.lives = 3;
      int[][] a = this.read.newBoard(filename);
      this.width = a.length;
      this.height = a[0].length;
      this.board = new Cell[this.width][this.height];

      for (int i = 0; i < width; i++) {
         for (int j = 0; j < height; j++) {
            if (a[i][j] == 0) {
               board[i][j] = new Cell(i, j, width, height, false, CellState.GRAY, this.engine);
            } else if (a[i][j] == 1) {
               board[i][j] = new Cell(i, j, width, height, true, CellState.GRAY, this.engine);
            }
         }
      }
   }

   public Board(int w, int h, EngineA engine) {
      width = w;
      height = h;
      this.engine = engine;
      this.lives = 3;
      int cont = 0;
      // Inicialización del tablero
      board = new Cell [width][height];
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            int valorEntero = (int)Math.floor(Math.random()*(2)); // Poner de forma aleatoria si es o no sol
            if(valorEntero==0)
               board[i][j] = new Cell(i,j,width, height,false, CellState.GRAY, this.engine);
            else{
               board[i][j] = new Cell(i,j,width, height,true, CellState.GRAY, this.engine);
               cont++;
            }

         }
      }
      if(cont == 0 || cont == w*h){ //Por si no hay ninguna celda sol, o son todas
         int i = (int)Math.floor(Math.random()*(width));
         int j = (int)Math.floor(Math.random()*(height));
         board[i][j] = new Cell(i,j,width, height,cont == 0, CellState.GRAY, this.engine);
      }
   }

   @Override
   public boolean handleEvent(InputA.Event e) {
      for (int i = 0; i < width; ++i) {
         for (int j = 0; j < height; ++j) {
            if(board[i][j].handleEvent(e)) {
               if (board[i][j].loseLife())
                  lives--;
               return true;
            }
         }
      }
      return false;
   }

   // Getters
   public int getWidth() { return width; }
   public int getHeight() { return height; }
   public int getLives() { return lives; }
   public void addLife() { lives++; }
   public Cell getCell(int x, int y) { return board[x][y]; }

   @Override
   public void render(GraphicsA g){ }

   @Override
   public void update(Double deltaTime) {}
}