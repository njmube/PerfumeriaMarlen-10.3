/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic.reports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alfredo
 */
public class TextReporter {

	static boolean DEBUG = false;

	static class TextToken {

		String e;

		int i;
		int l;

		public TextToken(String e, int max) {
			this.e = e;
		}

	}

	static class StaticTextToken extends TextToken {

		StaticTextToken(String e, int max) {
			super(e, max);
		}

		@Override
		public String toString() {
			l = e.length();
			return e;
		}

	}

	static abstract class VariableToken extends TextToken {

		String a;
		int n;
		String A;

		VariableToken(String e, int max) {
			super(e, max);
		}

		static VariableToken build(String ess, int max) {
			VariableToken vt = null;
			String e;

			int i;
			int l;
			String a;
			int n;
			String A;
			boolean isReg=ess.startsWith("#{");
			String es=ess.substring(2, ess.length()-1);
			
			String ca[] = es.split(",");

			if (DEBUG) System.out.println("\t\tVariableToken.build:ca.length" + Arrays.asList(ca));
			
			if (ca != null && ca.length == 4) {
				if (ca[0].length() == 1 && !Character.isLetterOrDigit(ca[0].charAt(0))) {
					vt = new SymbolToken(es, max);
				} else if(isReg){
					vt = new RecordFieldToken(es, max);
				} else {
					vt = new TextFieldToken(es, max);
				}
				vt.e = ca[0];
				vt.a = ca[1];
				int xn = ca[2].contains("%") ? (Integer.parseInt(ca[2].replace("%", "").trim()) * max) / 100
						: ca[2].startsWith("-") ? max - (Integer.parseInt(ca[2].replace("-", "")))
						: Integer.parseInt(ca[2]);
				vt.n = xn;
				vt.A = ca[3];
			} else {
				vt = new TextFieldToken(es, max);
				vt.e = ca[0];
				vt.a = "r";
				vt.n = 0;
				vt.A = "F";
			}
			if (DEBUG) System.out.println("\t\tVariableToken.build: return "+vt.getClass().getSimpleName()+", vt.e="+vt.e);
			return vt;
		}

		public String expand(String value) {
			StringBuilder sbr = new StringBuilder();
			int vl = value.length();
			if (DEBUG) System.out.println("\t\tVariableToken.expand: cl="+vl+", value="+value+", this.n="+this.n+", this.a="+this.a);
			if (this.n > 0) {
				if (vl >= n) {
					if (this.a.equals("r")) {
						sbr.append(value.substring(vl - n, vl));
					} else if (this.a.equals("l")) {
						sbr.append(value.substring(0, n));
					} else if (this.a.equals("c")) {
						sbr.append(value.substring(1 + ((vl - n) / 2), n));
					} else {
						throw new IllegalArgumentException("for:" + this.a);
					}
				} else {
					int s = n - vl;
					int sl = (s / 2) + s % 2;
					int sr = (s / 2);
					if (this.a.equals("r")) {
						for (int i = 0; i < s; i++) {
							sbr.append(' ');
						}
						sbr.append(value.substring(0, vl));
					} else if (this.a.equals("l")) {
						sbr.append(value.substring(0, vl));
						for (int i = 0; i < s; i++) {
							sbr.append(' ');
						}
					} else if (this.a.equals("c")) {
						for (int i = 0; i < sl; i++) {
							sbr.append(' ');
						}
						sbr.append(value);
						for (int i = 0; i < sr; i++) {
							sbr.append(' ');
						}
					} else {
						throw new IllegalArgumentException("for:" + this.a);
					}
				}
				String xcontent = sbr.toString();
				l = xcontent.length();
				return xcontent;
			} else {
				l = value.length();
				return e;
			}
		}

	}

	static class TextFieldToken extends VariableToken {

		protected TextFieldToken(String e, int max) {
			super(e, max);
		}
	}

	static class SymbolToken extends VariableToken {

		protected SymbolToken(String e, int max) {
			super(e, max);
		}
	}

	static class RecordFieldToken extends VariableToken {

		protected RecordFieldToken(String e, int max) {
			super(e, max);
		}
	}

	private static String mergeInsert(String s, String i, int p) {
		return s.substring(0, p) + i + s.substring(p + i.length());
	}

	private static String processLine(String srcline, HashMap<String, String> parameters, HashMap<String, String> rfield, int maxLineWidth) {
		List<TextToken> tokens = new ArrayList<TextToken>();
		
		int fromIndex = 0;
		int beginIndex = 0;
		int endIndex = 0;
		if (DEBUG) System.err.println("processLine: ->" + srcline + "<-, maxLineWidth=" + maxLineWidth);		
		String inThemiddle = null;
		int cont = 0;
		String sub = null;
		/*
		for (beginIndex = srcline.indexOf("${", fromIndex); beginIndex >= 0; beginIndex = srcline.indexOf("${", endIndex)) {
			if (cont > 0) {
				inThemiddle = srcline.substring(endIndex + 1, beginIndex);
				if (DEBUG) System.err.println("\tprocessLine[" + cont + "][" + (endIndex + 1) + "][" + beginIndex + "]->" + inThemiddle + "<-");
				
				tokens.add(new StaticTextToken(inThemiddle, maxLineWidth));
			}

			endIndex = srcline.indexOf("}", beginIndex);
			sub = srcline.substring(beginIndex, endIndex + 1);

			inThemiddle = "";
			if (DEBUG) System.err.println("\tprocessLine(" + cont + ")[" + beginIndex + "][" + endIndex + "]->" + sub + "<-");
			
			String contentToken = sub.substring(2, sub.length() - 1);
			if (DEBUG) System.err.println("\tprocessLine:contentToken->" + contentToken + "<-");
			
			if (contentToken.length() > 1) {
				tokens.add(VariableToken.build(contentToken, maxLineWidth));
			}
			cont++;
		}
		*/
		
		String regExp="(\\$|\\#)\\{((\\p{Alnum})+|(-|_|=|#))\\,(r|c|l)\\,(((-)?)\\p{Digit}+|(\\p{Digit}+(%)?))\\,(L|C|R|F)\\}";
		Pattern pattern = Pattern.compile(regExp);

		Matcher matcher = pattern.matcher(srcline);
		boolean found = false;
		cont = 0;
		beginIndex=0;
		endIndex=0;
		int prevBeginIndex=0;
		int prevEndIndex=0;
		while (matcher.find()) {
			beginIndex = matcher.start();
			
			endIndex=matcher.end();
			if (beginIndex > prevEndIndex) {
				inThemiddle = srcline.substring(prevEndIndex, beginIndex);				
				tokens.add(new StaticTextToken(inThemiddle, maxLineWidth));
				if (DEBUG) System.err.println("\tprocessLine:inThemiddle->" + inThemiddle + "<-");
			}
			
			prevBeginIndex=beginIndex;
			prevEndIndex  =endIndex;
			String txt=matcher.group();
			tokens.add(VariableToken.build(txt, maxLineWidth));
			if (DEBUG) System.err.println("\tprocessLine:contentToken->" + txt + "<-");
			
			if(matcher.hitEnd() && endIndex < srcline.length()){
				inThemiddle = srcline.substring(endIndex+1);				
				tokens.add(new StaticTextToken(inThemiddle, maxLineWidth));
				if (DEBUG) System.err.println("\tprocessLine:inThemiddle last->" + inThemiddle + "<-");
			}
			
			cont++;
			found = true;
		}
		
		
		//======================================================================
		StringBuilder sbProcessed = new StringBuilder();
		int cc = 0;
		int indexStart = 0;
		int indexEnd = 0;
		String ti = null;
		boolean guia=false;
		for (int i = 1; i <= maxLineWidth; i++) {
			if (guia) {
				if (i % 10 == 0) {
					sbProcessed.append('_');
				} else {
					sbProcessed.append('.');
				}
			} else {
				sbProcessed.append(' ');
			}
		}
		String fp = null;
		TextToken tb = null;
		fp = sbProcessed.toString();
		String rp = "";
		int tc = 0;
		int p = 0;
		int iter=0;
		for (TextToken tt : tokens) {
			if(DEBUG)System.err.println("#---["+iter+"]---");
			if(DEBUG)System.err.println("tt         :->"+tt.e+"<-(0):"+tt.getClass().getSimpleName()+", params keys="+parameters.keySet()+", is TextFieldToken ?"+(tt instanceof TextFieldToken));
			if (tt instanceof StaticTextToken) {
				ti = tt.toString();
				tc = ti.length();
				cc += tc;
				p = tb != null ? tb.i + tb.l : 0;
				if(DEBUG) System.err.println("p          :->"+p+"<- (1)");
			} else if (tt instanceof TextFieldToken && parameters.containsKey(tt.e)) {
				ti = ((VariableToken) tt).expand(parameters.get(tt.e));
				tc = ti.length();
				cc += tc;
				VariableToken vt = (VariableToken) tt;
				p = vt.A.equals("L") ? 0
						: vt.A.equals("R") ? maxLineWidth - tc
						: vt.A.equals("C") ? (maxLineWidth - tc) / 2
						: vt.A.equals("F") ? (tb != null ? tb.i + tb.l : 0)
						: 0;
				if(DEBUG) System.err.println("p          :->"+p+"<- (2)");
			} else if (tt instanceof SymbolToken) {
				StringBuilder sbst = new StringBuilder();
				for (int i = 1; i <= ((SymbolToken) tt).n; i++) {
					sbst.append(tt.e.charAt(0));
				}
				ti = sbst.toString();
				tc = ti.length();
				cc += tc;
				VariableToken vt = (VariableToken) tt;
				p = vt.A.equals("L") ? 0
						: vt.A.equals("R") ? maxLineWidth - tc
						: vt.A.equals("C") ? (maxLineWidth - tc) / 2
						: vt.A.equals("F") ? (tb != null ? tb.i + tb.l : 0)
						: 0;
				if(DEBUG) System.err.println("p          :->"+p+"<- (3)");
			} else if (tt instanceof RecordFieldToken && rfield!=null && rfield.containsKey(tt.e)) {
				/*
				ti = ((VariableToken) tt).expand(rfield.get(tt.e));
				tc = ti.length();
				cc += tc;
				p = tb != null ? tb.i + tb.l : 0;
				if(DEBUG) System.err.println("p          :->"+p+"<- (4)");
				*/
				ti = ((RecordFieldToken) tt).expand(rfield.get(tt.e));
				tc = ti.length();
				cc += tc;
				VariableToken vt = (VariableToken) tt;
				p = vt.A.equals("L") ? 0
						: vt.A.equals("R") ? maxLineWidth - tc
						: vt.A.equals("C") ? (maxLineWidth - tc) / 2
						: vt.A.equals("F") ? (tb != null ? tb.i + tb.l : 0)
						: 0;
				if(DEBUG) System.err.println("p          :->"+p+"<- (4)");
			}
			tt.i = p;

			if(DEBUG) System.err.println("mergeInsert:->"+fp+"<- <= ->"+ti+"<-,"+p);
			fp = mergeInsert(fp, ti, p);

			tb = tt;
			
			iter++;
		}

		return fp;
	}
	private static void processReport(InputStream isReport, OutputStream os,HashMap<String, String> parameters, List<HashMap<String, String>> records, int maxLineWidth) {
		processReport(isReport, os, parameters, records, maxLineWidth, "\n", "UTF-8");
	}
	private static void processReport(InputStream isReport, OutputStream os,HashMap<String, String> parameters, List<HashMap<String, String>> records, int maxLineWidth,String endLine,String encoding) {
		BufferedReader br=null;
		PrintStream   ps=null;
		br=new BufferedReader(new InputStreamReader(isReport));
		try{
			ps = new PrintStream(os,true,encoding);
		}catch(UnsupportedEncodingException ue){
			throw new IllegalArgumentException(encoding+" NOT SUPORTED");
		}
		String sourceLine=null;
		
		List<String> preLines   =new ArrayList<String>();
		List<String> postLines  =new ArrayList<String>();
		List<String> detailLines=new ArrayList<String>();
		
		try {
			int zone=0;
			while((sourceLine=br.readLine())!=null){
				//System.err.print("->"+sourceLine);
				if(sourceLine.contains("#{")){
					zone=2;					
				} else {
					if(zone==2||zone==3){
						zone=3;
					} else{
						zone=1;
					}
				}
				//System.err.println("\t->"+zone);
				if(zone==1){
					preLines.add(sourceLine);
				}
				if(zone==2){
					detailLines.add(sourceLine);
				}
				if(zone==3){
					postLines.add(sourceLine);
				}				
			}
			
			//==================================================================
			
			for(String l:preLines){
				ps.println(processLine(l, parameters, null, maxLineWidth));
			}
			for(HashMap<String, String> r:records){
				for(String l:detailLines){			
					ps.println(processLine(l, parameters, r, maxLineWidth));
				}
			}
			for(String l:postLines){
				ps.println(processLine(l, parameters, null, maxLineWidth));
			}
			
		}catch(IOException ioe){
	
		}
		
		
	}

	public static void main(String[] args) {
		/*
		String srcline = "";
		HashMap<String, String> parameters = new HashMap<String, String>();
		HashMap<String, String> rfield = new HashMap<String, String>();
		int maxlw = 35;
		// srcline:\"\${a,r,2,L} \${b,l,3,F}\"  param:a=12345 param:b=223344 rfield:xx=11  rfield:zz=33  maxlw:25
		// srcline:\"\${a,r,2,L} \${b,l,1,F}    \${c,l,10,R}\"  param:a=12345 param:b=223344 param:c=2015/04/28 rfield:xx=11  rfield:zz=33  maxlw:35
		// srcline:\"\${-,l,80%,C}\"  maxlw:40
		// srcline:\"\${clienteL,r,8,L}:\${cliente,l,-10,F}\" param:clienteL=CLIENTE param:cliente=ALFREDO_ESTRADA_GONZÁLEZ  rfield:xx=11  rfield:zz=33  maxlw:35 --debug:true
		// srcline:\"\${cL,r,8,L}@\${c,l,-10,F}\" param:cL=CLIENTE param:c=ALFREDO_ESTRADA_GONZÁLEZ  rfield:xx=11  rfield:zz=33  maxlw:45 --debug:true

		for (String a : args) {
			//if(DEBUG) System.err.println("a->"+a+"<-");

			String aa[] = a.split(":");
			if (aa != null && aa.length > 1) {
				String an = aa[0];
				String av = aa[1];

				if (an.equals("param")) {
					String aap[] = av.split("=");
					if (aap != null && aap.length > 1) {
						String pn = aap[0];
						String pv = aap[1];
						parameters.put(pn, pv);
					}
				} else if (an.equals("rfield")) {
					String aap[] = av.split("=");
					if (aap != null && aap.length > 1) {
						String rn = aap[0];
						String rv = aap[1];
						rfield.put(rn, rv);
					}
				} else if (an.equals("srcline")) {
					srcline = av;
				} else if (an.equals("maxlw")) {
					maxlw = Integer.parseInt(av);
				} else if (an.equals("--debug")) {
					DEBUG = av.equals("true");
				}
			}
		}

		if(DEBUG) System.err.println("srcline:   ->" + srcline + "<-");
		if(DEBUG) System.err.println("parameters:->" + parameters + "<-");
		if(DEBUG) System.err.println("rfield:    ->" + rfield + "<-");
		if(DEBUG) System.err.println("maxlw:     ->" + maxlw + "<-");

		if(DEBUG) System.err.println("processLine:->.........1.........2.........3.........4.........5.........6");
		System.out.println("processLine:->" + processLine(srcline, parameters, rfield, maxlw) + "<-");
		*/
		HashMap<String, String> parameters = new HashMap<String, String> (); 
		List<HashMap<String, String>> records=new ArrayList<HashMap<String, String>>();
		
		parameters.put("titulo","PERFUMERIA MARLEN");
		parameters.put("subTitulo","|S.A. DE C.V.|");
		
		parameters.put("fechaL","FECHA");
		parameters.put("fecha" ,"2015/04/28");
		
		parameters.put("clienteL","CLIENTE");
		parameters.put("cliente" ,"ALFREDO ESTRADA GONZÁLEZ");
		Random rand= new Random();
		DecimalFormat df= new DecimalFormat("###,##0.00");
		double st=0.0;
		double tt=0.0;
		double iva=0.0;
		for(int i=0;i<15;i++){
			HashMap<String, String> r=new HashMap<String, String>();
			int n=rand.nextInt(100);
			String c=String.valueOf(rand.nextInt(1000000))+String.valueOf(rand.nextInt(1000000));
			String d="DESCRIPCCIÓN["+c+"]@"+rand.nextInt(100000);
			double pp=rand.nextDouble()*100.0;
			double imp =n*pp;
			st  += imp/1.16;
			iva += imp/0.16;
			String p=df.format(pp);
			
			r.put("n", String.valueOf(n));
			r.put("c", c);
			r.put("d", d);
			r.put("p", p);
			r.put("i", df.format(imp));
			
			records.add(r);
		}
		parameters.put("st",df.format(st));
		parameters.put("des",df.format(0.0));
		parameters.put("iva",df.format(iva));
		parameters.put("tot",df.format(st));
		DEBUG=false;
		processReport(TextReporter.class.getResourceAsStream("/textreports/test1report.txt"), System.out, parameters, records, 47);
	}

}
