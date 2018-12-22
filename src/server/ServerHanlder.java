package server;

import bean.Cmd;
import device.Device;
import main.Main;
import util.T;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yangjie on 2018/12/22.
 */
public class ServerHanlder {

    // 线程池(来1个任务就会启用1个线程，线程可以被重用)
    ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * 收到消息,响应
     * @param cmd
     */
    public void accept(Cmd cmd) {
       int channelId = cmd.getChannelID();
       String data = cmd.getData();
       System.out.println(T.getThreadName()+"收到响应="+data);


       // 绝对是要加线程池的
       for (Device device : Main.devices) {
           if (device.getChannelId() == channelId) {

               executor.execute(new Runnable() {
                   @Override
                   public void run() {
                       device.handle(data);
                   }
               });

           }
       }

    }
}
