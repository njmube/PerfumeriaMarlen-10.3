/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.io.*;

/**
 *
 * @author alfredo
 */
public class ProcessClassPathTask {

	public static void main(String[] args) {
		String classPathFile       = args[0];
		String classPathOutputFile = args[1];
		
		BufferedReader br = null;
		PrintWriter    pr = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(classPathFile)));
			pr = new PrintWriter(new FileOutputStream(classPathOutputFile));
			String line = br.readLine();
			String cpLineSpaces = "Class-Path: "+line.replace("#", " ");
			int maxChars=71;
			char[] cpChars = cpLineSpaces.toCharArray();
			int numLines=0;
			int charsOut=0;
			pr.print("\n");
			for(int nc=0;nc<cpChars.length;nc++){
				if(charsOut>0 && charsOut%maxChars==0){
					numLines++;
					pr.print("\n");
					pr.print(" ");
					charsOut++;
				}
				pr.print(cpChars[nc]);
				charsOut++;
			}
			pr.print("\n");
			pr.print("\n");
			pr.close();
		} catch (IOException ioe) {
			ioe.printStackTrace(System.err);
		}
	}
}
