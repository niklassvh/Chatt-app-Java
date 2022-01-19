/*
Niklas van Herbert
niva1202
Datateknik
JavaII
Laboration 6
2021-08-24
 */
package Labb3;

public class CustomException extends Exception {
    private int num;

    CustomException()
    {
        super();
    }
    CustomException(String message){
        super(message);
    }
    CustomException(int number){
        super();
        this.num =number;
    }

    public int getNum() {
        return num;
    }

}
