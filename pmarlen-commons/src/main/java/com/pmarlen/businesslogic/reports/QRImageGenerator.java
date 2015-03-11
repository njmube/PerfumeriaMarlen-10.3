/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic.reports;

import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author alfredo
 */
public class QRImageGenerator {
	public static byte[] getQRImage(String cadenaOriginal){
		byte[] imageBytes = null;
		byte[] b = cadenaOriginal.getBytes();
		//convert the byte array into a UTF-8 string
		String data;
		try {
			data = new String(b, "UTF8");
		} catch (UnsupportedEncodingException e) {
			//the program shouldn't be able to get here
			throw new IllegalArgumentException(e);
		}

		//get a byte matrix for the data
		BitMatrix matrix;
		com.google.zxing.Writer writer = new QRCodeWriter();
		int width  = 260;
		int height = 260;
		try {
			
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
			
			//System.out.println("->EncodeHintType.values()="+Arrays.asList(EncodeHintType.values()));
			
			matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, width, height,hints);
		} catch (com.google.zxing.WriterException e) {
			//exit the method
			throw new IllegalArgumentException(e);
		}
		try {
			final int[] enclosingRectangle = matrix.getEnclosingRectangle();
			
			int x1,y1,x2,y2;
			
			
			x1=enclosingRectangle[0];
			y1=enclosingRectangle[1];
			x2=enclosingRectangle[2];
			y2=enclosingRectangle[3];
			
			int matrixWidth = matrix.getWidth();
			
			int qrWidth  = matrixWidth-(x1*2);
			int qrHeight = matrixWidth-(x1*2);
			
			BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, matrixWidth, matrixWidth);

			//graphics.setColor(Color.RED);
			
			//graphics.drawRect(0, 0, matrixWidth-(x1*2)-1, matrixWidth-(y1*2)-1);

			graphics.setColor(Color.BLACK);

			for (int i = 0; i < matrixWidth; i++) {
				for (int j = 0; j < matrixWidth; j++) {
					if (matrix.get(i, j)) {
						graphics.fillRect(i-x1, j-y1, 1, 1);
					}
				}
			}

			//ImageIO.write(image, "PNG", new FileOutputStream("qr_QR_" + width + "x" + height + ".png"));
			//ImageIO.write(image, "JPG", new FileOutputStream("qr_QR_" + width + "x" + height + ".jpg"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ImageIO.write(image, "JPG", baos);
			imageBytes = baos.toByteArray();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		
		return imageBytes;

	}	
}
