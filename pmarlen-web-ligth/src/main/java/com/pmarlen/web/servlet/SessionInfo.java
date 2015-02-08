/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alfredo
 */
public class SessionInfo {
	private static long applicationContextCreationTime;
	
	private HttpSession session;
	private String userName;
	private String lastVisitedPage;
	private String remoteAddr;
	private String userAgent;

	public SessionInfo(HttpSession session, String userName) {
		this.session = session;
		this.userName = userName;
		this.lastVisitedPage = "home";
		this.remoteAddr		 = "-";
		this.userAgent		 = "-";
	}

	/**
	 * @return the session
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private Date creationDate;

	public Date getCreationDate() {
		if (creationDate == null) {
			creationDate = new Date(session.getCreationTime());
		}
		return creationDate;
	}

	public Date getLastAccessedTime() {
		return new Date(session.getLastAccessedTime());
	}

	public String getCreationTimeDiff() {
		long diffInSeconds = (System.currentTimeMillis() - session.getCreationTime()) / 1000;
		return getDiff(diffInSeconds);
	}

	public String getLastAccessedTimeDiff() {
		long diffInSeconds = (System.currentTimeMillis() - session.getLastAccessedTime()) / 1000;
		return getDiff(diffInSeconds);
	}

	private static String getDiff(long diffInSeconds) {


		long diff[] = new long[]{0, 0, 0, 0};

		diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
		diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
		diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
		diff[0] = (diffInSeconds = (diffInSeconds / 24));

		StringBuffer sb = new StringBuffer();
		if (diff[0] > 0) {
			sb.append(diff[0]);
			sb.append(" D");
		}
		if (diff[1] > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(diff[1]);
			sb.append(" H");
		}
		if (diff[2] > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(diff[2]);
			sb.append(" M");
		}
		if (diff[3] > 0) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(diff[3]);
			sb.append(" S");
		}
		sb.append(".");

		return sb.toString();
	}

	public void setLastVisitedPage(String lastVisitedPage) {
		this.lastVisitedPage = lastVisitedPage;
	}

	public String getLastVisitedPage() {
		return lastVisitedPage;
	}

	public static String getLifeTimeUpDiff(){
		long diffInSeconds = (System.currentTimeMillis() - applicationContextCreationTime) / 1000;
		return getDiff(diffInSeconds);
	}

	public static void setApplicationContextCreationTime(long applicationContextCreationTime) {
		SessionInfo.applicationContextCreationTime = applicationContextCreationTime;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
