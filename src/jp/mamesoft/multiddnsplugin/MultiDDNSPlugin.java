package jp.mamesoft.multiddnsplugin;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MultiDDNSPlugin extends JavaPlugin{
	static Logger log;
	static String service;
	static String id;
	static String domain;
	static String pass;
	static String host;
	public void onEnable(){
		this.saveDefaultConfig();
		log = this.getLogger();

		service = this.getConfig().getString("service");
		id = this.getConfig().getString("id");
		domain = this.getConfig().getString("domain");
		pass = this.getConfig().getString("pass");
		host = this.getConfig().getString("host");
		if(service.equals("noset")){
			log.info("[WARNING]DDNSサービスを設定してください");
		}else{
			renew();
			log.info("MultiDDNSPlugin has been enabled!");
		}
	}
	
	public void onDisable(){
		log.info("MultiDDNSPlugin has been disabled.");
	}
	public void renew(){
		TimerTask task = new RenewThread();
		Timer timer = new Timer();
		timer.schedule(task, 0, 30 * 60 * 1000);
	}
}
