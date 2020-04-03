package com.kep.kakaosync.daemon;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.kep.kakaosync.kauth.KauthApiUtil;
import com.kep.kakaosync.model.User;
import com.kep.kakaosync.repository.UserRepository;

@WebListener
public class DaemonListener implements ServletContextListener, Runnable {
	@Autowired 
	KauthApiUtil kauthApiUtil;
	
	@Autowired
	UserRepository userRepository;
	
    /** 작업을 수행할 thread */
    private Thread thread;
    private boolean isShutdown = false;
    
    /** context */
    private ServletContext sc;
    
    /** 작업을 수행한다 */
    public void startDaemon() {
        if (thread == null) {
            thread = new Thread(this, "Daemon thread for background task");
//            thread.setDaemon(true);
        }
        if (!thread.isAlive()) {
        	
            thread.start();
        }
    }
    /** 스레드가 실제로 작업하는 부분 */
    public void run() {
        Thread currentThread = Thread.currentThread();
        while (currentThread == thread && !this.isShutdown) {
            try {
            	// token 갱신하기
            	try {
            		// 전체 유저 불러오기
                    List<User> users = userRepository.findAll();
                    
                    for(User user : users) {
                    	JsonObject refreshInfo = kauthApiUtil.refreshToken(user.getRefreshToken());
                    	
                    	// 엑세스 토큰 갱신 하기
                    	if(refreshInfo.get("access_token") != null){
                    		user.setAcessToken(refreshInfo.get("access_token").toString());
                    	}
                    	// 리프레시 토큰 갱신 하기
                    	if(refreshInfo.get("refresh_token") != null){
                    		user.setRefreshToken(refreshInfo.get("refresh_token").toString());
                    	}
                    	// 갱신된 정보 저장
                    	userRepository.save(user);
                    	
                    }
            	} catch(Exception e) {
            		e.printStackTrace();
            	}
                
                Thread.sleep(1000*60*10); // 10분
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println ("== DaemonListener end. ==");
    }
    
    /** 컨텍스트 초기화 시 데몬 스레드를 작동한다 */
    public void contextInitialized (ServletContextEvent event) {
        System.out.println ("== DaemonListener.contextInitialized has been called. ==");
        sc = event.getServletContext();
        startDaemon();
    }
    
    /** 컨텍스트 종료 시 thread를 종료시킨다 */
    public void contextDestroyed (ServletContextEvent event) {
        System.out.println ("== DaemonListener.contextDestroyed has been called. ==");
        this.isShutdown = true;
        try
        {
            thread.join();
            thread = null;
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
}