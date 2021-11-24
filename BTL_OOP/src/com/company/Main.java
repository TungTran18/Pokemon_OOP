package com.company;

import PikachuGame.MainFrame;
import PikachuGame.TimeCounter;

public class Main {

    public static void main(String[] args) {
        MainFrame frame;
        frame = new MainFrame();
        TimeCounter time = new TimeCounter(frame);
        time.start();
        new Thread(frame).start();
    }
}
