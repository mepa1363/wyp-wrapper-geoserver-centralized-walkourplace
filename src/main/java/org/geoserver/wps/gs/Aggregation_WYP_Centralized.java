package org.geoserver.wps.gs;

import org.apache.commons.httpclient.util.URIUtil;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.process.gs.GSProcess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;

@DescribeProcess(title = "Aggreagtaion-WPS", description = "Aggregation WPS - Crow-flight, WalkYourPlace approach")
public class Aggregation_WYP_Centralized implements GSProcess {

	@DescribeResult(name = "AggregationResult", description = "Aggregated data")
	public String execute(
			@DescribeParameter(name = "POI", description = "POI data") String poi,
			@DescribeParameter(name = "Crime", description = "Crime data") String crime,
			@DescribeParameter(name = "StartPoint", description = "Walking start point") String start_point,
			@DescribeParameter(name = "Radius", description = "Walking radius") String radius,
			@DescribeParameter(name = "DistanceDecayFunction", description = "Distance Decay Function") String distance_decay_function) {
		return CallAggregationService(start_point, radius, poi, crime,
				distance_decay_function);
	}

	public static String CallAggregationService(String start_point,
			String radius, String poi, String crime,
			String distance_decay_function) {
		URL url;
		String line;
		HttpURLConnection connection;
		StringBuilder sb = new StringBuilder();
		String url_string;

		try {
			url_string = "http://127.0.0.1:6364/aggregation?start_point="
					+ start_point + "&radius=" + radius + "&poi=" + poi
					+ "&crime=" + crime + "&distance_decay_function="
					+ distance_decay_function;
			url_string = URIUtil.encodeQuery(url_string);

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
