/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class WriteLogs {

    String workingDirectory = System.getProperty("user.dir");
    String separator = System.getProperty("file.separator");
    String fullPath = workingDirectory + separator+"Src"+ separator + "Labb3" + separator + "logs" + separator;

    public WriteLogs(Message msg, String s) {



        File file = new File (fullPath);
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            File path = new File (fullPath + s + ".log");

            FileWriter writer = new FileWriter(path, true);
            writer.write("<" + msg.getAuthor().getNickname() + ">" + msg.getMessage());
            writer.write("\n");
            writer.close();


        } catch (IOException e) {
            new SystemExceptionHandler().ioException(e.toString());
        }

    }
}
