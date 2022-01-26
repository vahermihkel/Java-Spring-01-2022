package ee.mihkel;

                            // peab pärinema Thread klassist
public class Threads extends Thread {
    private final int threadNumber;

    public Threads(int threadNumber){
        this.threadNumber = threadNumber;
    }


    @Override
    public void run() {
        if (threadNumber == 1) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        } else if (threadNumber == 2) {
            for (int i = 10; i > 5; i--) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        } else if (threadNumber == 3) {
            for (int i = 100; i > 95; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
//        if (TIMER == 1h) {
//            interrupt();
//        }

    }
}
