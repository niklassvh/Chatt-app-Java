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
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class  Logs extends FriendsDAOimp {
    String filename;
    private ArrayList<Message> msglist = new ArrayList<>();

    String workingDirectory = System.getProperty("user.dir");
    String separator = System.getProperty("file.separator");
    String fullPath = workingDirectory + separator+"Src"+ separator + "Labb3" + separator + "logs" + separator;

    /**
     * Constructor. Reads the file into the Arraylist.
     * @param s
     */

    Logs(TreeMap<String, Friends> friends, String s){

        try {
            File path = new File(fullPath);

            if(!path.exists())
            {
                path.mkdirs();
            }
            File privlog = new File( fullPath + s + ".log");
            if (!privlog.exists())
            {
                privlog.createNewFile();
            }
            Scanner reader = new Scanner(privlog);

            while (reader.hasNextLine()) {
                Message lineOfLog = new Message();
                String text = reader.nextLine();

                text = text.replaceAll("<", "");
                text = text.replaceAll(">", ": ");
                text = text.replace("]", "");
                text = text.replace("[", "");
                String tagname = text.substring(text.indexOf(0) + 1, text.indexOf(":"));
                String msg = text.substring(text.indexOf(":") +1);

                Friends auth = new Friends();
                auth.setNickname(tagname);

                lineOfLog.setAuthor(auth);

                    lineOfLog.setMessage(msg);
                    msglist.add(lineOfLog);
                }
                reader.close();

            }
        catch(FileNotFoundException e) {
            System.out.println("No chat history found.");
            File createFile = new File(fullPath + s + ".log");
            new SystemExceptionHandler().fileNotFoundException(e.toString(), s);
            }

        catch (IOException io) {
            System.out.println(fullPath);
            new SystemExceptionHandler().ioException(io.toString());
        }


            }

    /**
     * Returns the list containing the chatlogs.
     */
    public ArrayList<Message> getLogList() {

        return this.msglist;

    }


}