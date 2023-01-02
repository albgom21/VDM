package com.example.libenginea;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization {
    private Context context;

    public Serialization(Context context){
        this.context =  context;
    }

    public void desSerialize(Serializable serializable, String file){
        try {
            // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
            FileInputStream fis = this.context.openFileInput(file);

            // Creamos un ObjectInputStream a partir del FileInputStream
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo Serializable
            serializable = (Serializable) ois.readObject();

            // Cerramos los streams
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void serialize(Serializable serializable, String file){
        try {
            // Creamos un FileOutputStream para escribir en un archivo en el almacenamiento interno de la aplicación
            FileOutputStream fos = this.context.openFileOutput(file, Context.MODE_PRIVATE);

            // Creamos un ObjectOutputStream a partir del FileOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Serializamos el objeto persona y lo escribimos en el archivo
            oos.writeObject(serializable);

            // Cerramos los streams
            oos.close();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
