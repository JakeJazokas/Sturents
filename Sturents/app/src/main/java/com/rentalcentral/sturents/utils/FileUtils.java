package com.rentalcentral.sturents.utils;

import android.content.Context;

import com.rentalcentral.sturents.model.SavedListingArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils {

    //Write data to cache file / create cache file
    public static File createCacheFile(Context context, String fileName, String data, boolean appendValue) {
        File cacheFile = new File(context.getFilesDir(), fileName);
        try {
            FileWriter fw = new FileWriter(cacheFile, appendValue);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            cacheFile = null;
        }

        return cacheFile;
    }

    //Read saved data from file
    public static String readFile(File file) {
        String fileContent = "";
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((currentLine = br.readLine()) != null) {
                fileContent += currentLine + '\n';
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            fileContent = null;
        }
        return fileContent;
    }


    //Writes a serializable object as binary to a file
    public static void writeSerializableListingArray(Context context, SavedListingArray listingArray, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(),"")+File.separator + fileName));
            outputStream.writeObject(listingArray);
            outputStream.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Reads a serializable object from a file
    public static SavedListingArray readSerializableListingArray(Context context, String fileName) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(new File(context.getFilesDir(),"")+File.separator + fileName)));
            SavedListingArray myListingsObject = (SavedListingArray) input.readObject();
            input.close();
            return myListingsObject;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
