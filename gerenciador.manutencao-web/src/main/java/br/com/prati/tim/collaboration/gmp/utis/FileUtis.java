package br.com.prati.tim.collaboration.gmp.utis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class FileUtis {

	public static byte[] compress(byte[] dataToCompress) throws IOException {
		
		Deflater deflater = new Deflater();
		
		deflater.setInput(dataToCompress);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(dataToCompress.length);
		deflater.finish();
		
		byte[] buffer = new byte[1024];
		
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); 
			outputStream.write(buffer, 0, count);
		}
		
		outputStream.close();
		
		byte[] compressed = outputStream.toByteArray();
		
		System.out.println("Original....: " + dataToCompress.length / 1024 + " Kb");
		System.out.println("Compressed..: " + compressed.length / 1024 + " Kb");
		
		return compressed;
	}
	
	public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
		Inflater inflater = new Inflater();
		
		inflater.setInput(data);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		
		byte[] buffer = new byte[1024];
		
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		
		outputStream.close();
		
		byte[] decompressed = outputStream.toByteArray();
		
		System.out.println("Original......: " + data.length);
		System.out.println("Decompressed..: " + decompressed.length);
		
		return decompressed;
	}

}
