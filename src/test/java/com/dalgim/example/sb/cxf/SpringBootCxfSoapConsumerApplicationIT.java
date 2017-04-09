package com.dalgim.example.sb.cxf;

import com.dalgim.namespace.personservice.PersonRegistry;
import com.dalgim.namespace.personservice.general.Void;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootCxfSoapConsumerApplicationIT {

	@Autowired
	PersonRegistry personRegistry;

	@Test
	public void contextLoads() {
		personRegistry.getAllPersonInfo(new Void());
        assert true;
	}

}
