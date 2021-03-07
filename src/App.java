import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

public class App {
    public static long currentTime;
    public static long time = 0;
    public static int counter;
    public static Semaphore smp;
    public static Queue<Integer> gordonList;
    public static Queue<Integer> jamieList;
    public static chef gordon, jamie;
    public static helper helper;
    public static boolean flg = false;
    public static String restAmount = "";

    public static void main(String[] args) throws InterruptedException {

        currentTime = System.currentTimeMillis();
        counter = 0;
        gordonList = new LinkedList<>();
        jamieList = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        for (int i = 0; i < x; i++) {
            if (scanner.nextInt() == 1) {
                gordonList.add(i);
            } else {
                jamieList.add(i);
            }
        }
        currentTime = System.currentTimeMillis();
        smp = new Semaphore(1);
        material m = new material();
        gordon = new chef(smp, "gordon", m);
        jamie = new chef(smp, "jamie", m);
        helper = new helper(smp, m);

        helper.start();
        gordon.start();
        jamie.start();

        gordon.join();
        jamie.join();
        helper.join();
    }


}

final class material {

    private int goje, raste, khardel, kachap, piaz;

    material() {
        goje = raste = khardel = kachap = piaz = 2;
    }

    void makeGordon(Integer i) throws IOException {
        if (raste >= 1 && goje >= 2 && piaz >= 1 && kachap >= 2) {
            raste -= 1;
            goje -= 2;
            piaz -= 1;
            kachap -= 2;
            App.counter++;
            System.out.println(App.counter + "-" + (i + 1) + "-Gordon Ramsay-" + App.time );

        }
    }

    boolean gordonRequired() {
        return (raste >= 1 && goje >= 2 && piaz >= 1 && kachap >= 2);
    }

    boolean jamieRequired() {
        return (raste >= 2 && khardel >= 2 && piaz >= 3 && kachap >= 2);
    }

    void makeJamie(Integer i) throws IOException {
        if (raste >= 2 && khardel >= 2 && piaz >= 3 && kachap >= 2) {
            raste -= 2;
            khardel -= 2;
            piaz -= 3;
            kachap -= 2;
            App.counter++;
            System.out.println(App.counter + "-" + (i + 1) + "-Jamie Oliver-" + App.time);
        }
    }

    public void makeGoje() {
        if (App.flg) {
            App.flg = false;
            App.restAmount = "";
            if (this.goje <= 5) {
                this.goje += 5;
            } else {
                this.goje = 10;
            }
            App.time++;
        } else {
            if (this.goje <= 5) {
                this.goje += 5;
            } else {
                this.goje = 10;
            }
            App.time++;
            if (!gordonRequired() && !jamieRequired()) {
                this.goje = 10;
                App.time++;
            } else {
                App.restAmount = "goje";
                App.flg = true;
                App.smp.release();
            }
        }

    }

    public void makeKachap() {
        if (App.flg) {
            App.flg = false;
            App.restAmount = "";
            if (this.kachap <= 5) {
                this.kachap += 5;
            } else {
                this.kachap = 10;
            }
            App.time++;
        } else {
            if (this.kachap <= 5) {
                this.kachap += 5;
            } else {
                this.kachap = 10;
            }
            App.time++;
            if (!gordonRequired() && !jamieRequired()) {
                this.kachap = 10;
                App.time++;
            } else {
                App.restAmount = "kachap";
                App.flg = true;
                App.smp.release();
            }
        }
    }

    public void makeKhardel() {
        if (App.flg) {
            App.flg = false;
            App.restAmount = "";
            if (this.khardel <= 5) {
                this.khardel += 5;
            } else {
                this.khardel = 10;
            }
            App.time++;
        } else {
            if (this.khardel <= 5) {
                this.khardel += 5;
            } else {
                this.khardel = 10;
            }
            App.time++;
            if (!gordonRequired() && !jamieRequired()) {
                this.khardel = 10;
                App.time++;
            } else {
                App.restAmount = "khardel";
                App.flg = true;
                App.smp.release();
            }
        }
    }

    public void makePiaz() {
        if (App.flg) {
            App.flg = false;
            App.restAmount = "";
            if (this.piaz <= 5) {
                this.piaz += 5;
            } else {
                this.piaz = 10;
            }
            App.time++;
        } else {
            if (this.piaz <= 5) {
                this.piaz += 5;
            } else {
                this.piaz = 10;
            }
            App.time++;
            if (!gordonRequired() && !jamieRequired()) {
                this.piaz = 10;
                App.time++;
            } else {
                App.restAmount = "piaz";
                App.flg = true;
                App.smp.release();
            }
        }
    }

    public void makeRaste() {
        if (App.flg) {
            App.flg = false;
            App.restAmount = "";
            if (this.raste <= 5) {
                this.raste += 5;
            } else {
                this.raste = 10;
            }
            App.time++;
        } else {
            if (this.raste <= 5) {
                this.raste += 5;
            } else {
                this.raste = 10;
            }
            App.time++;
            if (!gordonRequired() && !jamieRequired()) {
                this.raste = 10;
                App.time++;
            } else {
                App.restAmount = "raste";
                App.flg = true;
                App.smp.release();
            }
        }
    }

    public int getGoje() {
        return goje;
    }

    public int getKachap() {
        return kachap;
    }

    public int getKhardel() {
        return khardel;
    }

    public int getPiaz() {
        return piaz;
    }

    public int getRaste() {
        return raste;
    }
}

class helper extends Thread {
    int priority = 0;
    Integer x = -1;
    Integer y = -2;
    Semaphore smp;
    material m;

    helper(Semaphore smp, material m) {
        this.smp = smp;
        this.m = m;
    }

    int random() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (priority == 0) {
            if (m.getRaste() < 1) {
                arrayList.add(4);
            }
            if (m.getGoje() < 2) {
                arrayList.add(0);
            }
            if (m.getPiaz() < 1) {
                arrayList.add(3);
            }
        } else {
            if (m.getRaste() < 2) {
                arrayList.add(4);
            }
            if (m.getPiaz() < 3) {
                arrayList.add(3);
            }
            if (m.getKhardel() < 2) {
                arrayList.add(2);
            }
        }
        if (m.getKachap() < 2) {
            arrayList.add(1);
        }
        return arrayList.get((int) (Math.random() * arrayList.size()));
    }

    @Override
    public void run() {
        long i;

        while (true) {
            //har 2000 millisecond check mikonad ke mavad avalie mikhahad ya kheir(bejaye har 20 sanie,har 2 sanie check mikonad)
            if ((i = System.currentTimeMillis()) - App.currentTime < 2000) {
                continue;
            } else
                App.currentTime = i;
            try {
                smp.acquire();
                if (App.gordonList.isEmpty() && App.jamieList.isEmpty()) {
                    smp.release();
                    break;
                }

                if (App.gordonList.size() >= App.jamieList.size()) {
                    priority = 0;
                    App.gordon.setPriority(MAX_PRIORITY);
                    App.jamie.setPriority(MIN_PRIORITY);
                } else {
                    priority = 1;
                    App.jamie.setPriority(MAX_PRIORITY);
                    App.gordon.setPriority(MIN_PRIORITY);
                }

                if (App.flg) {
                    switch (App.restAmount) {
                        case "goje":
                            m.makeGoje();
                            y = 0;
                            break;
                        case "kachap":
                            m.makeKachap();
                            y = 1;
                            break;
                        case "khardel":
                            m.makeKhardel();
                            y = 2;
                            break;
                        case "raste":
                            m.makeRaste();
                            y = 4;
                            break;
                        case "piaz":
                            m.makePiaz();
                            y = 3;
                            break;
                    }
                }
                while (true) {
                    if (App.gordonList.isEmpty() && App.jamieList.isEmpty()) {
                        break;

                    } else if (App.gordonList.isEmpty()) {
                        if (m.jamieRequired())
                            break;
                    } else if (App.jamieList.isEmpty()) {
                        if (m.gordonRequired())
                            break;
                    } else {
                        if (m.gordonRequired() || m.jamieRequired())
                            break;

                    }
                    switch (x= random()) {
                        case 0:
                            if (!y.equals(x))
                                m.makeGoje();
                            break;
                        case 1:
                            if (!y.equals(x))
                                m.makeKachap();
                            break;
                        case 2:
                            if (!y.equals(x))
                                m.makeKhardel();
                            break;
                        case 3:
                            if (!y.equals(x))
                                m.makePiaz();
                            break;
                        case 4:
                            if (!y.equals(x))
                                m.makeRaste();
                    }
                    y = x;

                }
                smp.release();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Goosht "+m.getRaste() + "-Goje " + m.getGoje() + "-Piaz " + m.getPiaz() + "-Ketchup " + m.getKachap() + "-Mustard " + m.getKhardel());
    }
}

class chef extends Thread {
    Semaphore smp;
    String name;
    material m;

    chef(Semaphore smp, String name, material m) {
        this.smp = smp;
        this.name = name;
        this.m = m;
    }

    @Override
    public void run() {
        if (this.name.equals("gordon")) {
            try {
                while (true) {
                    smp.acquire();
                    if (App.gordonList.isEmpty()) {
                        smp.release();
                        break;
                    }
                    if (m.gordonRequired()) {
                        m.makeGordon(App.gordonList.poll());

                    }
                    smp.release();
                }
            } catch (InterruptedException | IOException exception) {
                exception.printStackTrace();
            }

        } else if (this.name.equals("jamie")) {
            try {
                while (true) {
                    smp.acquire();
                    if (App.jamieList.isEmpty()) {
                        smp.release();
                        break;
                    }
                    if (m.jamieRequired()) {
                        m.makeJamie(App.jamieList.poll());

                    }
                    smp.release();
                }

            } catch (InterruptedException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}