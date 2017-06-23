package com.atguigu.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKTest {
	
	private static final Logger logger = Logger.getLogger(ZKTest.class);
	//实力常量
	private static final String CONNECTSTRING = "192.168.199.133";
	
	private static final String PATH = "/atguigu";
	
	private static final int SESSIONTIMEOUT = 50*1000;
	
	public ZooKeeper startZK() throws IOException{
		
		return new ZooKeeper(CONNECTSTRING, SESSIONTIMEOUT, new Watcher() {
			
			@Override
			public void process(WatchedEvent arg0) {
				
				
			}
		});
	}
	public void stopZK(ZooKeeper zk) throws InterruptedException{
		
		if(zk != null){
			zk.close();
		}
	}
	
	public void createZNode(ZooKeeper zk,String nodePath,String nodeValue) throws KeeperException, InterruptedException{
		
		zk.create(nodePath, nodeValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	
	}
	
	public String getZNode(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException{
	
		String retValue = null;
		
		byte[] data = zk.getData(nodePath, false, new Stat());
		
		retValue = new String(data);
		
		return retValue;
	}
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException, CloneNotSupportedException {
		ZKTest zKTest = new ZKTest();
		
		ZooKeeper zk = zKTest.startZK();
		
		if(zk.exists(PATH, false)==null){
			zKTest.createZNode(zk, PATH, "hello123");
			logger.info("成功创建");
		}else{
			logger.info("失败创建");
		}
		zKTest.clone();
	}
}
