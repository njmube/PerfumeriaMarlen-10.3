package com.pmarlen.dev.task;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * CrearImagenesDePruebaEnMultimedio
 */
public class CrearImagenesDePruebaEnMultimedio {

	float jpegImageQuality = 1.0f;
	int targetImageWidth = 760;
	int targetImageHeight = 760;
	int targetMediumImageWidth = 320;
	int targetMediumImageHeight = 320;
	int targetMinimunImageWidth = 160;
	int targetMinimunImageHeight = 160;
	int targetIconImageWidth = 15;
	int targetIconImageHeight = 15;

	public void crearImagesnes(String url, String user, String password, String inputDirImages, String finalOutputDir) {

		Connection conn = null;
		PreparedStatement psMultimedio = null;
		PreparedStatement psProductoMultimedio = null;
		PreparedStatement psProducto = null;

		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		if (!writers.hasNext()) {
			throw new IllegalStateException("No JPEG writers found");
		}
		ImageWriter jpegImageWriter = (ImageWriter) writers.next();

		ImageWriteParam param = jpegImageWriter.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(jpegImageQuality);
		ResultSet rsProducto = null;
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				System.exit(1);
			}

			conn = DriverManager.getConnection(url, user, password);

			psMultimedio = conn.prepareStatement("DELETE FROM PRODUCTO_MULTIMEDIO");
			int rmM = psMultimedio.executeUpdate();
			psMultimedio.close();
			System.err.println(" ====> DElete all PRODUCTO_MULTIMEDIO:" + rmM);

			psMultimedio = conn.prepareStatement("DELETE FROM MULTIMEDIO");
			rmM = psMultimedio.executeUpdate();
			psMultimedio.close();
			System.err.println(" ====> Delete all MULTIMEDIO:" + rmM);

			psMultimedio = conn.prepareStatement("INSERT INTO MULTIMEDIO(MIME_TYPE,RUTA_CONTENIDO,SIZE_BYTES,NOMBRE_ARCHIVO) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			psProductoMultimedio = conn.prepareStatement("INSERT INTO PRODUCTO_MULTIMEDIO(MULTIMEDIO_ID,PRODUCTO_CODIGO_BARRAS) VALUES(?,?)");
			psProducto = conn.prepareStatement("SELECT * FROM PRODUCTO WHERE CODIGO_BARRAS = ?");

			System.err.println("====> imageFileDir=" + inputDirImages);

			File imageFileDir = new File(inputDirImages);
			File[] filesInDir = imageFileDir.listFiles();
			String productoCB = null;
			System.err.println("====> Listing dor for search all Images files in dir:" + imageFileDir.getAbsolutePath());
			int multimedioId = -1;
			boolean existeProducto = false;
			int total = filesInDir.length;
			int fileProcessed = 0;
			int porcentaje = 0;
			String fileRegExp = new String();
			ArrayList<String> fileNotFoundList = new ArrayList<String>();
			int nf = 0;
			for (File f : filesInDir) {
				try {
					if (f.isDirectory() || !f.canRead()) {
						continue;
					}

					String fnlc = f.getName().toLowerCase();
					if (fnlc.endsWith(".png") && fnlc.indexOf('_') > 1) {
						productoCB = fnlc.substring(0, fnlc.indexOf('_')); //.replace("_01.png", "").replace("_001.png", "");
					} else {
						continue;
					}

					existeProducto = false;
					psProducto.clearParameters();
					psProducto.setString(1, productoCB);
					rsProducto = psProducto.executeQuery();
					if (rsProducto.next()) {
						String cb = rsProducto.getString("CODIGO_BARRAS");
						//System.err.println("====>CONSULTANDO CODIGO_BARRAS="+cb);
						existeProducto = cb.equalsIgnoreCase(productoCB);
					}
					rsProducto.close();
					rsProducto = null;
					if (!existeProducto) {
						//System.err.println("====>NO EXISTE PRODUCTO CON CODIGO_BARRAS=["+productoCB+"]");					
						fileNotFoundList.add(f.getName());
						nf++;
						continue;
					}

					fileProcessed++;

					porcentaje = (fileProcessed * 100) / total;

					System.err.println("====>(" + fileProcessed + "/" + total + ") " + nf + " " + porcentaje + "%: IMPORTANDO IMAGEN PARA PRODUCTO CODIGO_BARRAS=[" + productoCB + "]");

					String nombreArchivoFinal = "PRODUCTO_MULTIMEDIO_" + productoCB + "_1.jpg";

					psMultimedio.clearParameters();
					psMultimedio.setString(1, "image/jpeg");
					psMultimedio.setString(2, "/usr/local/pmarlen_multimedio");
					psMultimedio.setInt(3, (int) f.length());
					psMultimedio.setString(4, nombreArchivoFinal);

					int um = psMultimedio.executeUpdate();

					ResultSet gkrs = psMultimedio.getGeneratedKeys();
					while (gkrs.next()) {
						multimedioId = gkrs.getInt(1);
					}
					gkrs.close();
					if (multimedioId == -1) {
						throw new Exception("No se obtuvo la llave multimedioId");
					}
				//System.err.println("\t====>INSERTED MULTIMEDIO.ID="+multimedioId);

					psProductoMultimedio.clearParameters();
					psProductoMultimedio.setInt(1, multimedioId);
					psProductoMultimedio.setString(2, productoCB);

					psProductoMultimedio.executeUpdate();
				//System.err.println("\t====>INSERTED PRODUCTO_MULTIMEDIO !");

					BufferedImage biTransformed = null;
					BufferedImage originalImage = null;
					Image imageScalled = null;

					originalImage = ImageIO.read(f);

					if (originalImage == null) {
						System.err.println("");
						System.err.println("====>ERROR READ:" + f);
						System.err.println("");
						fileProcessed--;
						continue;
					}

					int w = originalImage.getWidth();
					int h = originalImage.getHeight();

					int newImageW = 0;
					int newImageH = 0;

				//System.err.println("\t====>TRANSFORMING Image: size:" + w + "x" + h + " => " + targetImageWidth + "x" + targetImageHeight);
					if (h != targetImageHeight && h > w) {
						newImageH = targetImageHeight;
						newImageW = ((targetImageHeight * w) / h);
						//System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);
					} else {
						newImageH = ((targetImageHeight * h) / w);
						newImageW = targetImageWidth;
						//System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);						
					}

					imageScalled = originalImage.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);

					int coordImgX = 0;

					biTransformed = new BufferedImage(targetImageWidth, targetImageHeight, BufferedImage.TYPE_INT_RGB);

					Graphics2D g2d = (Graphics2D) biTransformed.getGraphics();
					Color bgColor = Color.WHITE;
					g2d.setColor(bgColor);
					g2d.fillRect(0, 0, targetImageWidth, targetImageHeight);
					int coordImgY = 0;
					if (newImageW >= targetImageWidth) {
						coordImgX = (targetImageWidth - newImageW) / 2;
						coordImgY = (targetImageHeight - newImageH) / 2;
						//System.err.println("\t\t CENTER + CROP =>> (" + (coordImgX) + ",0) to (" + targetImageWidth + "," + targetImageHeight + ")");
						g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
					} else if (newImageW < targetImageWidth) {
						coordImgX = (targetImageWidth - newImageW) / 2;
						//System.err.println("\t\t FILL BACKGROUNG WITH GRADIENT =>> Paint image in (" + coordImgX + ",0)");
						g2d.drawImage(drawVerticalsBarsgradient(imageScalled, 0.02f, bgColor), coordImgX, 0, null);
					}
					//System.err.println("\t====>SAVING SCALED");
					saveScaledJpegImage(biTransformed, targetImageWidth, targetImageHeight, finalOutputDir + File.separator + nombreArchivoFinal, jpegImageWriter, param);
					saveScaledJpegImage(biTransformed, targetMediumImageWidth, targetMediumImageHeight, finalOutputDir + File.separator + "MED_" + nombreArchivoFinal, jpegImageWriter, param);
					saveScaledJpegImage(biTransformed, targetMinimunImageWidth, targetMinimunImageHeight, finalOutputDir + File.separator + "MIN_" + nombreArchivoFinal, jpegImageWriter, param);
					saveScaledJpegImage(biTransformed, targetIconImageWidth, targetIconImageHeight, finalOutputDir + File.separator + "ICO_" + nombreArchivoFinal, jpegImageWriter, param);
					//System.err.println("\t====>DOME WITH IMAGE.");
				} catch (Exception e) {
					System.err.println("====>PROCESSING:" + f + ", EXCEPTION:" + e);
				}
			}

			psMultimedio.close();
			psProductoMultimedio.close();

			conn.close();
			System.exit(0);
			System.err.println("NOT FOUND FOR PRODUCTO : ");
			for (String fnf : fileNotFoundList) {
				System.err.println(fnf);
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void extraerImagenes(String url, String user, String password, String baseDirImages, String dirImages) {

		Connection conn = null;
		PreparedStatement psMultimedio = null;

		ResultSet rsMultimedio = null;
		byte[] buffer = new byte[1028 * 64];
		try {
			conn = DriverManager.getConnection(url, user, password);

			psMultimedio = conn.prepareStatement("SELECT ID,RUTA_CONTENIDO,NOMBRE_ARCHIVO FROM MULTIMEDIO");
			rsMultimedio = psMultimedio.executeQuery();

			int contExtraidos = 0;
			while (rsMultimedio.next()) {
				Integer multimedioId = rsMultimedio.getInt("ID");
				String nombrearchivo = rsMultimedio.getString("NOMBRE_ARCHIVO");

				InputStream is = null;
				//is = rsMultimedio.getBinaryStream("CONTENIDO");
				is = new FileInputStream(baseDirImages + rsMultimedio.getString("RUTA_CONTENIDO") + nombrearchivo);
				FileOutputStream fos = new FileOutputStream(dirImages + File.separator + nombrearchivo);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int r = -1;
				while ((r = is.read(buffer, 0, buffer.length)) != -1) {
					fos.write(buffer, 0, r);
					baos.write(buffer, 0, r);
				}
				is.close();
				fos.close();
				baos.close();
				System.err.println("\tOk exploded image for multimedio:" + multimedioId);

				BufferedImage productoImge = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));

				Image productoImgeMedRes = productoImge.getScaledInstance(targetMediumImageWidth, targetMediumImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeMedResRenderd = new BufferedImage(targetMediumImageWidth, targetMediumImageHeight, BufferedImage.TYPE_INT_RGB);

				Graphics2D g2d = (Graphics2D) productoImgeMedResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeMedResRenderd.getWidth(), productoImgeMedResRenderd.getHeight());
				g2d.drawImage(productoImgeMedRes, 0, 0, null);
				ImageIO.write(productoImgeMedResRenderd, "jpeg", new FileOutputStream(dirImages + File.separator + "MED_" + nombrearchivo));
				System.err.println("\t\tOk exploded MedRes:");

				Image productoImgeMinRes = productoImge.getScaledInstance(targetMinimunImageWidth, targetMinimunImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeMinResRenderd = new BufferedImage(targetMinimunImageWidth, targetMinimunImageHeight, BufferedImage.TYPE_INT_RGB);

				g2d = (Graphics2D) productoImgeMinResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeMinResRenderd.getWidth(), productoImgeMinResRenderd.getHeight());
				g2d.drawImage(productoImgeMinRes, 0, 0, null);
				ImageIO.write(productoImgeMinResRenderd, "jpeg", new FileOutputStream(dirImages + File.separator + "MIN_" + nombrearchivo));
				System.err.println("\t\tOk exploded MinRes:");

				Image productoImgeIconRes = productoImge.getScaledInstance(targetIconImageWidth, targetIconImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeIconResRenderd = new BufferedImage(targetIconImageWidth, targetIconImageHeight, BufferedImage.TYPE_INT_RGB);

				g2d = (Graphics2D) productoImgeIconResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeIconResRenderd.getWidth(), productoImgeIconResRenderd.getHeight());
				g2d.drawImage(productoImgeIconRes, 0, 0, null);
				ImageIO.write(productoImgeIconResRenderd, "jpeg", new FileOutputStream(dirImages + File.separator + "ICO_" + nombrearchivo));
				System.err.println("\t\tOk exploded Icono:");
				contExtraidos++;
			}
			rsMultimedio.close();
			psMultimedio.close();

			System.out.println("-->>contExtraidos=" + contExtraidos);
			conn.close();

		} catch (IOException ex) {
			ex.printStackTrace(System.err);
			return;
		} catch (SQLException ex) {
			ex.printStackTrace(System.err);
			return;
		}
	}

	public BufferedImage drawVerticalsBarsgradient(Image imageToDraw, float fadeWidth, Color barColor) {
		int imageWidth = imageToDraw.getWidth(null);
		int imageHeight = imageToDraw.getHeight(null);

		BufferedImage reflection = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D rg = reflection.createGraphics();
		rg.drawImage(imageToDraw, 0, 0, null);
		rg.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		Color barColorTransparent = new Color(barColor.getRed() / 255.0f, barColor.getGreen() / 255.0f, barColor.getBlue() / 255.0f, 0.0f);
		rg.setPaint(
				new GradientPaint(
						0, 0, barColorTransparent,
						imageWidth * fadeWidth, 0, barColor));
		rg.fillRect(0, 0,
				(int) (imageWidth * fadeWidth), imageHeight);

		rg.setPaint(
				new GradientPaint(
						imageWidth, 0, barColorTransparent,
						imageWidth - imageWidth * fadeWidth, 0, barColor));
		rg.fillRect((int) (imageWidth - imageWidth * fadeWidth), 0,
				imageWidth, imageHeight);
		rg.dispose();
		return reflection;
	}

	private void saveScaledJpegImage(BufferedImage src, int w, int h, String destFile, ImageWriter jpegImageWriter, ImageWriteParam param) throws IOException {
		Image productoImgeMedRes = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		BufferedImage outImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2ds = (Graphics2D) outImg.getGraphics();
		g2ds.setColor(Color.WHITE);
		g2ds.fillRect(0, 0, outImg.getWidth(), outImg.getHeight());
		g2ds.drawImage(productoImgeMedRes, 0, 0, null);
		ImageOutputStream ios = ImageIO.createImageOutputStream(new FileOutputStream(destFile));
		jpegImageWriter.setOutput(ios);
		jpegImageWriter.write(null, new IIOImage(outImg, null, null), param);
	}

	public static void main(String[] args) {
		CrearImagenesDePruebaEnMultimedio cidpm = new CrearImagenesDePruebaEnMultimedio();
		if (args.length == 0) {
			System.err.println(" -u  url  user  password  dirImages  @PRODUCTO_CB@.JPG finalOutputDir");
			System.err.println(" -x  url  user  password  dirImages  baseDir");

			System.exit(1);
		}
		System.out.println("==>> ");
		if (args[0].equals("-u")) {
			cidpm.crearImagesnes(args[1], args[2], args[3], args[4], args[5]);
		} else if (args[0].equals("-x")) {
			cidpm.extraerImagenes(args[1], args[2], args[3], args[4], args[5]);
		}

	}
}
