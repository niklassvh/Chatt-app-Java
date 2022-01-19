/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */
package Labb3;

/** Creates an object and is used to structure the information
 * from the provided file.
 */
public class Friends {
    private String nickname="";
    private String fullname="";
    private String lastip="";
    private String image = "";
    private ChatDAO chat;




    /**
     * String representation of object
     */
    public String toString()
    {
        return nickname;
    }
    // dont return the chat since i dont want the friends list to include it. Can make another tostring if i want to handle chat
    public String toStringWrite()
    {
        return "<"+nickname+">" + "\n" + "[FULLNAME]" + fullname + "\n" + "[LASTIP]" + lastip + "\n" + "[IMAGE]" + image + "\n";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLastip() {
        return lastip;
    }

    public void setLastip(String lastip) {
        this.lastip = lastip;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public ChatDAO chatDAO() {
        return chat;
    }

    public void setChatDao(ChatDAO chat) {
        this.chat = chat;
    }

}
