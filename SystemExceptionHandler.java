/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */
package Labb3;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SystemExceptionHandler {
// Lets user reconnect if socket connection would break.
String workingDirectory = System.getProperty("user.dir");
String separator = System.getProperty("file.separator");
String fullPath = workingDirectory + separator+"Src"+ separator + "Labb3" + separator + "logs" + separator;

    public void socketOfflineException(String errorInformation, Client t){

        int errorChoice = JOptionPane.showConfirmDialog(null, "Server offline\n Reconnect?", "Error!", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (errorChoice == JOptionPane.YES_OPTION) {
            writeExceptionToLog(errorInformation);
            JOptionPane.showMessageDialog(null, "Trying to reconnect", "Error!", JOptionPane.INFORMATION_MESSAGE);
           t.connect(t);

        }
        else if (errorChoice == JOptionPane.NO_OPTION) {
            writeExceptionToLog(errorInformation);
            JOptionPane.showMessageDialog(null, "Closing program", "You choose not to reconnect", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

    }

    public void unknownHostException(String errorInformation){
        writeExceptionToLog(errorInformation);
        JOptionPane.showMessageDialog(null,"Unknown host", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    public void ioException(String errorInformation){
        writeExceptionToLog(errorInformation);
        JOptionPane.showMessageDialog(null,"Problem reading or writing to user files", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void fileNotFoundException(String errorInformation, String target){
        writeExceptionToLog(errorInformation);
        JOptionPane.showMessageDialog(null,"File not found for " + target + ". Creating a new one", "Error Loading file", JOptionPane.ERROR_MESSAGE);
    }
    public void threadInterupptedException(String errorInformation){
        writeExceptionToLog(errorInformation);
        JOptionPane.showMessageDialog(null,"A thread has been interrupted. Closing Program", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    // Writes all the errorinformation to a file.
    public void writeExceptionToLog(String error){
        //File logFile = new File(System.getProperty("user.dir") + "/src/Labb3/logs/system.log");
        LocalDateTime logTimer = LocalDateTime.now();
        try {
            File logFile = new File (fullPath);
            if(!logFile.exists())
            {
                logFile.mkdirs();
            }
            FileWriter writer = new FileWriter(fullPath + "system.log",true);
            writer.write(logTimer + ": " + "Cause: " + error + "\n");
            writer.close();
        }

        catch (IOException ex) {
            ioException(ex.toString());
        }

    }


}
