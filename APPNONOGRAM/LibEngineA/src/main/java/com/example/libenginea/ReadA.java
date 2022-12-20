package com.example.libenginea;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadA {
    AssetManager mgr;
    ReadA(AssetManager mgr)
    {
        this.mgr = mgr;
    }

    public int[][] newBoard(String file) {      //Crea tablero a partir de lectura de txt
        int[][] board = null;
        try {
            InputStream inputStream = this.mgr.open(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Leemos char y saltamos espacio
                char width = (char)bufferedReader.read(); //cols
                char width2 = (char)bufferedReader.read();
                int d = Integer.parseInt(String.valueOf(width));

                if(width2 != ' ')
                {
                    bufferedReader.read();
                    int u = Integer.parseInt(String.valueOf(width2));
                    d *= 10;
                    d += u;
                }

                char height = (char)bufferedReader.read(); //fils
                char height2 = (char)bufferedReader.read();
                int d2 = Integer.parseInt(String.valueOf(height));

                if(height2 != '\r') {
                    bufferedReader.read();
                    int u2 = Integer.parseInt(String.valueOf(height2));
                    d2 *= 10;
                    d2 += u2;
                }
                bufferedReader.read();

                int w = d;
                int h = d2;
                board = new int[w][h];
                int i = 0;
                int j = 0;
                char c = (char)bufferedReader.read();
                int num;
                while (c != '-') { //Acabamos fichero con un menos(-)
                    while(c!='0' && c != '1')
                    {
                        c = (char)bufferedReader.read();
                    }

                    num = Integer.parseInt(String.valueOf(c));

                    board[i][j] = num;

                    i++;
                    if(i == w) {
                        j++;
                        i = 0;
                    }

                    c = (char)bufferedReader.read();
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return board;
    }
}
