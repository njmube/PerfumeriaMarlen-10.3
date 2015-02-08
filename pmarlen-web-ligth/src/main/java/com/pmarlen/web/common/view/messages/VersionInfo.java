package com.pmarlen.web.common.view.messages;

import com.pmarlen.model.Constants;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="versionInfo")
@RequestScoped
public class VersionInfo {

	public String getSvnInfo_revision() {
		return Constants.getServerVersion();
	}

	public String getVersion() {
		return Constants.getServerVersion();		
	}	

	public String getBuildTimestamp() {
		return Constants.getBuildTimestamp();	
	}

	
}
