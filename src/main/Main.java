package main;

import device.Device;
import device.SerialPortScreenDevice;
import server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yangjie on 2018/12/22.
 */
public class Main {
    public static List<Device> devices = new ArrayList<>();

    public static void main(String[] args) {
        // 设备5个
        SerialPortScreenDevice device1 = new SerialPortScreenDevice("串口屏1",11);
        SerialPortScreenDevice device2 = new SerialPortScreenDevice("串口屏2",21);
        SerialPortScreenDevice device3 = new SerialPortScreenDevice("串口屏3",31);
        SerialPortScreenDevice device4 = new SerialPortScreenDevice("串口屏4",41);
        SerialPortScreenDevice device5 = new SerialPortScreenDevice("串口屏5",51);
        devices.add(device1);
        devices.add(device2);
        devices.add(device3);
        devices.add(device4);
        devices.add(device5);

        // 通信箱的服务端，不断发信息
        Server server = new Server();
        server.start();
    }
}
