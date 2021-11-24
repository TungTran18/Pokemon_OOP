package PikachuGame;

public class TimeCounter extends Thread{
    MainFrame frame;

    public TimeCounter(MainFrame frame) {
        this.frame = frame;
    }

    public void run() {
        while (true){
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            if (frame.isPause()){
                if (frame.isResume()){
                    frame.time--;
                }
            }
            else {
                frame.time--;
            }
            if (frame.time == 0){
                frame.showDialogNewGame("Het Gio! Ban co muon choi lai khong?","Lose",1);
            }
        }
    }
}
