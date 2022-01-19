/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 3
2021-03-08
 */

package Labb3;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public interface ChatDAO {

    public void chooseLog(TreeMap<String, Friends> friends, String s);
    public void addMessage(Message msg, String s);
    public void setMessage (Friends usr, String a, String target);
    public ArrayList<Message> getMessages();

}
