public class TimePrinter implements Runnable {

    @Override
    public void run() {
        String messageFormat = "%ds from the start\n";
        int timeDelay = 1000;
        int time = 1;
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(timeDelay);
                System.out.printf(messageFormat, time);
                time++;
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
