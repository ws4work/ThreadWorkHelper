package work;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.sf.json.JSONObject;

public class ThreadWorkHelper {

	final static JFrame jf = new JFrame();
	final static Container con = jf.getContentPane();
	final static JPanel panel = new JPanel();

	// 布局
	static GridLayout TWOBTNGRIDLAYOUT = new GridLayout(2, 1);

	// 颜色
	static Color BACKGROUNDCOLOR = new Color(238, 238, 238);

	// 操作
	public final static String openTime = "17:53:00";
	public final static String openOper = "src\bat\run.bat";

	public static void main(String[] args) {

		// 初始化JFrame
		defaltJFrameSettings();

		panel.setLayout(new FlowLayout());

		// 创建显示时间/天气JTextArea
		final JTextArea timejta = new JTextArea();
		final JTextArea weatherjta = new JTextArea();
		JPanel jptime = new JPanel();
		jptime.setLayout(new BorderLayout());
		// 时间
		showTime(timejta);
		// 天气情况
		showWeather(weatherjta);
		jptime.add(timejta, BorderLayout.NORTH);
		jptime.add(weatherjta, BorderLayout.CENTER);
		con.add(jptime, BorderLayout.NORTH);

		// 开启或者停止线程
		openAndCloseBtnOper(timejta, weatherjta);

		// QQ操作
		qqOper();

		// VPN操作
		vpnOper();

		con.add(panel, BorderLayout.CENTER);

	}

	public static void showTime(JTextArea jta) {
		jta.setText(DateToStr(new Date()));
		jta.setSize(200, 100);
		jta.setEditable(false);
		jta.setBackground(BACKGROUNDCOLOR);
		jta.setBorder(null);
		jta.setVisible(true);
	}

	public static void showWeather(JTextArea weatherjta) {
		try {
			JSONObject weatherinfo = WeatherApiQuery.sendMessage();
			String citynm = weatherinfo.getString("citynm");
			String temperature = weatherinfo.getString("temperature");
			String weather = weatherinfo.getString("weather");
			String wind = weatherinfo.getString("wind");
			String winp = weatherinfo.getString("winp");
			String weatherResult = citynm + " " + temperature + " " + weather + " " + wind + " " + winp;
			weatherjta.setText(weatherResult);
			weatherjta.setSize(200, 100);
			weatherjta.setEditable(false);
			weatherjta.setBackground(BACKGROUNDCOLOR);
			weatherjta.setBorder(null);
			weatherjta.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void qqOper() {
		JPanel jpqq = new JPanel();
		jpqq.setLayout(TWOBTNGRIDLAYOUT);

		// 打开QQ操作
		JButton openQQ = new JButton("打开QQ");
		OperCollection.openQQMethod(openQQ);
		jpqq.add(openQQ);
		// 关闭QQ操作
		JButton closeQQ = new JButton("关闭QQ");
		OperCollection.closeQQMethod(closeQQ);
		jpqq.add(closeQQ);

		panel.add(jpqq);
	}

	public static void vpnOper() {
		JPanel jpvpn = new JPanel();
		jpvpn.setLayout(TWOBTNGRIDLAYOUT);

		// 打开VPN操作
		JButton openVPN = new JButton("打开VPN");
		OperCollection.openVPNMethod(openVPN);
		jpvpn.add(openVPN);
		// 关闭VPN操作
		JButton closeVPN = new JButton("关闭VPN");
		OperCollection.closeVPNMethod(closeVPN);
		jpvpn.add(closeVPN);

		panel.add(jpvpn);
	}

	/**
	 * 打开或者停止线程
	 * 
	 * @param jta
	 */
	public static void openAndCloseBtnOper(final JTextArea timejta, final JTextArea weatherjta) {
		// JButton jb1 = new JButton("开始");
		// 对JButton大小的设置
		// jb1.setPreferredSize(new Dimension(30, 30));
		// 对JButton透明的设置
		// jb1.setContentAreaFilled(false);
		// 对JButton去掉按钮的边框的设置
		// jb1.setBorderPainted(false);
		// 对JButton添加图标呢的设置
		// 实例化一个图标对象
		// ImageIcon image = new ImageIcon(icons[i]);
		// 实例化按钮对象，并且设置按钮上显示图片
		// JButton button = new JButton(image);
		// ——或者
		// button.setIcon(new ImageIcon(getClass().getResource("qq.png")));
		// //qq.png是你要添加的图片
		// 设置凸起来的按钮，很多其他的swing也可用此方法
		// jb1.setBorder(BorderFactory.createRaisedBevelBorder());
		// 设置凹起来的按钮，很多其他的swing也可用此方法
		// jb1.setBorder(BorderFactory.createLoweredBevelBorder());
		// 设置按钮的前景色和背景色
		// jb1.setFont(new java.awt.Font("华文行楷", 1, 15));
		// jb1.setBackground(Color.green);
		// jb1.setSize(50, 50);
		// jb1.setVisible(true);

		final Thread t = treadMethod(timejta, weatherjta);
		// 启动或停用线程
		startOrStopThread(t);
	}

	private static void startOrStopThread(final Thread t) {

		JPanel jpoper = new JPanel();
		jpoper.setLayout(TWOBTNGRIDLAYOUT);
		JButton jb3 = new JButton("开始");
		jb3.setSize(50, 50);
		jb3.setVisible(true);
		jb3.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				t.resume();
			}
		});
		JButton jb4 = new JButton("停止");
		jb4.setSize(50, 50);
		jb4.setVisible(true);
		jb4.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				t.suspend();
			}
		});
		jpoper.add(jb3);
		jpoper.add(jb4);
		panel.add(jpoper);
	}

	private static Container defaltJFrameSettings() {
		// 设置默认关闭方式
		// HIDE_ON_CLOSE关闭后隐藏
		// DO_NOTHING_ON_CLOSE关闭失效
		// DISPOSE_ON_CLOSE
		// EXIT_ON_CLOSE关闭后退出系统
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.setExtendedState(JFrame.ICONIFIED);
		// 设置大小
		jf.setSize(280, 170);
		// 取消标题栏
		// jf.setUndecorated(true);
		// 设置图标
		try {
			jf.setIconImage(ImageIO.read(new File("src/res/cursor-wight-up.gif")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 设置标题
		jf.setTitle("WorkHelper");
		// 设置可见度
		jf.setVisible(true);
		// 设置置顶
		jf.setAlwaysOnTop(true);
		// 设置可改变大小
		jf.setResizable(false);
		// 改变按钮的样式
		setLookAndFeel();
		// final Container con = jf.getContentPane();
		con.setLayout(new BorderLayout());
		con.setBackground(BACKGROUNDCOLOR);
		return con;
	}

	/**
	 * 线程执行方法--每秒显示时间
	 * 
	 * @param jta
	 * @return
	 */
	private static Thread treadMethod(final JTextArea timejta, final JTextArea weatherjta) {
		final Thread t = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (true) {
					showTime(timejta);
					// jta.setText(DateToStr(new Date()));
					if (i % 3600 == 0) {
						showWeather(weatherjta);
					}
					i++;
					// System.out.println(i);

					if (openTime.equals(TimeToStr(new Date()))) {
						try {
							Runtime.getRuntime().exec(OperCollection.openVPNOper);
							Robot robot = new Robot();
							robot.delay(500);
							robot.mouseMove(1740, 1057);
							robot.delay(500);
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
							robot.delay(10);
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
						} catch (IOException e) {
							e.printStackTrace();
						} catch (AWTException e) {
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
		t.start();
		return t;
	}

	/**
	 * 为主面板设置皮肤
	 */
	private static void setLookAndFeel() {
		try {
			// 改变按钮的样式
			// Metal风格 (默认)
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// Windows风格
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// Windows Classic风格
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			// Motif风格
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			// Mac风格 (需要在相关的操作系统上方可实现)
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
			// GTK风格 (需要在相关的操作系统上方可实现)
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			// 可跨平台的默认风格
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// 当前系统的风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 日期转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 时间转化为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String TimeToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String str = format.format(date);
		return str;
	}

}
