import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 10, 12, 1.1);
        GameProgress game2 = new GameProgress(111, 1110, 111, 11.11);
        GameProgress game3 = new GameProgress(222, 1220, 1221, 12.21);

        File file1 = new File("Games/savegames/save1.dat");
        File file2 = new File("Games/savegames/save2.dat");
        File file3 = new File("Games/savegames/save3.dat");

        saveGame(file1.getAbsolutePath(), game1);
        saveGame(file2.getAbsolutePath(), game2);
        saveGame(file3.getAbsolutePath(), game3);

        List<String> listToFile = new LinkedList<>();
        listToFile.add(file1.getAbsolutePath());
        listToFile.add(file2.getAbsolutePath());
        listToFile.add(file3.getAbsolutePath());

        File zip = new File("Games/savegames/zip.zip");
        zipFiles(zip.getAbsolutePath(), listToFile);
    }

    public static void saveGame(String pathToFile, GameProgress game) {

        try (FileOutputStream outputStream = new FileOutputStream(pathToFile)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(game);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public static void zipFiles(String pathToZip, List<String> listToFile) {
        Iterator<String> iter = listToFile.iterator();

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(pathToZip))) {
            while (iter.hasNext()) {
                File file = new File(iter.next());
                FileInputStream fileInputStream = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zip.putNextEntry(zipEntry);

                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);

                zip.write(buffer);
                zip.closeEntry();

                file.delete();
            }
        } catch (Exception e) {
        }
    }
}
