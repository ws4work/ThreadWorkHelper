package work;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class WeatherApiQuery {

	final static String url = "http://api.k780.com:88/?app=weather.today&weaid=1&appkey=20678&sign=d946e59330ec8812dcd6f57c00ab1ae0&format=json";
//	final static String testurl = "http://webservice.36wu.com/TrainService.asmx";

	public static void main(String[] args) {
		try {
			sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject sendMessage() throws Exception {
		// System.out.println("调用servlet开始=================");

		BufferedReader reader = null;

		try {
			String strMessage = "";
			StringBuffer buffer = new StringBuffer();

			// 接报文的地址
			URL uploadServlet = new URL(url);
			HttpURLConnection servletConnection = (HttpURLConnection) uploadServlet.openConnection();
			// 设置连接参数
			servletConnection.setRequestMethod("POST");
			servletConnection.setDoOutput(true);
			servletConnection.setDoInput(true);
			servletConnection.setAllowUserInteraction(true);
			servletConnection.setRequestProperty("Charset", "UTF-8");

			// 开启流，写入XML数据
//			OutputStream output = servletConnection.getOutputStream();
			// System.out.println("发送的报文：");
			// System.out.println(uploadServlet.toString());

			// output.write(uploadServlet.toString().getBytes());
//			output.flush();
//			output.close();

			// 获取返回的数据
			InputStream inputStream = servletConnection.getInputStream();
			// 设置接收数据的编码方式
			reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			while ((strMessage = reader.readLine()) != null) {
				buffer.append(strMessage);
			}
			String string = buffer.toString();
			System.out.println("接收返回值:" + string);
			JSONObject json = JSONObject.fromObject(string);
			String success = json.getString("success");
			if ("1".equals(success)) {
				JSONObject result = json.getJSONObject("result");
				String weaid = result.getString("weaid");
				if ("1".equals(weaid)) {
					return result;
				}
			} else {
				System.out.println("查询失败");
			}
		} catch (java.net.ConnectException e) {
			throw new Exception();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return null;
	}

}
