package com.blen.exam;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {"test"})
@SpringBootTest(classes = ExamApplicationTests.class)
@MapperScan("com.blen.exam.dao")
public class BaseTest {

  @Value("${spring.h2.console.path}")
  private String h2;

  @Ignore
  @Test
  public void getValue() {
    assertTrue(h2.contains("h2"));
  }

}
