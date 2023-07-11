package com.sc.accounting_smart_cookies.controller;

import com.sc.accounting_smart_cookies.TestDocumentInitializer;
import com.sc.accounting_smart_cookies.dto.ClientVendorDTO;
import com.sc.accounting_smart_cookies.entity.User;
import com.sc.accounting_smart_cookies.entity.common.UserPrincipal;
import com.sc.accounting_smart_cookies.enums.ClientVendorType;
import com.sc.accounting_smart_cookies.service.*;
import com.sc.accounting_smart_cookies.service.implementation.SecurityServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientVendorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ClientVendorService service;


    static UserDetails userDetails;
    static User user;

    @BeforeAll
    static void setUpAll() {
        user = TestDocumentInitializer.getUserEntity("Admin");
        userDetails = new UserPrincipal(user);
    }


    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void list() throws Exception {

        // given
        ClientVendorDTO clientVendor = TestDocumentInitializer.getClientVendor(ClientVendorType.CLIENT);
        List<ClientVendorDTO> clients = Arrays.asList(clientVendor);
        doReturn(clients).when(service).findVendorsByType(ClientVendorType.CLIENT);
        // when
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/clientVendors/list")
                                      .with(user(userDetails)))
                                      .andDo(print())
                                      .andExpect(status().isOk()).andReturn();

        // then
        ModelAndView mav = result.getModelAndView();
        assert mav != null;
        ModelAndViewAssert.assertViewName(mav, "clientVendor/clientVendor-list");
    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void commonAttributes() {
    }
}