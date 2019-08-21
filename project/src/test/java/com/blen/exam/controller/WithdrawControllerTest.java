package com.blen.exam.controller;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.blen.exam.data.req.WithdrawalCreateParam;
import com.blen.exam.data.req.WithdrawalFetchParam;
import com.blen.exam.data.req.WithdrawalPayParam;
import com.blen.exam.service.WithdrawService;
import com.alibaba.fastjson.JSONObject;

@RunWith(PowerMockRunner.class)
public class WithdrawControllerTest {
  private static final String COMMON_PATH = "/exam/withdrawal";
  private MockMvc mockMvc;
  @Mock private WithdrawService withdrawService;

  @Before
  public void initMocks() {
    WithdrawController withdrawController = new WithdrawController();
    this.mockMvc = MockMvcBuilders.standaloneSetup(withdrawController).build();
    ReflectionTestUtils.setField(withdrawController, "withdrawService", withdrawService);
  }

  @Test
  public void create() throws Exception {
    when(withdrawService.create(any())).thenReturn(1L);
    WithdrawalCreateParam createParam =
        WithdrawalCreateParam.builder()
            .userId(1000L)
            .currency("btc")
            .amount(BigDecimal.valueOf(10))
            .fee(BigDecimal.valueOf(0.3))
            .address("address")
            .build();
    String requestJson = JSONObject.toJSONString(createParam);
    mockMvc
        .perform(
            post(COMMON_PATH + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  public void fetch() throws Exception {
    WithdrawalFetchParam fetchParam = WithdrawalFetchParam.builder().userId(2000L).id(2L).build();
    String requestJson = JSONObject.toJSONString(fetchParam);
    mockMvc
        .perform(
            post(COMMON_PATH + "/fetch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  public void query() throws Exception {
    WithdrawalPayParam payParam =
        WithdrawalPayParam.builder()
            .id(3L)
            .txhash("txhash")
            .height(300L)
            .realFee(BigDecimal.valueOf(0.4))
            .build();
    String requestJson = JSONObject.toJSONString(payParam);
    mockMvc
        .perform(
            post(COMMON_PATH + "/pay").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk());
  }
}
