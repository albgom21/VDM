package com.example.libenginea;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gdv.ucm.libengine.IRead;
import gdv.ucm.liblogica.Cell;
import gdv.ucm.liblogica.CellState;

public class ReadA implements IRead {
    AssetManager mgr;
    ReadA(AssetManager mgr)
    {
        this.mgr = mgr;
    }

    @Override
    public int[][] newBoard(String file) {
        int[][] board = null;
        try {
            InputStream inputStream = this.mgr.open(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //Leemos char y saltamos espacio
                char width = (char)bufferedReader.read(); //cols
                bufferedReader.read();
                char height = (char)bufferedReader.read(); //fils
                bufferedReader.read();
                bufferedReader.read();

                int w=Integer.parseInt(String.valueOf(width));
                int h=Integer.parseInt(String.valueOf(height));
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
