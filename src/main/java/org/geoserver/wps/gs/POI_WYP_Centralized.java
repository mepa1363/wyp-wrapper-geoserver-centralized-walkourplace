package org.geoserver.wps.gs;

import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.process.gs.GSProcess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

@DescribeProcess(title = "POI-WPS", description = "POI WPS - Crow-flight, WalkYourPlace approach")
public class POI_WYP_Centralized implements GSProcess {

	@DescribeResult(name = "POIResult", description = "POI result")
	public String execute(
			@DescribeParameter(name = "StartPoint", description = "Walking start point") String start_point,
			@DescribeParameter(name = "Radius", description = "Walking radius") String radius) {
		return CallPOIService(start_point, radius);
	}

	public static String CallPOIService(String start_point, String radius) {
		URL url;
		String line;
		HttpURLConnection connection;
		StringBuilder sb = new StringBuilder();

		try {
			url = new URL("http://127.0.0.1:6365/poi?start_point="
					+ start_point + "&radius=" + radius);
			connection = (HttpURLConnection) url.openConnection();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

			rd.close();

			connection.disconnect();

		} catch (Exception e) {
			System.out.println("Errors...");
		}

		return sb.toString();
	}
}
