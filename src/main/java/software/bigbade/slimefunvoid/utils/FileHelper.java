package software.bigbade.slimefunvoid.utils;

import software.bigbade.slimefunvoid.SlimefunVoid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.logging.Level;

public class FileHelper {
    //Private constructor to hide implicit public one
    private FileHelper() {}

    public static void createFile(File file) {
        if(!file.exists()) {
            try {
                if(!file.createNewFile())
                    throw new IOException();
            } catch (IOException e) {
                SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not create file", e);
            }
        }
    }

    public static void copyFile(InputStream inputStream, File output) {
        FileOutputStream outputStream = getOutputStream(output);
        Objects.requireNonNull(outputStream);
        byte[] reading;
        int available = getAvailable(inputStream);
        while(available > 0) {
            reading = new byte[Math.min(available, 4096)];
            readBytes(inputStream, reading);
            writeBytes(outputStream, reading);
            available = getAvailable(inputStream);
        }
    }

    private static void writeBytes(OutputStream stream, byte[] output) {
        try {
            stream.write(output);
        } catch (IOException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not read from file file", e);
        }
    }

    private static void readBytes(InputStream stream, byte[] output) {
        try {
            if(stream.read(output) != output.length)
                throw new IOException();
        } catch (IOException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not read from file file", e);
        }
    }

    private static int getAvailable(InputStream stream) {
        try {
            return stream.available();
        } catch (IOException e) {
            return 0;
        }
    }

    private static FileInputStream getInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not create input stream", e);
        }
        return null;
    }

    private static FileOutputStream getOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            SlimefunVoid.getPluginLogger().log(Level.SEVERE, "Could not create output stream", e);
        }
        return null;
    }
}
