/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 3
2021-03-08
 */

package Labb3;

import javax.swing.*;
import java.util.TreeMap;

public interface FriendsDAO {


    public TreeMap<String, Friends> getFriends();
    public void changeFriend(String key, String val);
    public Friends getFriend(String s);
    public Friends getCurrentUser();
    public void addLogToFriends();
    public void printList();
    public void addLogToCurrentUser();
    public void setFriends(TreeMap<String, Friends> newFriendsMap);

}
