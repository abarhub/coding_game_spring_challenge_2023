package premier;

import java.util.*;

/**
 * Solve this puzzle by writing the shortest code.
 * Whitespaces (spaces, new lines, tabs...) are counted in the total amount of chars.
 * These comments should be burnt after reading!
 **/
class Player1 {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int LX = in.nextInt(); // the X position of the light of power
        int LY = in.nextInt(); // the Y position of the light of power
        int TX = in.nextInt(); // Thor's starting X position
        int TY = in.nextInt(); // Thor's starting Y position

        int nbx,nby;

        nbx=LX-TX;
        nby=LY-TY;

        //if(nbx<0)nbx=-nbx;
        //if(nby<0)nby=-nby;

        System.err.println("LX="+LX+",LY="+LY+",TX="+TX+",TY="+TY+",nbx="+nbx+",nby="+nby);

        // game loop
        while (true) {
            int remainingTurns = in.nextInt(); // The level of Thor's remaining energy, representing the number of moves he can still make.

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            String s="",s1="",s2="";

            System.err.println("nbx="+nbx+",nby="+nby);

            if(nbx!=0){
                if(nbx>0){
                    s2="E";
                } else if(nbx==0){
                    s2="";
                } else {
                    s2="W";
                }
                if(nbx>0)nbx--;
                else if (nbx<0) nbx++;
            }

            if(nby!=0){
                if(nby>0){
                    s1="S";
                } else if(nby==0){
                    s1="";
                } else {
                    s1="N";
                }
                if(nby>0)nby--;
                else if (nby<0)nby++;
            }

            s=s1+s2;

            // A single line providing the move to be made: N NE E SE S SW W or NW
            System.out.println(s);
        }
    }
}