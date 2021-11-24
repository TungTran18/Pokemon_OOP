package PikachuGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Supervisor {

    private MatrixGame matrixGame;
    private int row;
    private int collum;

    public Supervisor(MatrixGame matrixGame){
        this.matrixGame = matrixGame;
        this.row = matrixGame.getRow();
        this.collum = matrixGame.getCollum();

        for (int i = 1; i < row - 1; i++){
            for (int j = 1; j < collum - 1; j++){

            }
        }
    }


}
