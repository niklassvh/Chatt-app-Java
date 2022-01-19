/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
/**
 * Updates a friend.
 */
public class WriteList {
    /** In use only to show that changing fullname works from labb 3.
     * Dont know why i should have it since the friendslist comes from the server and not from a file like in Lab 3.
     * So i guess it essentially have no use except if i would decide to print out a friends object after the change
     */
    String workingDirectory = System.getProperty("user.dir");
    String separator = System.getProperty("file.separator");
    String fullPath = workingDirectory + separator+"Src"+ separator + "Labb3" + separator + "logs" + separator;

    String log = System.getProperty("user.dir")+"/src/Labb3/logs/friends.list";

        public WriteList(TreeMap<String, Friends> map, String key, String value ) {

                Iterator<Map.Entry<String, Friends>> iterator = map.entrySet().iterator();
                Map.Entry<String, Friends> entry = null;

            try {
                FileWriter writer = new FileWriter(fullPath + "friends.list");

                if (map.containsKey(key))
                {
                    Friends updatefriend = map.get(key);
                    updatefriend.setFullname(value);

                    while (iterator.hasNext()) {
                        entry = iterator.next();
                        String add = entry.getValue().toStringWrite();
                        writer.append(add);

                    }
                    writer.close();
                }
            }
            catch(IOException e)
            {
                new SystemExceptionHandler().ioException(e.toString());
            }

        }

}
