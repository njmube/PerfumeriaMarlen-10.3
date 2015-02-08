package com.pmarlen.web.common.view.messages;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messages {

    private static Logger logger = LoggerFactory.getLogger(Messages.class);

    public static FacesMessage getMessage(String bundleName, String resourceId, Object[] params) {
    	
        //logger.debug("->getMessage:bundleName=" + bundleName + ", resourceId=" + resourceId);

        FacesContext context = FacesContext.getCurrentInstance();               
        
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        
        //logger.debug("\t->appBundle=" + appBundle + ", locale=" + locale);

        ClassLoader loader = getClassLoader();
        String summary = getString(appBundle, bundleName, resourceId,
                locale, loader, params);
        if (summary == null) {
            summary = "[" + resourceId + "]";
        }
        String detail = getString(appBundle, bundleName, resourceId + "_detail",
                locale, loader, params);
        
        
        return new FacesMessage(summary, detail);
    }

    public static FacesMessage getSimpleMessage(String resourceId) {
    	
        //logger.debug("->getMessage: resourceId=" + resourceId);

        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        
        //logger.debug("\t->appBundle=" + appBundle + ", locale=" + locale);

        ClassLoader loader = getClassLoader();
        String resourceMessage = null;
        ResourceBundle rb;

        rb = ResourceBundle.getBundle(appBundle, locale, loader);
        
        if (rb != null) {
			try {
				resourceMessage = rb.getString(resourceId);
			} catch( MissingResourceException mre){
				//logger.debug("\t->:"+mre);
				resourceMessage = "<" + resourceId + ">";
			}			
        } else {
            resourceMessage = "(" + resourceId + ")";
        }

        return new FacesMessage(resourceMessage);
    }

    public static String getLocalizedString(String resourceId) {
    	
    	//logger.debug("->getMessage: resourceId=" + resourceId);

        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        
        //logger.debug("\t->appBundle=" + appBundle + ", locale=" + locale);

        ClassLoader loader = getClassLoader();
        String resourceMessage = null;
        ResourceBundle rb;

        rb = ResourceBundle.getBundle(appBundle, locale, loader);
        if (rb != null) {
			try {
				resourceMessage = rb.getString(resourceId);				
			} catch( MissingResourceException mre){
				//logger.debug("\t->:"+mre);
				resourceMessage = "|" + resourceId + "|";
			}
            
        } else {
            resourceMessage = "|" + resourceId + "|";
        }

        return resourceMessage;
    }
	
	public String getLocalizedServletString(String resourceId) {
    	
    	//logger.debug("->getMessage: resourceId=" + resourceId);

        Locale locale = Locale.getDefault();
        
        //logger.debug("\t->bundleBase=" + bundleBase + ", locale=" + locale);

        ClassLoader loader = getClassLoader();
        String resourceMessage = null;
        ResourceBundle rb;

        rb = ResourceBundle.getBundle(bundleBase, locale, loader);
        if (rb != null) {
			try {
				resourceMessage = rb.getString(resourceId);				
			} catch( MissingResourceException mre){
				//logger.debug("\t->:"+mre);
				resourceMessage = "|" + resourceId + "|";
			}
            
        } else {
            resourceMessage = "|" + resourceId + "|";
        }

        return resourceMessage;
    }

    public static String getString(String bundle, String resourceId,Object[] params) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        ClassLoader loader = getClassLoader();
        return getString(appBundle, bundle, resourceId, locale, loader, params);
    }

    public static String getString(String bundle1, String bundle2,String resourceId, Locale locale, ClassLoader loader,Object[] params) {
        String resource = null;
        ResourceBundle bundle;

        if (bundle1 != null) {
            bundle = ResourceBundle.getBundle(bundle1, locale, loader);
            if (bundle != null) {
                try {
                    resource = bundle.getString(resourceId);
                } catch (MissingResourceException ex) {
                	
                    //logger.debug( "Damm it 1 :searching:" + resourceId);
                	
                    Enumeration<String> bke = bundle.getKeys();
                    for (int ik = 0; bke.hasMoreElements(); ik++) {
                    	
                        //logger.debug( "\t->bundle[" + ik + "]=" + bke.nextElement());
                        
                    }
                }
            }
        }

        if (resource == null) {
            bundle = ResourceBundle.getBundle(bundle2, locale, loader);
            if (bundle != null) {
                try {
                    resource = bundle.getString(resourceId);
                } catch (MissingResourceException ex) {
                	
                    //logger.debug( "Damm it 2 : searching:" + resourceId);
                	
                    Enumeration<String> bke = bundle.getKeys();
                    for (int ik = 0; bke.hasMoreElements(); ik++) {
                    	
                        //logger.debug( "\t->bundle[" + ik + "]=" + bke.nextElement());                    	
                    }
                }
            }
        }

        if (resource == null) {
            return null; // no match
        }
        if (params == null) {
            return resource;
        }

        MessageFormat formatter = new MessageFormat(resource, locale);
        return formatter.format(params);
    }

    public static Locale getLocale(FacesContext context) {
        Locale locale = null;
        UIViewRoot viewRoot = context.getViewRoot();        
        if (viewRoot != null) {
            locale = viewRoot.getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }   
    
    public static ClassLoader getClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader;
    }

    public static String stringToHTMLString(String string) {
        StringBuffer sb = new StringBuffer(string.length());
        // true if last char was blank
        boolean lastWasBlankChar = false;
        int len = string.length();
        char c;

        for (int i = 0; i < len; i++) {
            c = string.charAt(i);
            if (c == ' ') {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss
                // word breaking
                if (lastWasBlankChar) {
                    lastWasBlankChar = false;
                    sb.append("&nbsp;");
                } else {
                    lastWasBlankChar = true;
                    sb.append(' ');
                }
            } else {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"') {
                    sb.append("&quot;");
                } else if (c == '&') {
                    sb.append("&amp;");
                } else if (c == '<') {
                    sb.append("&lt;");
                } else if (c == '>') {
                    sb.append("&gt;");
                } else if (c == '\n') // Handle Newline
                {
                    sb.append("&lt;br/&gt;");
                } else {
                    int ci = 0xffff & c;
                    if (ci < 160) // nothing special only 7 Bit
                    {
                        sb.append(c);
                    } else {
                        // Not 7 Bit use the unicode system
                        sb.append("&#");
                        sb.append(new Integer(ci).toString());
                        sb.append(';');
                    }
                }
            }
        }
        return sb.toString();
    }

    private static DecimalFormat cpFormat = new DecimalFormat("00000");

    public static String toCPFormat(int cp){
        return cpFormat.format(cp);
    }
    
    /**
     * This version of getMessage() is used in the RI for localizing RI
     * specific messages.
     */
    public static FacesMessage getMessage(String messageId, Object[] params) {
        Locale locale = null;
        FacesContext context = FacesContext.getCurrentInstance();

        // context.getViewRoot() may not have been initialized at this point.
        if ((context != null) && (context.getViewRoot() != null)) {
        	
            locale = context.getViewRoot().getLocale();

            if (locale == null) {
                locale = Locale.getDefault();
            }
            
        } else {
            locale = Locale.getDefault();
        }

        return getMessage(locale, messageId, params);
    }

    public static FacesMessage getMessage(Locale locale, String messageId, Object[] params) {
    	
    	String summary = null;
    	String detail = null;
    	String bundleName = null;
    	ResourceBundle bundle = null;

        // see if we have a user-provided bundle
        if (null != (bundleName = getApplication().getMessageBundle())) {
            if (null != (bundle = ResourceBundle.getBundle(bundleName,locale, getCurrentLoader(bundleName)))) {
                // see if we have a hit
                try {
                    summary = bundle.getString(messageId);
                } catch (MissingResourceException e) {
                }
            }
        }

        // we couldn't find a summary in the user-provided bundle
        if (null == summary) {
            // see if we have a summary in the app provided bundle
            bundle = ResourceBundle.getBundle(FacesMessage.FACES_MESSAGES, locale, getCurrentLoader(bundleName));

            if (null == bundle) {
                throw new NullPointerException();
            }

            // see if we have a hit
            try {
                summary = bundle.getString(messageId);
            } catch (MissingResourceException e) {
            	
            }
        }

        // we couldn't find a summary anywhere!  Return null
        if (null == summary) {
            return null;
        }

        // At this point, we have a summary and a bundle.
        if ((null == summary) || (null == bundle)) {
            throw new NullPointerException();
        }

        summary = substituteParams(locale, summary, params);

        try {
        	
            detail = substituteParams(locale, bundle.getString(messageId + "_detail"), params);
            
        } catch (MissingResourceException e) {
        	
        }

        return (new FacesMessage(summary, detail));
    }

    
	protected static Application getApplication() {
	    return (FacesContext.getCurrentInstance().getApplication());
	}

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
    	
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }

        return loader;
    }

    public static String substituteParams(Locale locale, String msgtext, Object[] params) {
        String localizedStr = null;

        if ((params == null) || (msgtext == null)) {
            return msgtext;
        }

        StringBuffer b = new StringBuffer(100);
        MessageFormat mf = new MessageFormat(msgtext);

        if (locale != null) {
            mf.setLocale(locale);
            b.append(mf.format(params));
            localizedStr = b.toString();
        }

        return localizedStr;
    }


	// ============================================================
	
	String bundleBase;
	public Messages(String bundleBase){
		//logger.debug("Created: with bundleBase="+bundleBase);
		this.bundleBase = bundleBase;
	}
	
    public String getHTMLString(String resourceId) {
		//logger.debug("-->> getHTMLString("+resourceId+")");
		String localizedString = getLocalizedServletString(resourceId);
		//logger.debug("\tlocalizedString="+localizedString);
		String localizedHTMLString = stringToHTMLString(localizedString);
		//logger.debug("\tlocalizedHTMLString="+localizedHTMLString);
		return localizedHTMLString;
	}
}
