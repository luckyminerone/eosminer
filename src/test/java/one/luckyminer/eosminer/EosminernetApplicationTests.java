package one.luckyminer.eosminer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import one.luckyminer.eosminer.runner.AppRunner;
import one.luckyminer.eosminer.service.EosBlockService;

@SpringBootTest
class EosminernetApplicationTests {

	@Test
	void contextLoads() {
		EosBlockService eosBlockService = new EosBlockService(null);
		AppRunner apprunner = new AppRunner(eosBlockService);
		try {
			apprunner.run("test");
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

}
