package cn.zeppin.product.score;

import net.kernal.spiderman.Config;
import net.kernal.spiderman.Spiderman;
import net.kernal.spiderman.kit.XMLConfBuilder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = "cn.zeppin.product.score.mapper")
public class Application extends SpringBootServletInitializer{
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		Task();
	}
	
	public static void Task() {
//		Resource resource = new ClassPathResource("spiderman/tencent/urls.txt");
//		try {
//			File file = resource.getFile();
//			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//			bw.write("");
//			bw.close();
//		}catch(Exception e){
//			return;
//		}

		final String xml = "spiderman/tencent/detail.xml";
		final Config conf = new XMLConfBuilder(xml).build();
		new Spiderman(conf).go();//启动，别忘记看控制台信息哦，结束之后会有统计信息的
	}

}
