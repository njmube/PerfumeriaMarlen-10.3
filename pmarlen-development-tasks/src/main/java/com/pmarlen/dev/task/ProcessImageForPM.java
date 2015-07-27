/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.dev.task;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author alfredo
 */
public class ProcessImageForPM {

	private static final float defImageQuality = 0.9f;

	private static final int wmImageWidth = 700;
	private static final int wmImageHeight = 700;

	private static final int targetDefImageWidth = 760;
	private static final int targetDefImageHeight = 760;
	private static final int targetMedImageWidth = 320;
	private static final int targetMedImageHeight = 320;
	private static final int targetMinImageWidth = 128;
	private static final int targetMinImageHeight = 128;
	private static final int targetIcoImageWidth = 16;
	private static final int targetIcoImageHeight = 16;

	private static final String regExp="\\p{Digit}{2,18}_\\p{Digit}+\\.png";
	
	public static void main(String[] args) {
		String inputFile = null;		
		String outputPMImagesDir = null;		
		String waterMarkImgFile = null;
		File dirOriginalImages = null;
		String fromFile= null;
		
		
		
		if (args.length == 4) {
			dirOriginalImages = new File(args[0]);
			inputFile = args[1];
			outputPMImagesDir = args[2];
			waterMarkImgFile = args[3];
		} else {
			dirOriginalImages = new File("/usr/local/pmarlen_multimedio/imagenes_productos_originales"); //new File("/Users/alfredo/tmp/images_pm_processed/original"); //
			inputFile         = "/usr/local/pmarlen_multimedio/imagenes_productos_originales/*.png";
			outputPMImagesDir = "/usr/local/pmarlen_multimedio/imgs_productos"; // "/Users/alfredo/tmp/images_pm_processed/wmImgs"; 
			waterMarkImgFile  = "/usr/local/pmarlen_multimedio/PM_marcaagua_700x200.png";
		}
		
		File files[]=dirOriginalImages.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().matches(regExp);				
			}
		});
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		HashMap<String,List<File>> organizedFilesByCB = new HashMap<String,List<File>> ();
		int tot=0;
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
			System.out.println("");
			
			String cb=f.getName().substring(0, f.getName().indexOf('_'));
			
			List<File> sameCBFilesList = organizedFilesByCB.get(cb);
			if(sameCBFilesList != null){
				sameCBFilesList.add(f);
				tot++;
			} else {
				sameCBFilesList = new ArrayList<File>();
				sameCBFilesList.add(f);				
				organizedFilesByCB.put(cb, sameCBFilesList);
				tot++;
			}
		}
		System.out.println("==================================================");
		
		DecimalFormat df=new DecimalFormat("00");
		List<String> rename=new ArrayList<String>();
		List<String> errorFiles=new ArrayList<String>();
		int nf=1;
		boolean canProcess=true;
		
		if(fromFile != null){
			canProcess=false;
		}
		
		for(String cb:organizedFilesByCB.keySet()){
			List<File> sameCBFilesList = organizedFilesByCB.get(cb);
			//System.out.println(cb+"\t("+sameCBFilesList.size()+") Files.");
			if(cb.contains(fromFile)){
				canProcess=true;
			}
			int c=1;
			for(File f: sameCBFilesList){
				System.out.print("\tPROCESSING :CB_n:"+cb+"\tFILE:"+f.getName());
				String numberFile = f.getName().substring(f.getName().indexOf('_')+1, f.getName().indexOf('.'));
				String correctNumberFile = df.format(c);
				
				if(!numberFile.equals(correctNumberFile)){
					
					String correctName=cb+"_"+correctNumberFile+".png";
					System.out.println("\t("+numberFile+" !="+correctNumberFile+")\t=> "+correctName);
					rename.add("mv "+f.getName()+" "+correctName);
				} else {					
					try {
						if(canProcess){
							System.out.println("-P->>> ("+nf+"/"+tot+")\t"+((nf*100)/tot)+" %\t");
							processPMImage(f.getAbsolutePath(), outputPMImagesDir, waterMarkImgFile);
						} else {
							System.out.println("   >>> ("+nf+"/"+tot+")\t"+((nf*100)/tot)+" %\t");
						}
					}catch(Exception e){
						errorFiles.add(f.getName());
						e.printStackTrace(System.err);
					}
				}
				c++;
				nf++;
			}
			
		}
		
		System.out.println("==================================================>> ERRORS FOR RENAME:");
		for(String rn: rename){
			System.out.println(rn);
		}
		System.out.println("==================================================>> ERRORS FOR PROCESSING FILE:");
		for(String ef : errorFiles){
			System.out.println(ef);
		}
		
	}

	public static void processPMImage(String inputFile,	String outputPMImagesDir,String waterMarkImgFile) {

		String cb_n = "";
		
				
		String  regExp="\\p{Digit}+_\\p{Digit}+";
		Pattern pattern = Pattern.compile(regExp);

		Matcher matcher = pattern.matcher(inputFile);
		boolean found = false;
		int beginIndex=0;
		int endIndex=0;
		while (matcher.find()) {
			beginIndex = matcher.start();			
			endIndex=matcher.end();
			cb_n = inputFile.substring(beginIndex, endIndex);			
		}
		
		//System.out.println("====>PROCESSING IMAGES FOR PRODUCTO CODIGO_BARRAS:" + cb_n +", inputFile:"+inputFile);
		//---------------
		Iterator<ImageWriter> jpegWriters = ImageIO.getImageWritersBySuffix("jpeg");
		if (!jpegWriters.hasNext()) {
			throw new IllegalStateException("No JPEG writers found");
		}
		ImageWriter jpegImageWriter = (ImageWriter) jpegWriters.next();

		ImageWriteParam jpegIWParam = jpegImageWriter.getDefaultWriteParam();
		jpegIWParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		jpegIWParam.setCompressionQuality(defImageQuality);

		//---------------
		Iterator<ImageWriter> pngWriters = ImageIO.getImageWritersBySuffix("png");
		if (!pngWriters.hasNext()) {
			throw new IllegalStateException("No PNG writers found");
		}
		ImageWriter pngImageWriter = (ImageWriter) pngWriters.next();
		ImageWriteParam pngIWParam = pngImageWriter.getDefaultWriteParam();
		//pngIWParam.setCompressionMode(ImageWriteParam.MODE_DEFAULT);
		//pngIWParam.setCompressionQuality(bestImageQuality);
		//---------------
		Iterator<ImageWriter> gifWriters = ImageIO.getImageWritersBySuffix("gif");
		if (!gifWriters.hasNext()) {
			throw new IllegalStateException("No GIF writers found");
		}
		ImageWriter gifImageWriter = (ImageWriter) gifWriters.next();
		ImageWriteParam gifIWParam = gifImageWriter.getDefaultWriteParam();
		gifIWParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		gifIWParam.setCompressionType("LZW");
		gifIWParam.setCompressionQuality(defImageQuality);

		//======================================================================
		File f = new File(inputFile);
		if (!f.exists() || !f.canRead()) {
			System.err.println("Can't read file: " + f);
			return;
		}
		BufferedImage imageTransfomed = null;
		BufferedImage imageWMTransfomed = null;
		BufferedImage originalImage = null;
		BufferedImage watterMarkImg = null;
		Image imageScalled = null;
		Image imageWMScalled = null;
		Graphics2D g2d = null;
		try {
			originalImage = ImageIO.read(f);
			if(originalImage == null){
				throw new IllegalStateException("File "+f.getName()+" can't be read as Image");
			}
			watterMarkImg = ImageIO.read(new File(waterMarkImgFile));
		} catch (IOException ex) {
			System.err.println("====>ERROR READ:" + f);
			return;
		}
		
		int w = originalImage.getWidth();
		int h = originalImage.getHeight();

		int newImageW = 0;
		int newImageH = 0;

		//System.out.println("\t====>TRANSFORMING Image: size:" + w + "x" + h + " => Base Size: " + targetDefImageWidth + "x" + targetDefImageHeight);
		if (h != targetDefImageHeight && h > w) {
			newImageH = targetDefImageHeight;
			newImageW = ((targetDefImageHeight * w) / h);
			//System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);
		} else {
			newImageH = ((targetDefImageHeight * h) / w);
			newImageW = targetDefImageWidth;
			//System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);						
		}

		imageScalled = originalImage.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);

		int coordImgX = 0;
		int coordImgY = 0;
		int coordWMImgX = 0;
		int coordWMImgY = 0;

		imageTransfomed = new BufferedImage(targetDefImageWidth, targetDefImageHeight, BufferedImage.TYPE_INT_ARGB);
		imageWMTransfomed = new BufferedImage(targetDefImageWidth, targetDefImageHeight, BufferedImage.TYPE_INT_ARGB);

		//----------------------------------------------------------------------
		g2d = (Graphics2D) imageTransfomed.getGraphics();
		coordImgX = (targetDefImageWidth - newImageW) / 2;
		coordImgY = (targetDefImageHeight - newImageH) / 2;
		coordWMImgX = (targetDefImageWidth - wmImageWidth) / 2;
		coordWMImgY = (targetDefImageHeight - wmImageHeight) / 2;
		g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
		//----------------------------------------------------------------------
		g2d = (Graphics2D) imageWMTransfomed.getGraphics();
		coordImgX = (targetDefImageWidth - newImageW) / 2;
		coordImgY = (targetDefImageHeight - newImageH) / 2;
		coordWMImgX = (targetDefImageWidth - wmImageWidth) / 2;
		coordWMImgY = (targetDefImageHeight - wmImageHeight) / 2;
		g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
		for (int k = 0; k < 3; k++) {
			coordWMImgY = (targetDefImageHeight * k / 3) + (targetDefImageHeight - wmImageHeight) / 2;
			g2d.drawImage(watterMarkImg, coordWMImgX, coordWMImgY, null);
		}

		//System.out.println("====>SAVING:");
		String nombreArchivoFinal =  "_"+cb_n;		
		try {
			//System.out.println("\t====>WRITING JPEGs");
			nombreArchivoFinal =  "_"+cb_n;
			saveScaledJPGImage(imageWMTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			saveScaledJPGImage(imageWMTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			saveScaledJPGImage(imageWMTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			nombreArchivoFinal = "-NWM_" + cb_n;
			saveScaledJPGImage(imageTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			saveScaledJPGImage(imageTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			saveScaledJPGImage(imageTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);
			saveScaledJPGImage(imageTransfomed, targetIcoImageWidth, targetIcoImageHeight, outputPMImagesDir + File.separator + "ICO" + nombreArchivoFinal + ".jpg", jpegImageWriter, jpegIWParam);

			//System.out.println("\t====>WRITING PNGs");
			nombreArchivoFinal =  "_"+cb_n;
			saveScaledImage(imageWMTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			saveScaledImage(imageWMTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			saveScaledImage(imageWMTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			nombreArchivoFinal = "-NWM_" + cb_n;
			saveScaledImage(imageTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			saveScaledImage(imageTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			saveScaledImage(imageTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);
			saveScaledImage(imageTransfomed, targetIcoImageWidth, targetIcoImageHeight, outputPMImagesDir + File.separator + "ICO" + nombreArchivoFinal + ".png", pngImageWriter, pngIWParam, false);

			//System.out.println("\t====>WRITING GIFs");
			nombreArchivoFinal =  "_"+cb_n;
			saveScaledImage(imageWMTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			saveScaledImage(imageWMTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			saveScaledImage(imageWMTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			nombreArchivoFinal = "-NWM_" + cb_n;
			saveScaledImage(imageTransfomed, targetDefImageWidth, targetDefImageHeight, outputPMImagesDir + File.separator + "DEF" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			saveScaledImage(imageTransfomed, targetMedImageWidth, targetMedImageHeight, outputPMImagesDir + File.separator + "MED" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			saveScaledImage(imageTransfomed, targetMinImageWidth, targetMinImageHeight, outputPMImagesDir + File.separator + "MIN" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);
			saveScaledImage(imageTransfomed, targetIcoImageWidth, targetIcoImageHeight, outputPMImagesDir + File.separator + "ICO" + nombreArchivoFinal + ".gif", gifImageWriter, gifIWParam, false);

			//System.out.println("\t====>DONE.");

		} catch (IOException ex) {
			System.err.println("====>ERROR WRITE:");
			ex.printStackTrace(System.err);
			return;
		}
	}

	private static void saveScaledImage(BufferedImage src, int w, int h, String destFile, ImageWriter imageWriter, ImageWriteParam iwp, boolean whiteBackGround) throws IOException {
		Image productoImgeMedRes = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage outImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2ds = (Graphics2D) outImg.getGraphics();
		if (whiteBackGround) {
			g2ds.setColor(Color.WHITE);
			g2ds.fillRect(0, 0, outImg.getWidth(), outImg.getHeight());
		}
		g2ds.drawImage(productoImgeMedRes, 0, 0, null);
		ImageOutputStream ios = ImageIO.createImageOutputStream(new FileOutputStream(destFile));
		imageWriter.setOutput(ios);
		imageWriter.write(null, new IIOImage(outImg, null, null), iwp);
	}

	private static void saveScaledJPGImage(BufferedImage src, int w, int h, String destFile, ImageWriter imageWriter, ImageWriteParam iwp) throws IOException {
		Image productoImgeMedRes = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage outImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2ds = (Graphics2D) outImg.getGraphics();
		g2ds.setColor(Color.WHITE);
		g2ds.fillRect(0, 0, outImg.getWidth(), outImg.getHeight());

		g2ds.drawImage(productoImgeMedRes, 0, 0, null);
		ImageOutputStream ios = ImageIO.createImageOutputStream(new FileOutputStream(destFile));
		imageWriter.setOutput(ios);
		imageWriter.write(null, new IIOImage(outImg, null, null), iwp);
	}

}
