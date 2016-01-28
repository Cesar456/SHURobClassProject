package com.cesar.service;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ParseCourse {

	/**
	 * 判断课程是否选满
	 * 
	 * @param s
	 * @return true表示已经被选完
	 */
	public static boolean isEmpty(String s) {
		Document document = Jsoup.parse(s);
		try {
			Elements elements = document.select("tr[id=0]").get(0).select("td");
			int max = Integer.parseInt(elements.get(7).text());
			int num = Integer.parseInt(elements.get(8).text());
			if (max == num) {
				return false;
			}
			return true;
		} catch (Exception e) {
			try {
				Files.append(e.getStackTrace() + "\n" + s, new File("log.txt"),
						Charsets.UTF_8);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 判断选课是否成功
	 * @param html
	 * @return
	 */
	public static boolean isChoosed(String html) {
		String s = Jsoup.parse(html).text();
		System.out.println(s);
		if (s.contains("选课失败课程")&&!s.contains("选课成功课程")) {
			return false;
		} else if(s.contains("选课成功课程")) {
			return true;
		}
		return false;
	}
}
