package de.i3mainz.chronontology.restconfig;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

public class ResponseGZIP {

	public static Response setResponse(String acceptEncoding, String output) {
		if (acceptEncoding.contains("gzip")) {
			// set outputstream
			final String OUTSTRING_FINAL = output;
			StreamingOutput stream = new StreamingOutput() {
				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException {
					try {
						output = GZIP(OUTSTRING_FINAL, output);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			};
			return Response.ok(stream).header("Content-Type", "application/json;charset=UTF-8").header("Content-Encoding", "gzip").build();
		} else {
			return Response.ok(output).header("Content-Type", "application/json;charset=UTF-8").build();
		}
	}

	private static OutputStream GZIP(String input, OutputStream baos) throws IOException {
		try (GZIPOutputStream gzos = new GZIPOutputStream(baos)) {
			gzos.write(input.getBytes("UTF-8"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return baos;
	}

}
