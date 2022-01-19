/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */

package Labb3;

public class Message {

    private Friends Author;
    private String Message = "";

    public Message(){
    this.Author = Author;
    this.Message = Message;
    }

    public String toString()
    {
        return Author.getNickname() + ":" + Message ;
    }

    public Friends getAuthor(){

        return Author;
    }
    public String getMessage(){


        return Message;
    }

    public void setAuthor(Friends author) {
        this.Author = author;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

}