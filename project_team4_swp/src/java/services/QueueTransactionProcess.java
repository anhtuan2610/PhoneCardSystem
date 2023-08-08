
package services;

import entity.Transaction;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author phamtung
 */
public class QueueTransactionProcess {
    private ScheduledExecutorService scheduler;
    private BlockingQueue<Integer> queue;

    public void startProcessing() {
        int time = 0;
        int numThreads = 1; // Số lượng luồng xử lý
        queue = new LinkedBlockingQueue<>(); // Tạo hàng chờ (queue) để lưu trữ các phần tử
        scheduler = Executors.newScheduledThreadPool(numThreads);

        long delay = 0; // Không có độ trễ ban đầu
        long period = 20; // Chu kỳ 15 giây

        scheduler.scheduleAtFixedRate(this::processQueue, delay, period, TimeUnit.SECONDS);
        System.out.println("Start queue transaction");
    }

    public void stopProcessing() {
        scheduler.shutdown();
        queue.clear();
        System.out.println("Clear queue transaction thanh cong");
    }

    public void addToQueue(int item) {
        queue.add(item);
        System.out.println("Add thanh cong transaction :" + item);
    }

    public void processQueue() {
       
        while (!queue.isEmpty()) {
            int orderTran = queue.poll();
            // Xử lý phần tử từ hàng chờ
            System.out.println("Processing transaction: " + orderTran);
        }
    }

    public static void main(String[] args) {
        QueueTransactionProcess example = new QueueTransactionProcess();
        example.startProcessing();

        // Thêm các phần tử vào hàng chờ
        example.addToQueue(1);
        example.addToQueue(2);
        example.addToQueue(3);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        example.addToQueue(4);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        example.addToQueue(5);
        // Chờ một thời gian để xử lý các phần tử trong hàng chờ
        // Dừng xử lý
//        example.stopProcessing();
    }
}
