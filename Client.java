/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.RuntimeException;
import java.util.TreeMap;

class Client implements HostListener {
    private Socket hostSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final String host;
    private final int port;

    private final String register = "<REGISTER><%1$s><%2$s><%3$s><IMAGE>";
    private final String publicMessage = "<PUBLIC><%1$s><%2$s>";
    private final String PrivateMessage = "<PRIVATE><%1$s><%2$s><%3$s>";
    private final String logout = "<LOGOUT><SERVER><%1$s>";

    private String nickName;
    private String fullName;
    private boolean connected;
    private String ip;
//------------------------------
    private TreeMap<String,Friends> friendsList = new TreeMap<>();
    FriendsDAO fDAO;
    Chatframe chatArea;
    Friends servInfo = new Friends();
    ArrayList<String> chatList;
    Client cli;
    String logon = "User logon request";
    //--------------------------

    //Constructor
    public Client(FriendsDAO friendsDao, Chatframe chatbox, String host, int port) {
        this.servInfo.setNickname("SERVER");
        this.fDAO = friendsDao;
        this.host = host;
        this.port = port;
        this.chatArea = chatbox;

        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException uhe) {
            new SystemExceptionHandler().unknownHostException(uhe.toString());
            System.err.println("Coudn't access local interface adress, using 127.0.0.1");
            this.ip = "127.0.0.1";
        }
    }

    public void register(String nickname, String fullname) {
        assertConnected();
        this.nickName = nickname;
        this.fullName = fullname;
        sendRegister();

    }

    /***
     * Takes input from the user and adds it to the public log
     *
     * @param messageText the text provided by user when clicking the send button
     */
    public void sendPublicMessage(String messageText) {

        String message = String.format(publicMessage, nickName, messageText);
        System.out.println("Send public funktion.");
        fDAO.getCurrentUser().chatDAO().setMessage(fDAO.getCurrentUser(), messageText, fDAO.getCurrentUser().getNickname());
        fDAO.addLogToCurrentUser();
        out.println(message);
        System.out.println(message);
        out.flush();
    }

    /***
     * Takes user input and adds it to the targeted friends log
     * Checks if the target is a friend who is online.
     *
     * @param messageText user input
     * @param reciever what friend to write message to
     * @throws CustomException prevents messages without a target
     */
    public void sendPrivateMessage(String messageText, String reciever) throws CustomException {
        if (reciever.isEmpty()){
            throw new CustomException(5);
        }
        String message = String.format(PrivateMessage, nickName, reciever, messageText);
        findAuthor(friendsList, reciever);
        System.out.println("Ta emot meddelande: "+ reciever);
        Friends sender = findAuthor(friendsList, nickName);
        System.out.println("Skickar meddelande:" + sender);
        fDAO.getFriend(reciever).chatDAO().setMessage(sender, messageText, reciever);
        fDAO.addLogToFriends();
        System.out.println("Sätt meddelande:");
        System.out.println( "mottagare: " +fDAO.getFriend(reciever).getNickname());
        System.out.println("Personen som skickar: " + sender.getNickname());
        System.out.println("Meddelandet : " + message);

        out.println(message);
        out.flush();
    }

    public void sendLogout() {
        String message = String.format(logout, nickName);
        out.println(message);
        out.flush();
        out.close();
        try {
            in.close();
            hostSocket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    public void register(String name) {
        register(name, name);
    }

    /***
     * Connection to server
     * @param client
     * @throws SystemExceptionHandler

     */
    public void connect( Client client) {
        cli = client;
        try {
        hostSocket = new Socket(host, port);
        out = new PrintWriter(hostSocket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
        connected = true;
        new ListenerThread(this, in, client).start();

        client.register(fDAO.getCurrentUser().getNickname(), fDAO.getCurrentUser().getFullname());
        }
        catch(UnknownHostException unk) {
            new SystemExceptionHandler().unknownHostException(unk.toString());
            System.err.println("Host unreachable");
        }
        catch (IOException ex) {
            new SystemExceptionHandler().socketOfflineException(ex.toString(), client);

        }

    }

    private void sendRegister()  {
        assertConnected();
        String message = String.format(register, nickName, fullName, ip);
       out.println(message);
        out.flush();

    }

    private void assertConnected()  {
        if (connected == false) {
            System.out.println("Not connected!");
        }
    }

    /***
     * Chooses which action to take based on the incoming message from the server
     * this is based on the tags in the message.
     * @param message incomming message from server
     * @throws CustomException
     */
    @Override
    public void messageRecieved(String message) {
        if(message.contains("<FRIEND>")) {
            try {
                addFriend(message);
            } catch (CustomException e) {
                new CustomExceptionHandler(e);
            }
            catch(StringIndexOutOfBoundsException e)
            {
                new SystemExceptionHandler().ioException(e.toString());
            }
        }
        else if(message.contains("<SERVER>")){
            try {
                messageFromServer(message);
            } catch (CustomException e) {
                new CustomExceptionHandler(e);
            }

            return;
        }
        else if(message.contains("<LOGOUT>")){
            removeFriend(message);
        }

        else if(message.contains("<PUBLIC>")){
            try {
                addServerMessage(message, false);
            } catch (CustomException e) {
                new CustomExceptionHandler(e);
            }

        }

        else if(message.contains("<PRIVATE>")){

            try {
                addServerMessage(message, true);
            } catch (CustomException e) {
                new CustomExceptionHandler(e);
            }
            System.out.println(message);

        }
    }
//-------------------------------------------------------------

    /***
     * adds a friend to the friendslist when the server notifies that a new used has logged on.
     * @param message incoming massage from server
     * @throws CustomException
     */
    public void addFriend(String message) throws CustomException{
        // Cheks that the message has correct amount of information, else the friend object wont be added
        if (countOccurence(message) != 10) {
            throw new CustomException(3);
        }
        Friends friend = new Friends();
        String text = message.substring(message.indexOf(">") + 1);
        String name = text.substring(text.indexOf("<") + 1, text.indexOf(">"));
        friend.setNickname(name);
        text = text.substring(text.indexOf(">") + 1);
        friend.setFullname(text.substring(text.indexOf("<") + 1, text.indexOf(">")));
        text = text.substring(text.indexOf(">") + 1);
        friend.setLastip(text.substring(text.indexOf("<") + 1, text.indexOf(">")));
        text = text.substring(text.indexOf(">") + 1);
        friend.setImage(text.substring(text.indexOf("<") + 1, text.indexOf(">")));
        friendsList.put(friend.getNickname(), friend);


    }

    /**
     * Removes a friend from the friendslist when server notifies a logout.
     * @param message
     */
    private void removeFriend(String message) {
        String text = message.substring(message.indexOf(">")+1);
        String Name = text.substring(text.indexOf("<")+1, text.indexOf(">"));
        for(Map.Entry<String, Friends> entry:fDAO.getFriends().entrySet()){    //Checks if friendname already exists in the friendlist
            Friends rmFriend=entry.getValue();
            if(rmFriend.getNickname().equals(Name)) {
                friendsList.remove(Name);
            }
        }

    }

    /***
     * Adds messages from other users on the server to the logs.
     * @param message message from server
     * @param privOrPub if privatechat or public chat is selected
     * @throws CustomException checks for correct message format
     */
    private void addServerMessage(String message, boolean privOrPub) throws CustomException {
       // for some reason, the chatbot does not send the ending > while asking certain questions, like whats 112 times 500. Addings the closing > here.
        if (countOccurence(message) == 5) {
            message = message + ">";
        }
        if (countOccurence(message) != 6)  {
            throw new CustomException(4);
        }

        String text = message.substring(message.indexOf(">")+1);
        String author = text.substring(text.indexOf("<")+1, text.indexOf(">"));
        text = text.substring(text.indexOf(">")+1);
        String msg = text.substring(text.indexOf("<")+1, text.indexOf(">"));

        System.out.println("i AddServerMessage");
        Message newMessage = new Message();
        newMessage.setAuthor(findAuthor(friendsList, author));
        newMessage.setMessage(msg);

        if(privOrPub == false){
            fDAO.getCurrentUser().chatDAO().addMessage(newMessage, fDAO.getCurrentUser().getNickname());
        }
        else  if(privOrPub == true){
            fDAO.addLogToFriends();
            fDAO.getFriend(author).chatDAO().addMessage(newMessage, fDAO.getFriend(author).getNickname());
        }
    }


    private class ListenerThread extends Thread {

        private final HostListener listener;
        private final BufferedReader in;

        public ListenerThread(HostListener listener, BufferedReader in, Client c) {
            this.listener = listener;
            this.in = in;
        }

        public void run() {

            try {
                while (connected) {

                    String line = in.readLine();
                    if (line.contains(logon)){
                       new CustomExceptionHandler(new CustomException(2));
                    }
                    System.out.println("inkommande servermeddelande: " );
                    System.out.println(line);

                    listener.messageRecieved(line);

                }

            } catch (IOException ex) {
                if ( ex.toString().contains("SocketException"))
                {
                    new SystemExceptionHandler().socketOfflineException("Server Connection Lost", cli);
                }
                else {
                    new SystemExceptionHandler().ioException(ex.toString());
                }
            }

        }
    }


// returns friendlist that is later added to the JList.
    public TreeMap<String, Friends> getFriends(){return friendsList;}

    /***
     * Puts messages from the server that's not from a friend, like welcome messages for example, in a serverfile
     * @param message
     * @throws CustomException checks that the ip server has matches with what the user has given
     */
    public void messageFromServer(String message) throws CustomException {

        if (message.contains("You are accessing this server from"))
        {
            String ip = message.substring(message.indexOf("from")+6, message.indexOf("and")-1);
            String saidIP = message.substring(message.indexOf("/")+35, message.indexOf("Announcing")-2);
            if (!ip.equals(saidIP)) {
                throw new CustomException(1);

            }
        }
        String text = message.substring(message.indexOf(">")+1);
        String author = text.substring(text.indexOf("<")+1, text.indexOf(">"));
        text = text.substring(text.indexOf(">")+1);
        String msg = text.substring(text.indexOf("<")+1, text.indexOf(">"));


        Message newMessage = new Message();
        newMessage.setAuthor(servInfo);
        newMessage.setMessage(msg);
        fDAO.addLogToCurrentUser();
        fDAO.getCurrentUser().chatDAO().addMessage(newMessage, fDAO.getCurrentUser().getNickname());

        }

    /**
     * finds the author of each message in a messagelist.
     * @param friendsList
     * @param author
     */
         public Friends findAuthor(TreeMap<String, Friends> friendsList, String author) {

        if (friendsList.containsKey(author)) {
            return fDAO.getFriend(author);
        }
        else {
            return fDAO.getCurrentUser();
        }

    }

    /***
     * counts occurences for < and > to determine weather a servermessage
     * contains enough information to make a friends object
      */

        public int countOccurence(String message)
        {   int counter = 0;
            char frst = '<';
            char scnd = '>';
            for (int j = 0; j < message.length(); j++)
            {
                if (message.charAt(j) == frst) {
                    counter++;
                }
                if (message.charAt(j) == scnd) {
                    counter++;
                }
            }
            return counter;
        }
    }

