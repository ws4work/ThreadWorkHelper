package work;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;

public class OperCollection {
	
	public final static String openQQOper = "C:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQScLauncher.exe";
	public final static String openVPNOper = "D:\\OpenVPN\\OpenVPN\\bin\\openvpn-gui.exe";
	
	/**
	 * 打开QQ
	 */
	public static void openQQMethod(JButton openQQ) {
		openQQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Runtime.getRuntime().exec(openQQOper);
				} catch (IOException e1) {
					System.out.println("Error exec!");
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * 打开VPN
	 */
	public static void openVPNMethod(JButton openVPN) {
		openVPN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Runtime.getRuntime().exec(openVPNOper);
					Robot robot = new Robot();
					robot.delay(500);
					robot.mouseMove(1740, 1057);
					robot.delay(500);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					robot.delay(10);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
//					robot.mouseMove(1600, 870);
//					robot.keyPress(InputEvent.BUTTON1_MASK);
//					robot.keyRelease(InputEvent.BUTTON1_MASK);
				} catch (IOException e1) {
					System.out.println("Error exec!");
					e1.printStackTrace();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * 关闭vpn
	 */
	public static void closeVPNMethod(JButton closeVPN) {
		closeVPN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// vpnProcess.destroy();
				try {
					Runtime.getRuntime().exec("src/bat/closeVPN.bat");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public static void closeQQMethod(JButton closeQQ) {
		closeQQ.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// vpnProcess.destroy();
				try {
					Runtime.getRuntime().exec("src/bat/closeQQ.bat");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
