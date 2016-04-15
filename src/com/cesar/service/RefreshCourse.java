package com.cesar.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class RefreshCourse {
	
	private static final String getValidCode = "http://xk.autoisp.shu.edu.cn:8080/Login/GetValidateCode?%20%20+%20GetTimestamp()";
	private static final String loginUrl = "http://xk.autoisp.shu.edu.cn:8080/";
	private static final String queryCourse = "http://xk.autoisp.shu.edu.cn:8080/StudentQuery/CtrlViewQueryCourse";
	private static final String chooseCourse = "http://xk.autoisp.shu.edu.cn:8080/CourseSelectionStudent/CtrlViewOperationResult";
	
	private static final String userName = "**";
	private static final String passWord = "**";
	private static String valid = "";
	
	private Scanner scanner = new Scanner(System.in);
	private HttpClient httpClient = new DefaultHttpClient();
	private HttpPost post;
	private HttpResponse response;

	public void login() {
		HttpGet get = new HttpGet(getValidCode);
		try {
			response = httpClient.execute(get);
			InputStream in = response.getEntity().getContent();
			streamToPng(in);
			System.out.println("请输入验证码");
			valid = scanner.next(); 
			Map<String,String> params = new HashMap<String, String>();
			params.put("txtUserName", userName);
			params.put("txtPassword", passWord);
			params.put("txtValiCode", valid);
			post = postForm(loginUrl, params);
			response = httpClient.execute(post);
			EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCoursePage(){
		Map<String, String> params = new HashMap<>(); 
		params.put("CourseNo", "15576090");
		params.put("CourseName", "");
		params.put("TeachNo", "");
		params.put("TeachName", "");
		params.put("CourseTime", "");
		params.put("NotFull", "false");
		params.put("Credit", "4");
		params.put("Campus", "2");
		params.put("Enrolls", "");
		params.put("DataCount", "0");
		params.put("MinCapacity", "");
		params.put("MaxCapacity", "");
		params.put("PageIndex", "1");
		params.put("PageSize", "20");
		params.put("FunctionString", "InitPage");
		post = postForm(queryCourse, params);
		try {
			response = httpClient.execute(post);
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回选课的结果html
	 * @return
	 */
	public String chooseCourse(){
		Map<String, String> params = new HashMap<>(); 
		params.put("IgnorClassMark", "False");
		params.put("IgnorCourseGroup", "False");
		params.put("IgnorCredit", "False");
		params.put("StudentNo", userName);
		params.put("ListCourse[0].CID", "15576090");
		params.put("ListCourse[0].TNo", "2000");
		params.put("ListCourse[0].NeedBook", "false");
		params.put("ListCourse[1].CID", "");
		params.put("ListCourse[1].TNo", "");
		params.put("ListCourse[1].NeedBook", "false");
		params.put("ListCourse[2].CID", "");
		params.put("ListCourse[2].TNo", "");
		params.put("ListCourse[2].NeedBook", "false");
		params.put("ListCourse[3].CID", "");
		params.put("ListCourse[3].TNo", "");
		params.put("ListCourse[3].NeedBook", "false");
		params.put("ListCourse[4].CID", "");
		params.put("ListCourse[4].TNo", "");
		params.put("ListCourse[4].NeedBook", "false");
		params.put("ListCourse[5].CID", "");
		params.put("ListCourse[5].TNo", "");
		params.put("ListCourse[5].NeedBook", "false");
		post = postForm(chooseCourse, params);
		try {
			response = httpClient.execute(post);
			return EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void streamToPng(InputStream in){
		try {
			FileOutputStream out = new FileOutputStream(new File("C:\\Users\\Cesar\\Desktop\\1.png"));
			byte[] b = new byte[1024];
			int t = 0;
			while ((t = in.read(b))>0) {
				out.write(b,0,t);
			}
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static HttpPost postForm(String url, Map<String, String> params) {
		HttpPost resultpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			resultpost.setEntity(new UrlEncodedFormEntity(nvps));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return resultpost;
	}

}
