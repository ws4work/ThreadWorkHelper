package work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class WorkHelperClient {

	public static final int port = 1128;
	public static final String host = "localhost";

	public static void main(String[] args) {
		System.out.println("Client Start...");
		while (true) {
			Socket socket = null;
			try {
				// ����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�
				socket = new Socket(host, port);

				// ��ȡ������������
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// ��������˷�������
				PrintStream out = new PrintStream(socket.getOutputStream());
				System.out.print("������: \t");
				String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
				out.println(str);

				String ret = input.readLine();
				System.out.println("�������˷��ع�������: " + ret);
				// ����յ� "OK" ��Ͽ�����
				if ("OK".equals(ret)) {
					System.out.println("�ͻ��˽��ر�����");
					Thread.sleep(500);
					break;
				}

				out.close();
				input.close();
			} catch (Exception e) {
				System.out.println("�ͻ����쳣:" + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						socket = null;
						System.out.println("�ͻ��� finally �쳣:" + e.getMessage());
					}
				}
			}
		}
	}

}
