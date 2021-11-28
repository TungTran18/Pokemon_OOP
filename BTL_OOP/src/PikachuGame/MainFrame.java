package PikachuGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener, Runnable {
    //panel chính để hiển thị ra màn hình

    private JRadioButton easyButton;
    private JRadioButton mediumButton;
    private JRadioButton hardButton;

    private int row = 8;
    private int collum = 10;

    private int width = 1600;
    private int height = 800;
    private ButtonEvent graphicsPanel;
    private JPanel mainPanel;
    private int MAX_Time = 300;
    public int time = MAX_Time;
    public JLabel lbScore;
    private JProgressBar progressTime;
    private JButton btnNewGame;

    //2 bien de quan ly trang thai cua game, se duoc su dung trong class khac
    public boolean pause = false;
    public boolean resume = false;

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }



    public MainFrame(){
        add(mainPanel = createMainPanel());
        setTitle("Pepe-kachu game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(),BorderLayout.CENTER);
        panel.add(createControlPanel(),BorderLayout.EAST);

        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new ButtonEvent(this, row, collum);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);
        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel(){
        lbScore = new JLabel("0");
        progressTime = new JProgressBar(0,100);
        progressTime.setValue(100);

        JPanel panelLeft = new JPanel(new GridLayout(2,1,5,5));
        panelLeft.add(new Label("Score: "));
        panelLeft.add(new Label("Time: "));

        JPanel panelCenter = new JPanel(new GridLayout(2,1,5,5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5,0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        JPanel panelControl = new JPanel(new BorderLayout(10,10));
        panelControl.setBorder(new EmptyBorder(10,3,5,3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
        panelControl.add(btnNewGame = createButton("New Game"),BorderLayout.PAGE_END);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Control"));
        panel.add(panelControl, BorderLayout.PAGE_START);
        panel.add(createPanelLevel());

        return panel;
    }

    private JPanel createPanelLevel(){
        //tạo các button để điều chỉnh độ khó (độ to) của màn chơi
        easyButton = new JRadioButton("8 x 10");
        easyButton.setSelected(true);

        mediumButton = new JRadioButton("10 x 14");

        hardButton = new JRadioButton("14 x 18");

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(easyButton);
        btnGroup.add(mediumButton);
        btnGroup.add(hardButton);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("level: "));
        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);

        return panel;
    }

    private JButton createButton(String buttonName){
        JButton button = new JButton(buttonName);
        button.addActionListener(this);
        return button;
    }

    public void newGame(){
        time = MAX_Time;
        graphicsPanel.removeAll();
        //kiêm tra xem nút độ khó nào đc chọn để set kích cỡ của màn chơi
        if (easyButton.isSelected()){
            this.row = 8;
            this.collum = 10;
        }
        if (mediumButton.isSelected()){
            this.row = 10;
            this.collum = 14;
        }
        if (hardButton.isSelected()){
            this.row = 14;
            this.collum = 18;
        }
        mainPanel.add(createGraphicsPanel(),BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
        lbScore.setText("0");
    }

    public void showDialogNewGame(String message, String title, int t){
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (select == 0){
            pause = false;
            newGame();
        }
        else {
            if (t == 1){
                System.exit(0);
            }
            else{
                resume = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewGame){
            showDialogNewGame("Game chưa kết thúc. Bạn muốn tạo game mới?","Warning",0);
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / MAX_Time * 100));
        }
    }
}
