package com.isoco.xlike.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class decompressFile {

	/**
	 * @param args
	 */

	public String decompress(InputStream input) throws Exception {

		// opens the compressed file
		GZIPInputStream in = new GZIPInputStream(input);
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		// Transfer bytes from the compressed file to the output file
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) > 0) {
			output.write(buffer, 0, len);
		}

		in.close();
		output.close();

		return output.toString();
	}

	public void UnZip(String input) {
		final int BUFFER = 2048;

		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(input);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];

				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String jsiDecompressTXT() {
		try {
			URL url = new URL("http://newsfeed.ijs.si/stream/txt?after=2012-03-08T09:00:00Z"); // Abro URL
			//URL url = new URL("http://newsfeed.ijs.si/stream/txt"); // Abro URL
			InputStream is = url.openStream(); // Abro InputStream desde URL

			return decompress(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// File
			InputStream ddfichero = new FileInputStream(new File(
					"resources/test_jsi/rych-en-2012-02-29T00-20-44Z.xml.gz"));

			InputStream ddfichero2 = new FileInputStream(new File(
					"resources/test_jsi/txt-all-2012-02-28T23-58-44Z.xml.gz"));
			decompressFile dds = new decompressFile();
			// System.out.println(dds.decompress(ddfichero2));

			// URL
			URL url = new URL("http://newsfeed.ijs.si/stream/txt"); // Abro URL
			InputStream is = url.openStream(); // Abro InputStream desde URL
			System.out.println("File - Descompress");
			System.out.println(dds.decompress(is));
		} catch (Exception ex) {
			System.out.println("Main - decompressFile" + ex);
		}
	}

}
