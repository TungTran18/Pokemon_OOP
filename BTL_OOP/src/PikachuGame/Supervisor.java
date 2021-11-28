package PikachuGame;

import java.awt.*;
import java.util.ArrayList;

public class Supervisor {
    //giám sát xem game còn nước đi hợp lệ không
    private MatrixGame matrixGame;
    private int row;
    private int collum;
    private int iconCount = 21;
    ArrayList<Point>[][] remainPoint = new ArrayList[iconCount][1];


    public Supervisor(MatrixGame matrixGame){
        this.matrixGame = matrixGame;
        this.row = matrixGame.getRow();
        this.collum = matrixGame.getCollum();

        for (int i = 0; i < iconCount; i++){
            remainPoint[i][0] = new ArrayList<>();
        }

        for (int i = 1; i < row - 1; i++){
            for (int j = 1; j < collum - 1; j++){
                int key = matrixGame.getMatrix()[i][j];
                remainPoint[key][0].add(new Point(i,j));
            }
        }
    }

    public boolean checkValidLine (){
        return false;
    }


}
