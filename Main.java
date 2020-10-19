package Serialization.homework3;

import Serialization.homework2.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String zipPath = "/Users/maksim/Games/savegames/saves.zip";
        String targetPath = "/Users/maksim/Games/savegames";

        openZip(zipPath, targetPath);

        try {
            GameProgress gameProgress1 = openProgress("/Users/maksim/Games/savegames/packedTest1.txt");
            System.out.println(gameProgress1.toString());
            GameProgress gameProgress2 = openProgress("/Users/maksim/Games/savegames/packedTest2.txt");
            System.out.println(gameProgress2.toString());
            GameProgress gameProgress3 = openProgress("/Users/maksim/Games/savegames/packedTest3.txt");
            System.out.println(gameProgress3.toString());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static GameProgress openProgress(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameProgress gameProgress = (GameProgress) ois.readObject();
        fis.close();
        ois.close();
        return gameProgress;
    }

    private static void openZip(String zipPath, String targetPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))){
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                String fullFilePath = zipEntry.getName();
                File file = new File(fullFilePath);
                String fileName = "";
                if (!file.isDirectory()) {
                    fileName = file.getName();
                }
                FileOutputStream fos = new FileOutputStream(targetPath + File.separator + fileName);
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fos.write(i);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
