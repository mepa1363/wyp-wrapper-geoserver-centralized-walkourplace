package org.geoserver.wps.gs;

import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.process.gs.GSProcess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

@DescribeProcess(title = "Management-WPS", description = "Management WPS - Crow-flight, WalkYourPlace approach")
public class Management_WYP implements GSProcess {

	@DescribeResult(name = "Accessibility Score", description = "Accessibility score calculated based on crow-flight approach(WalkYourPlace)")
	public String execute(
			@DescribeParameter(name = "StartPoint", description = "Walking start point") String start_point,
			@DescribeParameter(name = "Radius", description = "Walking radius") String radius,
			@DescribeParameter(name = "DistanceDecayFunction", description = "Distance Decay Function") String distance_decay_function) {
		return CallManagementService(start_point, radius,
				distance_decay_function);
	}

	public static String CallManagementService(String start_point,
			String radius, String distance_decay_function) {
		URL url;
		String line;
		HttpURLConnection connection;
		StringBuilder sb = new StringBuilder();
		String url_string;

		try {

			url_string = "http://127.0.0.1:6363/management?start_point="
					+ start_point + "&radius=" + radius
					+ "&distance_decay_function=" + distance_decay_function;

			url = new URL(url_string);

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
