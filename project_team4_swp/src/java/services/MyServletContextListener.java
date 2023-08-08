/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 *
 * @author phamtung
 */
public class MyServletContextListener implements ServletContextListener {

    private QueueOrderProcesss queueOrder;
    private QueueTransactionProcess queueTransaction;
//    private QueueOrderProcess queueOrder;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Phương thức này được gọi khi server khởi động
//        queueOrder = new QueueOrderProcess();
//        queueOrder.startProcessing();
//        sce.getServletContext().setAttribute("queueOrder", queueOrder);

        queueOrder = new QueueOrderProcesss();
        queueOrder.startProcessing();
        sce.getServletContext().setAttribute("queueOrder", queueOrder);

        queueTransaction = new QueueTransactionProcess();
        queueTransaction.startProcessing();
        sce.getServletContext().setAttribute("queueTransaction", queueTransaction);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Phương thức này được gọi khi server tắt
        queueOrder.stopProcessing();
        queueTransaction.stopProcessing();

    }

}
