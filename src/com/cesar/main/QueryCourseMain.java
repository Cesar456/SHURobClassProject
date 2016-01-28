package com.cesar.main;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cesar.service.ParseCourse;
import com.cesar.service.RefreshCourse;
import com.cesar.util.SimpleMailSender;

public class QueryCourseMain {
	public static void main(String[] args) {
		SimpleMailSender sender = SimpleMailSender.getInstance();
		RefreshCourse refreshCourse = new RefreshCourse();
		refreshCourse.login();
		int i = 0;
		while (true) {
			String time = new Date(System.currentTimeMillis()).toLocaleString();
			System.out.println("第"+i++ +"次刷新,当前时间为:"+time);
			String html1 = refreshCourse.getCoursePage();
			if (html1 != null) {
				boolean isEmpty = ParseCourse.isEmpty(html1);
				if (isEmpty) { //还有空余
					String html2 = refreshCourse.chooseCourse();
					if(html2!=null){
						boolean ischoose = ParseCourse.isChoosed(html2);
						if(ischoose){
							System.out.println("选课成功");
							try {
								sender.send(
										"845330810@qq.com",
										"选课成功，本消息发送时间为"
												+ time);
							} catch (AddressException e) {
								e.printStackTrace();
							} catch (MessagingException e) {
								e.printStackTrace();
							}
							break;
						}
					}
					System.out.println("选课失败，重新刷新");
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
