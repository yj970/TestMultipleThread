package server;

import bean.Cmd;
import util.T;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yangjie on 2018/12/22.
 */
public class Server {
    ServerHanlder serverHanlder;
    int testCount = 0;
    int channelId = 1;
    public Server() {
        serverHanlder = new ServerHanlder();
    }

    public void start() {
        // 模拟
        Timer timer = new Timer("Server-Thread");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 业务代码，模拟数据
                if (testCount == 50) {
                    timer.cancel();
                    return;
                }

                channelId +=10;
                if (channelId == 61) {
                    channelId = 11;
                }
                testCount++;
                Cmd cmd = new Cmd();
                cmd.setChannelID(channelId);
                cmd.setData("BN"+testCount);
                serverHanlder.accept(cmd);
            }
        },0,20);
    }

}
