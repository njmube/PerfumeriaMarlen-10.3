/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pmarlen.dev.task;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class AnalizePMImageInputFiles {
	private static final String regExp="\\p{Digit}{2,18}_\\p{Digit}+\\.png";
	public static void main(String[] args) {
		File dirOriginalImages = null;
		
		dirOriginalImages = new File("/usr/local/pmarlen_multimedio/imagenes_productos_originales");	//new File(args[0]);
		
		File files[]=dirOriginalImages.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().matches(regExp);				
			}
		});
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		HashMap<String,List<File>> organizedFilesByCB = new HashMap<String,List<File>> ();
		for(File f:files){
			System.out.print(f.isDirectory()?"d":"-");
			System.out.print(f.canRead()?"r":"-");
			System.out.print(f.canWrite()?"w":"-");
			System.out.print(f.canExecute()?"x":"-");
			System.out.print(" ");			
			System.out.print(f.getName());
			System.out.print("\t");
			System.out.print(f.length());
			System.out.print("\t");
			System.out.print(sdf.format(new Date(f.lastModified())));
			System.out.print("\t");
			try {
				System.out.println(getMD5Checksum(f.getAbsolutePath()));
			}catch(Exception e){
				System.out.println("\tERROR WHILE GET MD5:"+e.getMessage());
			}
			String cb=f.getName().substring(0, f.getName().indexOf('_'));
			
			List<File> sameCBFilesList = organizedFilesByCB.get(cb);
			if(sameCBFilesList != null){
				sameCBFilesList.add(f);
			} else {
				sameCBFilesList = new ArrayList<File>();
				sameCBFilesList.add(f);
				organizedFilesByCB.put(cb, sameCBFilesList);
			}
		}
		System.out.println("==================================================");
		
		DecimalFormat df=new DecimalFormat("00");
		List<String> rename=new ArrayList<String>();
		for(String cb:organizedFilesByCB.keySet()){
			List<File> sameCBFilesList = organizedFilesByCB.get(cb);
			System.out.println(cb+"\t("+sameCBFilesList.size()+") Files.");
			int c=1;
			for(File f: sameCBFilesList){
				System.out.print("\t"+f.getName());
				String numberFile = f.getName().substring(f.getName().indexOf('_')+1, f.getName().indexOf('.'));
				String correctNumberFile = df.format(c);
				
				if(!numberFile.equals(correctNumberFile)){
					
					String correctName=cb+"_"+correctNumberFile+".png";
					System.out.println("\t("+numberFile+" !="+correctNumberFile+")\t=> "+correctName);
					rename.add("mv "+f.getName()+" "+correctName);
				} else {
					System.out.println("");
				}
				c++;
			}
		}
		
		System.out.println("==================================================>> ERRORS FOR RENAME:");
		for(String rn: rename){
			System.out.println(rn);
		}
		
	}
	
   public static byte[] createChecksum(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5Checksum(String filename) throws Exception {
       byte[] b = createChecksum(filename);
       StringBuilder result = new StringBuilder();

       for (int i=0; i < b.length; i++) {
           result.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ));
       }
       return result.toString();
   }	
}
