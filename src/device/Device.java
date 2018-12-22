package device;

import util.T;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yangjie on 2018/12/22.
 */
public class Device {
    public int channelId; // 通道id
    public String name; // 名字

    // 命令发送队列
    Queue<String> cmdQueue = new LinkedList<>();

    public Device(String name, int channelId) {
        this.channelId = channelId;
        this.name = name;

        startSendCmdThread();
    }

    /**
     * 启动发送命令线程
     */
    private void startSendCmdThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (cmdQueue) {
                        while (cmdQueue.size() == 0) {
                            // 等待
                            try {
                                cmdQueue.notifyAll();
                                System.out.println(T.getThreadName() + "cmd队列长度等于0，发送cmd线程进入等待...");
                                cmdQueue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String cmd = cmdQueue.poll();
                        System.out.println(T.getThreadName() + "发送cmd命令..."+cmd);
                        // ... 模拟发送，假设耗时1s
                        try {
                            Thread.sleep(1000);
                            System.out.println(T.getThreadName() + "发送cmd命令（完成！）..."+cmd);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void handle(String data) {
        // 解析数据data，返回命令cmd
        System.out.println(T.getThreadName()+name+data+"(开始处理...)");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(T.getThreadName()+name+data+"(完成...)");
        // 模拟数据，假设讲data转为cmd
        String cmd = "cmd"+data.replace("BN","");
        // 加入命令队列
        addCmd(cmd);
    }

    int MAX_SIZE = 5;// 缓冲列队最大值
    public void addCmd(String cmd) {
        synchronized (cmdQueue) {
            while (cmdQueue.size() >= MAX_SIZE) {
                try {
                    cmdQueue.notifyAll();
                    System.out.println(T.getThreadName()+"cmd队列长度等于最大值，添加cmd线程进入等待...");
                    cmdQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(T.getThreadName()+"cmd队列加入命令..."+cmd);
            cmdQueue.offer(cmd);
            // 唤醒线程
            cmdQueue.notifyAll();
        }
    }
 }
