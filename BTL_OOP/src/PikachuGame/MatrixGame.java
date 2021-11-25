package PikachuGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MatrixGame {
    private int row;
    private int collum;
    private int[][] matrix;
    public MatrixGame(int row, int collum){
        this.row = row;
        this.collum = collum;
        System.out.println(row + " x " + collum);
        createMatrix();
        showMatrix();
    }

    public void showMatrix(){
        for (int i = 1; i < row -1; i++){
            for (int j = 1; j < collum -1; j++){
                System.out.printf("%3d",matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void createMatrix() {
        matrix = new int[row][collum];

        Random rand = new Random();
        int iconCount = 21;
        int max = (((row * collum) / iconCount) % 2 == 0) ? ((row * collum) / iconCount)+2 : ((row * collum) / iconCount)+1 ; //so lan lon nhat ma mot loai icon co the xuat hien tren ma tran
        int arr[] = new int[iconCount + 1];

        //tao mot khung vien cho game
        for (int i = 0; i < collum; i++){
            matrix[0][i] = matrix[row - 1][i] = 0;
        }
        for (int i = 0; i < row; i++){
            matrix[i][0] = matrix[i][collum-1] = 0;
        }

        //tao mot arrraylist gom cac toa do nham danh dau cac icon xuat hien tranh cho viec trung vi tri trong khoi tao ma tran cho game
        ArrayList<Point> listPoint = new ArrayList<Point>();
        for (int i = 1; i < row - 1; i++){
            for (int j = 1; j < collum - 1; j++){
                listPoint.add(new Point(i,j));
            }
        }

        // gan cac icon len ma tran game
        // sau khi toa do nao da co icon se duoc remove ra khoi array list
        int mark = 0;
        while (mark < (((row - 2) * (collum - 2)) / 2) ){
            int iconIndex = rand.nextInt(iconCount) + 1;
            if (arr[iconIndex] < max) {
                arr[iconIndex] += 2;
                for (int i = 0; i < 2; i++){
                    int size = listPoint.size();
                    int pointIndex = rand.nextInt(size);
                    matrix[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] = iconIndex;
                    listPoint.remove(pointIndex);
                }
                mark++;
            }
        }
    }

    private boolean checkLineX(int y1, int y2, int x){ //check theo hang X
        System.out.println("check line x");

        //tim diem nao co y thap hon
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        //tim vat can tren hang
        for (int y = min + 1; y < max; y++){
            if (matrix[x][y] != 0){
                System.out.println("die" + x + " " + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }

        //neu khong co vat can thi return true;
        return true;
    }

    private boolean checkLineY(int x1, int x2, int y){ //check theo cot y
        System.out.println("check line y");

        //tim diem nao co x thap hon
        int min = Math.min(x1,x2);
        int max = Math.max(x1,x2);

        //tim vat can tren cot
        for (int x = min + 1; x < max; x++){
            if (matrix[x][y] != 0){
                System.out.println("die" + x + " " + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }

        //neu khong co vat can thi return true;
        return true;
    }

    private boolean checkRectX(Point p1, Point p2) {
        System.out.println("check rect x");
        // tim diem co y lon hon
        Point pMinY = p1, pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        for (int y = pMinY.y; y <= pMaxY.y; y++) {
            if (y > pMinY.y && matrix[pMinY.x][y] != 0) {
                return false;
            }
            // ktra 2 duong
            if ((matrix[pMaxY.x][y] == 0) && checkLineY(pMinY.x, pMaxY.x, y) && checkLineX(y, pMaxY.y, pMaxY.x)) {

                System.out.println("Rect x");
                System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                        + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                        + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                // nếu cả 3 duong deu hop le thi return true
                return true;
            }
        }
        // neu khong co du 3 duong hop le thi return false
        return false;
    }

    private boolean checkRectY(Point p1, Point p2) {
        System.out.println("check rect y");
        // tìm điểm có x min
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        //
        for (int x = pMinX.x; x <= pMaxX.x; x++) {
            if (x > pMinX.x && matrix[x][pMinX.y] != 0) {
                return false;
            }
            if ((matrix[x][pMaxX.y] == 0)
                    && checkLineX(pMinX.y, pMaxX.y, x)
                    && checkLineY(x, pMaxX.x, pMaxX.y)) {

                System.out.println("Rect y");
                System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
                        + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                        + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                return true;
            }
        }
        return false;
    }

    private boolean checkMoreLineX(Point p1, Point p2, int type) {
        System.out.println("check chec more x");
        // tìm điểm có y bé hơn
        Point pMinY = p1, pMaxY = p2;
        if (p1.y > p2.y) {
            pMinY = p2;
            pMaxY = p1;
        }
        // tìm tọa độ để bắt đầu
        int y = pMaxY.y + type;
        int row = pMinY.x;
        int colFinish = pMaxY.y;
        if (type == -1) {
            colFinish = pMinY.y;
            y = pMinY.y + type;
            row = pMaxY.x;
            System.out.println("colFinish = " + colFinish);
        }

        // tìm cột kết thúc của đường đi nằm ngoài hcn do 2 điểm tạo thành
        if ((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y) && checkLineX(pMinY.y, pMaxY.y, row)) {
            while (matrix[pMinY.x][y] == 0 && matrix[pMaxY.x][y] == 0) {
                if (checkLineY(pMinY.x, pMaxY.x, y)) {

                    System.out.println("TH X " + type);
                    System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
                            + pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
                            + ") -> (" + pMaxY.x + "," + pMaxY.y + ")");
                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    private boolean checkMoreLineY(Point p1, Point p2, int type) {
        System.out.println("check more y");
        Point pMinX = p1, pMaxX = p2;
        if (p1.x > p2.x) {
            pMinX = p2;
            pMaxX = p1;
        }
        int x = pMaxX.x + type;
        int col = pMinX.y;
        int rowFinish = pMaxX.x;
        if (type == -1) {
            rowFinish = pMinX.x;
            x = pMinX.x + type;
            col = pMaxX.y;
        }
        if ((matrix[rowFinish][col] == 0|| pMinX.x == pMaxX.x) && checkLineY(pMinX.x, pMaxX.x, col)) {
            while (matrix[x][pMinX.y] == 0 && matrix[x][pMaxX.y] == 0) {
                if (checkLineX(pMinX.y, pMaxX.y, x)) {
                    System.out.println("TH Y " + type);
                    System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
                            + x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
                            + ") -> (" + pMaxX.x + "," + pMaxX.y + ")");
                    return true;
                }
                x += type;
            }
        }
        return false;
    }

    public CheckedPair checkTwoPoint(Point p1, Point p2){
        if (!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
            // check line with x
            if (p1.x == p2.x) {
                System.out.println("line x");
                if (checkLineX(p1.y, p2.y, p1.x)) {
                    return new CheckedPair(p1, p2);
                }
            }
            // check line with y
            if (p1.y == p2.y) {
                System.out.println("line y");
                if (checkLineY(p1.x, p2.x, p1.y)) {
                    System.out.println("ok line y");
                    return new CheckedPair(p1, p2);
                }
            }
            // check in rectangle with x
            if ( checkRectX(p1, p2)) {
                System.out.println("rect x");
                return new CheckedPair(p1, p2);
            }
            // check in rectangle with y
            if (checkRectY(p1, p2)) {
                System.out.println("rect y");
                return new CheckedPair(p1, p2);
            }
            // check more right
            if (checkMoreLineX(p1, p2, 1)) {
                System.out.println("more right");
                return new CheckedPair(p1, p2);
            }
            // check more left
            if (checkMoreLineX(p1, p2, -1)) {
                System.out.println("more left");
                return new CheckedPair(p1, p2);
            }
            // check more down
            if (checkMoreLineY(p1, p2, 1)) {
                System.out.println("more down");
                return new CheckedPair(p1, p2);
            }
            // check more up
            if (checkMoreLineY(p1, p2, -1)) {
                System.out.println("more up");
                return new CheckedPair(p1, p2);
            }
        }
        return null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCollum() {
        return collum;
    }

    public void setCollum(int collum) {
        this.collum = collum;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
