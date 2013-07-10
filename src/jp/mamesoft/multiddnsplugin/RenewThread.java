package jp.mamesoft.multiddnsplugin;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.TimerTask;

public class RenewThread extends TimerTask {
	@Override
	public void run() {
		String service = MultiDDNSPlugin.service;
		if(service.equals("mydnsjp")){
			basicauth("http://www.mydns.jp/login.html");
		}else if(service.equals("ddojp")){
			getrequest("http://ddo.jp/dnsupdate.php?dn=%domain%&pw=%pass%");
		}else if(service.equals("ddojpfree")){
			getrequest("http://free.ddo.jp/dnsupdate.php?domain=%id%&pw=%pass%");
		}else if(service.equals("valuedomain")){
			getrequest("http://dyn.value-domain.com/cgi-bin/dyn.fcg?d=%domain%&p=%pass%&h=%host%");
		}else if(service.equals("kdnsjp")){
			basicauth("https://kdns.jp/nic/update?hostname=%domain%");
		}else{
			MultiDDNSPlugin.log.info("[WARNING]DDNSサービスの設定が間違っています！");
		}
	}
	
	//BASIC認証
	public void basicauth(String urlStr){
		urlStr = urlStr.replaceAll("%domain%", MultiDDNSPlugin.domain);
		urlStr = urlStr.replaceAll("%host%", MultiDDNSPlugin.host);
		HttpURLConnection conn = null;

		try {
		    final URL url = new URL(urlStr);

		    conn = (HttpURLConnection)url.openConnection();
		    conn.setRequestMethod("GET");
		    
		    Authenticator.setDefault(new Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication()
		        {
		            final String username = MultiDDNSPlugin.id;
		            final String password = MultiDDNSPlugin.pass;

		    	return new PasswordAuthentication(username, password.toCharArray());
		        }
		    });
		    
		    conn.connect();

		    if (conn.getResponseCode() == 200) {

		        BufferedInputStream in = null;
		        try {
		            in = new BufferedInputStream(conn.getInputStream());

		            while (true) {
		                final int data = in.read();
		                if (data == -1)
		                    break;

		                // @@@ 取得したデータを、どうにかする @@@
		            }

		        } finally {
		            if (in != null)
		                in.close();
		        }
		    }
		} catch (IOException e) {
			MultiDDNSPlugin.log.info("[WARNING]IPアドレスの更新に失敗しました");
		} finally {
		    if (conn != null)
		        conn.disconnect();
				MultiDDNSPlugin.log.info("IPアドレスを更新しました");
		}
	}
	
	//GETリクエスト
	public void getrequest(String urlStr){
		urlStr = urlStr.replaceAll("%id%", MultiDDNSPlugin.id);
		urlStr = urlStr.replaceAll("%domain%", MultiDDNSPlugin.domain);
		urlStr = urlStr.replaceAll("%pass%", MultiDDNSPlugin.pass);
		urlStr = urlStr.replaceAll("%host%", MultiDDNSPlugin.host);
		HttpURLConnection conn = null;

		try {
		    final URL url = new URL(urlStr);

		    conn = (HttpURLConnection)url.openConnection();
		    conn.setRequestMethod("GET");
		    
		    conn.connect();

		    if (conn.getResponseCode() == 200) {

		        BufferedInputStream in = null;
		        try {
		            in = new BufferedInputStream(conn.getInputStream());

		            while (true) {
		                final int data = in.read();
		                if (data == -1)
		                    break;

		                // @@@ 取得したデータを、どうにかする @@@
		            }

		        } finally {
		            if (in != null)
		                in.close();
		        }
		    }
		} catch (IOException e) {
			MultiDDNSPlugin.log.info("[WARNING]IPアドレスの更新に失敗しました");
		} finally {
		    if (conn != null)
		        conn.disconnect();
				MultiDDNSPlugin.log.info("IPアドレスを更新しました");
		}
	}
}
