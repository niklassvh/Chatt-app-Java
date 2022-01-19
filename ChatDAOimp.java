/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

import java.util.ArrayList;
import java.util.TreeMap;
//
public class ChatDAOimp implements ChatDAO{
    private ArrayList<Message> chatmsg;

    /**
     * Determines what chatlog to get based on if the provided string matches and object in the treemap.
     */
    public void chooseLog(TreeMap<String, Friends> friends, String s) {
        Logs logreader = new Logs(friends, s);
        chatmsg = logreader.getLogList();
    }

    /**
     * adds message to the logfile
     */
    public void addMessage(Message msg, String s){
        chatmsg.add(msg);
        WriteLogs updatelog = new WriteLogs(msg, s);
    }
    /**
     * Sets the massage. From: user, b: message, target: person you're chatting with's log.
     * Passes it to addMessage
     */
    public void setMessage (Friends usr, String b , String target)
    {
        Message addInput = new Message();
        addInput.setAuthor(usr);
        addInput.setMessage(b);
        addMessage(addInput, target);
    }

    public ArrayList<Message> getMessages(){
        return chatmsg;
    }
}
