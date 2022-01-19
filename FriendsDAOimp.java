/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

    public class FriendsDAOimp implements FriendsDAO {
    private Friends crrUsr = new Friends();
    private TreeMap<String, Friends> friendsMap = new TreeMap<String, Friends>();
    private ChatDAOimp currentUsrDao = new ChatDAOimp();

        /**
         * Constructor
         */
    FriendsDAOimp(){ }

        /**
         *Prints friends list. not currently used.
         */
    public void printList()
    {
        Iterator<Map.Entry<String, Friends>> iterator = friendsMap.entrySet().iterator();
        Map.Entry<String, Friends> entry = null;
        while (iterator.hasNext()) {
            entry = iterator.next();
            System.out.println(entry.getValue().toStringWrite());
        }
    }

        /**
         *Chooses the log file matching the friend in the list and loads it to the friend objects chatdao variable
         */
    public void addLogToFriends() {
        TreeMap <String, Friends> friends = friendsMap;
        for(Map.Entry<String, Friends> entry : friends.entrySet()) {
           ChatDAOimp chat = new ChatDAOimp();
            Friends friend = entry.getValue();
            chat.chooseLog(friendsMap, friend.getNickname());
            friend.setChatDao(chat);
        }

    }
        /**
         * Returns treemap containing all friend objects
         */
        public TreeMap<String, Friends> getFriends() {
        return friendsMap;
    }

        public void setFriends(TreeMap<String, Friends> newFriendsMap) {

            friendsMap = newFriendsMap;

        }

        /**
         * Gets the friend object that matches the provided string
         */
    public Friends getFriend(String s)
    {

        return friendsMap.get(s);
    }

        /**
         * Updates friend object
         */
    public void changeFriend(String key, String val) {

        WriteList update = new WriteList(friendsMap, key, val);

    }

        /**
         * Adds the log file matching the current user.
         */
    public void addLogToCurrentUser()
        {
            currentUsrDao.chooseLog(friendsMap, getCurrentUser().getNickname());
        }

        /**
         * Returns a current user object.
         */

    public Friends getCurrentUser()
    {
            crrUsr.setNickname("EURA");
            crrUsr.setFullname("Niklas van Herbert");
            crrUsr.setLastip("192.168.1.1");
            crrUsr.setImage("No Picture");
            crrUsr.setChatDao(currentUsrDao);
            return  crrUsr;
    }

    }
