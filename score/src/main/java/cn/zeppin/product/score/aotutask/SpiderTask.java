package cn.zeppin.product.score.aotutask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.kernal.spiderman.Config;
import net.kernal.spiderman.Spiderman;
import net.kernal.spiderman.kit.XMLConfBuilder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpiderTask {
	
//	@Scheduled(cron="40 0 0  * * * ")
	public void TencentMainTask() {
		Resource resource = new ClassPathResource("spiderman/tencent/urls.txt");
		try {
			File file = resource.getFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("");
			bw.close();
		}catch(Exception e){
			return;
		}
		
		final String xml = "spiderman/tencent/main.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
	
//	@Scheduled(cron="40 5 0  * * * ")
	public void TencentDetailTask() {
		final String xml = "spiderman/tencent/detail.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
	
//	@Scheduled(cron="40 10 0  * * * ")
	public void SinaMainTask() {
		Resource resource = new ClassPathResource("spiderman/sina/urls.txt");
		try {
			File file = resource.getFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("");
			bw.close();
		}catch(Exception e){
			return;
		}
		
		final String xml = "spiderman/sina/main.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
	
//	@Scheduled(cron="40 15 0  * * * ")
	public void SinaDetailTask() {
		final String xml = "spiderman/sina/detail.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
	
//	@Scheduled(cron="40 20 0  * * * ") 
	public void NeteasyMainTask() {
		Resource resource = new ClassPathResource("spiderman/neteasy/urls.txt");
		try {
			File file = resource.getFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("");
			bw.close();
		}catch(Exception e){
			return;
		}
		
		final String xml = "spiderman/neteasy/main.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
	
//	@Scheduled(cron="40 25 0  * * * ")
	public void NeteasyDetailTask() {
		final String xml = "spiderman/neteasy/detail.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();
	}
}