/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class Mainframe implements ActionListener {

    JFrame frame = new JFrame("Messenger App");
    Menubar Menu = new Menubar();
    Friendframe friends = new Friendframe();
    Chatframe chatbox = new Chatframe();
    FriendsDAO friendDAO = new FriendsDAOimp();
    JMenuItem Exit = Menu.getExit();
    JCheckBoxMenuItem Checkpub = Menu.getOpt2pub();
    JCheckBoxMenuItem Checkpriv = Menu.getOpt2priv();
    JMenuItem editfriend= Menu.getOpt3edit();
    JList<String> list = friends.getList();
    JButton sendbutton = chatbox.getSendbutton();
    Client client = new Client(friendDAO, chatbox,"0.0.0.0", 8000);
    int privateOrPublic = 3;
    String selectedFriend = "";

    /***
     *
     */
    /*
    * Creates the frame that binds all settings, panels, layout option etc. together
    */
    public Mainframe() {
       // friendDAO.addLogToCurrentUser();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(Menu.getMenubar());
        frame.add(friends.getFpan(), BorderLayout.EAST);
        frame.add(chatbox.getCpan(), BorderLayout.CENTER);
        frame.setVisible(true);
        Checkpub.addActionListener(this);
        Checkpriv.addActionListener(this);
        editfriend.addActionListener(this);
        Exit.addActionListener(this);
        sendbutton.addActionListener(this);
        JOptionPane.showMessageDialog(null,"Hi! And welcome to the messenger app.","Welcome!",JOptionPane.ERROR_MESSAGE);

        // Mouselistener. checks the Jlist to see what friend i choose in the friends list.
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent f) {

               if (!list.isSelectionEmpty()) {
                   String name = list.getSelectedValue();

                   if (Checkpriv.isSelected() && name != null) {
                       selectedFriend = name;
                       chatbox.emptyCtext();
                       // to prevent spam clicking the Jlist in privatechat before a friend is loaded into it. Unlikely scenario i guess, but if you're really fast it can cause some problems.
                       if (friendDAO.getFriend(name).chatDAO() == null) {
                         //  selectedFriend = "";
                           JOptionPane.showMessageDialog(null,"Went to private chat without any friends loaded\n Please re-enter private chat. ","Welcome!",JOptionPane.ERROR_MESSAGE);
                       } else {
                           chatbox.setCtext(friendDAO.getFriend(name).chatDAO().getMessages());
                           chatbox.setLabel("Private Chat with " + name);
                       }
                   }
               }
            }
        });

        client.connect(client);
        WriterThread();
    }

    public static void main(String[] args){

        Mainframe Application = new Mainframe();
    }

    /**
     * Thread that updates the friendlist and checks for new friends "at all times" so new messages will show in the chat.
     */
    private void WriterThread () {
        new Thread(new Runnable(){
            public void run(){
                while(true){
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run(){
                            friendDAO.setFriends(client.getFriends());
                            friendListUpdater();
                           if (privateOrPublic == 0)
                            {

                                ArrayList<Message> chatText = friendDAO.getCurrentUser().chatDAO().getMessages();
                                chatbox.emptyCtext();
                                chatbox.setCtext(chatText);
                                }

                            if (privateOrPublic == 1)
                            {

                                if (!selectedFriend.isEmpty()) {

                                        chatbox.emptyCtext();
                                        try {
                                            ArrayList<Message> privateChat = friendDAO.getFriend(selectedFriend).chatDAO().getMessages();
                                            chatbox.setCtext(privateChat);
                                        }
                                        catch(NullPointerException n){
                                            return;
                                        }
                                    }
                            }
                            }
                    });
                    try {
                        Thread.sleep(100);
                    }
                    catch(InterruptedException i) {
                        new SystemExceptionHandler().threadInterupptedException(i.toString());
                    }
                }
            }
        }).start();
    }




    /**
     * Adds listeners for the public and private menu checkboxes and for the Jlist in the friendspanel.
     * To get certain behaviours depending on what and where you click.
     */

    @Override
    public void actionPerformed( ActionEvent e) {

        ArrayList<String> clearchat = new ArrayList<>();
        clearchat.add("");
        //when Public checkbox is clicked
        if (e.getActionCommand() == "Public")
        {
            friendDAO.addLogToCurrentUser();
            privateOrPublic = 0;
            chatbox.emptyCtext();
            Checkpriv.setSelected(false);
            chatbox.setLabel("Public Chat");
            chatbox.setCtext(friendDAO.getCurrentUser().chatDAO().getMessages());

        }
        // Private checkbox clicked
        if (e.getActionCommand() == "Private")
        {
            friendDAO.addLogToFriends();
            privateOrPublic = 1;
            chatbox.emptyCtext();
            Checkpub.setSelected(false);
            chatbox.setLabel("Private Chat. Select someone from friends list");
            selectedFriend = "";


        }
            // Edit contact clicked
            if (e.getActionCommand() == "Edit") {
                String editname = selectedFriend;
                if (editname != "") {
                    String[] opt = {"Fullname", "Cancel"};
                    int i = JOptionPane.showOptionDialog(frame, "Edit"+ editname, "Edit Friend", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, null);
                    if (i == JOptionPane.YES_OPTION) {
                        String yes = JOptionPane.showInputDialog(frame,"Enter fullname for contact " + editname);
                        friendDAO.changeFriend(editname, yes);

                    }
                    System.out.println("Updated " + friendDAO.getFriend(editname)+"'s fullname to: " + friendDAO.getFriend(editname).getFullname());
                }

                if (editname == "") {
                    JOptionPane.showMessageDialog(frame, "Please choose a friend to edit", "Error ", ERROR_MESSAGE);
                }
            }
            // send button is clicked
            if (e.getActionCommand() == "Send")
            {
                chatbox.emptyCtext();
                String input = chatbox.getTextbox().getText();
                if (Checkpub.isSelected())
                {

                    client.sendPublicMessage(input);
                    chatbox.setCtext(friendDAO.getCurrentUser().chatDAO().getMessages());

                }
               else if (Checkpriv.isSelected())
                {

                    System.out.println(selectedFriend);

                    if (selectedFriend != "")
                    {
                        System.out.println(selectedFriend);
                        // Set a message and store it to the friend object that matches the private log variable
                        try{
                        client.sendPrivateMessage(input, selectedFriend);
                        chatbox.setCtext(friendDAO.getFriend(selectedFriend).chatDAO().getMessages());}
                        catch (CustomException ex){
                            new CustomExceptionHandler(ex);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Please choose a friend to send a private message to", "Error " , ERROR_MESSAGE);
                    }
                }
                chatbox.emptytextbox();
            }

            if (e.getActionCommand() == "Exit")
            {
                client.sendLogout();
                System.exit((0));
            }
        }

    /**
     * updates friendslist
     */
    public void friendListUpdater() {

        friends.getListModel().clear();
        for(Map.Entry<String, Friends> entry:friendDAO.getFriends().entrySet()){
            Friends b=entry.getValue();
            friends.getListModel().addElement(b.getNickname());
        }
        frame.revalidate();

    }

}

