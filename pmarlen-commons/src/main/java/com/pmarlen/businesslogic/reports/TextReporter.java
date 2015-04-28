/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.businesslogic.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class TextReporter {
	static class TextToken{
		String content;
		int    srcBeginIndex;
		int    srcEndIndex;

		public TextToken(String content, int srcBeginIndex, int srcEndIndex) {
			this.content = content;
			this.srcBeginIndex = srcBeginIndex;
			this.srcEndIndex = srcEndIndex;
		}

		@Override
		public String toString() {
			return content;
		}
	}
	
	static class StaticTextToken extends TextToken{
		StaticTextToken(String content, int srcBeginIndex, int srcEndIndex){
			super(content, srcBeginIndex, srcEndIndex);
		}
	}
	
	static class VariableToken extends TextToken{
		String a;
		int    n;
		String A;
		
		VariableToken(String c, int srcBeginIndex, int srcEndIndex){
			super(c, srcBeginIndex, srcEndIndex);
			
			String ca[]=c.split(",");
			
			//System.out.println("\t\tVariableToken:ca.length"+Arrays.asList(ca));
			if(ca!=null && ca.length==4){
				this.content = ca[0];
				this.a       = ca[1];
				this.n       = Integer.parseInt(ca[2]);
				this.A       = ca[3];
				//System.out.println("\t\tVariableToken: this.content="+this.content);
			} else {
				this.a       = "r";
				this.n       = 0;
				this.A       = "F";
			}
		}
		
		
		@Override
		public String toString() {
			
			StringBuilder sbr=new StringBuilder();
			int cl=content.length();
			if(this.n>0){
				
				if(cl >= n){
					if(this.a.equals("r")){
						sbr.append(content.substring(cl-n, cl));
					} else if(this.a.equals("l")){
						sbr.append(content.substring(0,n));
					} else if(this.a.equals("c")){
						sbr.append(content.substring(1+((cl-n)/2),n));
					} else {
						throw new IllegalArgumentException("for:"+this.a);
					}
				} else {
					int s  = n-cl;
					int sl = (s/2)+s%2;
					int sr = (s/2);
					if(this.a.equals("r")){
						for(int i=0;i<s;i++){
							sbr.append(' ');
						}
						sbr.append(content.substring(cl-n, cl));
					} else if(this.a.equals("l")){
						sbr.append(content.substring(0,n));
						for(int i=0;i<s;i++){
							sbr.append(' ');
						}
					} else if(this.a.equals("c")){
						for(int i=0;i<sl;i++){
							sbr.append(' ');
						}
						sbr.append(content.substring(1+((cl-n)/2),n));
						for(int i=0;i<sr;i++){
							sbr.append(' ');
						}
					} else {
						throw new IllegalArgumentException("for:"+this.a);
					}
				}				
				return sbr.toString();
			} else {			
				return content;
			}
		}
		
	}
	
	static class TextFieldToken extends VariableToken{
		TextFieldToken(String content, int srcBeginIndex, int srcEndIndex){
			super(content, srcBeginIndex, srcEndIndex);
		}
	}
	
	static class RecordFieldToken extends VariableToken{
		RecordFieldToken(String content, int srcBeginIndex, int srcEndIndex){
			super(content, srcBeginIndex, srcEndIndex);
		}
	}
	
	
	private static String processLine(String srcline,HashMap<String,String> parameters,HashMap<String,String> rfield,int maxLineWidth){	
		List<TextToken> tokens=new ArrayList<TextToken>();
		int fromIndex=0;
		int beginIndex=0;
		int endIndex=0;
		//System.err.println("processLine: ->"+srcline+"<-, maxLineWidth="+maxLineWidth);
		String inThemiddle=null;
		int cont=0;
		String sub=null;
		for(beginIndex=srcline.indexOf("${", fromIndex);beginIndex>=0;beginIndex=srcline.indexOf("${", endIndex)){
			if(cont>0){
				inThemiddle=srcline.substring(endIndex+1,beginIndex);
				//System.err.println("\tprocessLine["+cont+"]["+(endIndex+1)+"]["+beginIndex+"]->"+inThemiddle+"<-");
				tokens.add(new StaticTextToken(inThemiddle, endIndex+1, beginIndex));
			}
			
			endIndex  = srcline.indexOf("}", beginIndex);
			sub=srcline.substring(beginIndex, endIndex+1);
			
			inThemiddle = "";
			//System.err.println("\tprocessLine("+cont+")["+beginIndex+"]["+endIndex+"]->"+sub+"<-");
			String contentToken=sub.substring(2, sub.length()-1);
			tokens.add(new TextFieldToken(contentToken, beginIndex,endIndex+1));
			cont++;
		}
		
		//======================================================================
		StringBuilder sbProcessed=new StringBuilder();
		int cc=0;
		String ti=null;
		
		for(int i=0;i<maxLineWidth;i++){
			sbProcessed.append(' ');
		}
		
		String fp = sbProcessed.toString();
		
		for(TextToken tt: tokens){
			
			if(tt instanceof StaticTextToken){
				ti=tt.toString();				
				cc+=ti.length();
			}
			
			else if(tt instanceof TextFieldToken && parameters.containsKey(tt.content)){
				tt.content=parameters.get(tt.content);
				ti=tt.toString();
				
				cc+=ti.length();
			}
			else if(tt instanceof RecordFieldToken && rfield.containsKey(tt.content)){
				tt.content=rfield.get(tt.content);
				ti=tt.toString();
				
				cc+=ti.length();
			}
		}
		
		return fp;
	}
	
	
	public static void main(String[] args) {
		String srcline = "";
		HashMap<String,String> parameters=new HashMap<String,String>();
		HashMap<String,String> rfield    =new HashMap<String,String>();
		int maxlw=35;
		// srcline:\"\${a,r,2,L} \${b,l,3,F}\"  param:a=12345 param:b=223344 rfield:xx=11  rfield:zz=33  maxlw:25
		
		for(String a:args){
			//System.err.println("a->"+a+"<-");
			
			String aa[]=a.split(":");
			if(aa!=null && aa.length>1){
				String an=aa[0];
				String av=aa[1];
				
				if(an.equals("param")){
					String aap[]=av.split("=");
					if(aap!=null && aap.length>1){
						String pn=aap[0];
						String pv=aap[1];
						parameters.put(pn, pv);
					}
				} else if(an.equals("rfield")){
					String aap[]=av.split("=");
					if(aap!=null && aap.length>1){
						String rn=aap[0];
						String rv=aap[1];
						rfield.put(rn, rv);
					}
				} else if(an.equals("srcline")){
					srcline = av;
				} else if(an.equals("maxlw")){
					maxlw = Integer.parseInt(av);
				}
			}	
		}
		
		System.out.println("srcline:   ->"+srcline+"<-");
		System.out.println("parameters:->"+parameters+"<-");
		System.out.println("rfield:    ->"+rfield+"<-");
		
		System.out.println("processLine:->"+processLine(srcline, parameters, rfield, maxlw)+"<-");		
	}
	
}
