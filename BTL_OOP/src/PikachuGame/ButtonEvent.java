package PikachuGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEvent extends JPanel implements ActionListener {
    private int row;
    private int collum;
    private int gap = 2;
    private int size = 50;
    private JButton[][] buttons;
    private MatrixGame matrixGame;
    private Color backGroundColor = Color.black;
    private MainFrame frame;

    private Point p1;
    private Point p2;
    private CheckedPair checkedPair;
    private int score = 0;
    private int pairs;
    private Color checkedBtnBackGroundColor = new Color(227, 0, 125);

    public ButtonEvent (MainFrame frame, int row, int collum){
        this.frame = frame;
        this.row = row + 2; // + row va collum + 2 de tao vien cho ma tran
        this.collum = collum + 2;
        pairs = (row * collum) / 2;

        setLayout(new GridLayout(row, collum, gap, gap));
        setBackground(backGroundColor);
        setPreferredSize(new Dimension((size + gap) * collum,(size + gap) * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        newGame();
    }

    public void newGame(){
        matrixGame = new MatrixGame(this.row,this.collum);
        addArrayButton();
    }

    public void addArrayButton(){
        // tạo các button trong game
        buttons = new JButton[row][collum];

        for (int i = 1; i < row - 1; i++){
            for (int j = 1; j < collum - 1; j++){
                buttons[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(matrixGame.getMatrix()[i][j]);
                buttons[i][j].setIcon(icon);
                add(buttons[i][j]);
            }
        }
    }

    private Icon getIcon(int index) { //lấy ảnh của icon tương ứng từ folder icon
        int width = 50, height = 50;
        Image img = new ImageIcon(getClass().getResource("/icon/" + index + ".png")).getImage();
        Icon icon = new ImageIcon(img.getScaledInstance(width,height,img.SCALE_SMOOTH));

        return icon;
    }

    private JButton createButton(String action) {
        JButton button = new JButton();
        button.setActionCommand(action);
        button.setBorder(null);
        button.addActionListener(this);

        return button;
    }

    public void execute(Point p1, Point p2) {
        System.out.println("delete");
        setDisable(buttons[p1.x][p1.y]);
        setDisable(buttons[p2.x][p2.y]);
    }

    private void setDisable(JButton btn) {
        btn.setIcon(null);
        btn.setBackground(backGroundColor);
        btn.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnIndex = e.getActionCommand();
        //lấy tọa độ của điểm vừa được bấm
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0,indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot+1,btnIndex.length()));

        //nếu trước đấy chưa có nút nào được bấm thì sẽ gán p1, còn không thì gán p2
        //sau khi mà gán có 2 điểm p1 p2 thì sẽ xét xem là 2 điểm này có hợp lệ để ăn không
        if (p1 == null){
            p1 = new Point(x,y);
            //buttons[p1.x][p1.y].setBorder(new LineBorder(checkedBtnBackGroundColor));
            buttons[p1.x][p1.y].setBackground(checkedBtnBackGroundColor);
        }
        else{
            p2 = new Point(x,y);
            buttons[p2.x][p2.y].setBackground(checkedBtnBackGroundColor);
            System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "(" + p2.x + "," + p2.y + ")");
            checkedPair = matrixGame.checkTwoPoint(p1, p2);
            if (checkedPair != null){
                System.out.println("pair != null");
                matrixGame.getMatrix()[p1.x][p1.y] = 0;
                matrixGame.getMatrix()[p2.x][p2.y] = 0;
                matrixGame.showMatrix(); // chỉ để hiển thị ra console nhằm mục địch ktra
                execute(p1,p2);
                checkedPair = null;
                score += 10;
                pairs--;
                //frame.time++; //phan thuong cong thoi gian la tuy chon
                frame.lbScore.setText(score + "");;
            }
            else{
                buttons[p1.x][p1.y].setBackground(Color.white);
                buttons[p2.x][p2.y].setBackground(Color.white);
            }
            buttons[p1.x][p1.y].setBorder(null);
            p1 = null;
            p2 = null;
            System.out.println("done");
            if (pairs == 0){
                frame.showDialogNewGame("Win! Ban co muon choi tiep khong? ","Win", 1);
            }
        }
    }
}
