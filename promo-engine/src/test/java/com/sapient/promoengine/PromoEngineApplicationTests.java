package com.sapient.promoengine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sapient.promoengine.service.PromotionEngineService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PromoEngineApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	PromotionEngineService promoEngineService;
	
	@Test
	public void contextLoads() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/promos")
			.accept(MediaType.APPLICATION_JSON)).andReturn();
	}

}
