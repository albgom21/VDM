package gdv.ucm.appnonogram.LOGIC;

import com.example.libenginea.GraphicsA;
import com.example.libenginea.InterfaceA;
import com.example.libenginea.InputA;

import java.io.Serializable;

public class Board implements InterfaceA, Serializable {
   // Atributos
   private int width, height;
   private Cell[][] board;
   private int lives;


   private int lvl;
   private String type;
   private boolean random;    //para saber si es del modo historia o modo rápido

   public Board(Board b){     //Constructora por copia
      this.width = b.width;
      this.height = b.height;
      this.board = b.board;
      this.lives = b.lives;
      this.random = b.random;
      this.lvl = b.lvl;
      this.type = b.type;
   }

   public Board(int[][] a, int lvl, String type)   //Constructora por nivel y tipo
   {
      this.lvl = lvl;
      this.type = type;
      this.lives = 3;
      this.width = a.length;
      this.height = a[0].length;
      this.board = new Cell[this.width][this.height];
      this.random = false;

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

   public Board(int w, int h) {     //constructora tablero aleatorio
      this.width = w;
      this.height = h;
      this.lvl = 0;
      this.type = "random";
      this.lives = 3;
      this.random = true;
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
            if(board[i][j].handleEvent(e)) {
               if (board[i][j].loseLife())         //perdida de vida
                  lives--;
               return true;
            }
         }
      }
      return false;
   }

   // Getters
   public int getLvl() {return lvl;}
   public String getType() {return type;}
   public int getWidth() { return width; }
   public int getHeight() { return height; }
   public int getLives() { return lives; }
   public boolean getRandom() { return random; }
   public Cell getCell(int x, int y) { return board[x][y]; }


   public void addLife() { lives++; }
   public void setLvl(int lvl) {this.lvl = lvl;}
   public void setType(String type) {this.type = type;}

   @Override
   public void render(GraphicsA g){ }

   @Override
   public void update(Double deltaTime) {}
}