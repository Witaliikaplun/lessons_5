import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int SIZE = 10000000;
        final int HALF = SIZE / 2;
        float[] arr = new float[SIZE];

        float[] arr2 = new float[HALF];
        float[] arr3 = new float[HALF];

        Arrays.fill(arr, (float) 1.0);
        MyInterface interf = new MyInterface() {
            @Override
            public void method1(float[] arr, int size) {
                System.out.println("Время начала метода 1 -\t\t" + System.currentTimeMillis());
                for (int i = 0; i < size; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
                System.out.println("Время окончания метода 1 -\t" + System.currentTimeMillis());
            }
        };
        interf.method1(arr, SIZE);

        Arrays.fill(arr, (float) 1.0);  //повторное заполнение метода единицами после первого метода

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, 0, arr2, 0, HALF);
                System.out.println("Время начала 2 -\t\t" + System.currentTimeMillis());

                for (int i = 0; i < HALF; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
                System.arraycopy(arr2, 0, arr, 0, HALF);

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, HALF, arr3, 0, HALF);
                System.out.println("Время начала 3 -\t\t" + System.currentTimeMillis());

                for (int i = 0; i < HALF; i++) {
                    arr3[i] = (float) (arr3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                            Math.cos(0.4f + i / 2));
                }
                System.arraycopy(arr3, 0, arr, HALF, HALF);

            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Время окончания 2 -\t" + System.currentTimeMillis());
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Время окончания 3 -\t" + System.currentTimeMillis());


    }


}