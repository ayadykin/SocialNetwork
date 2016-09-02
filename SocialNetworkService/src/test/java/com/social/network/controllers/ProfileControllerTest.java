package com.social.network.controllers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.social.network.config.RootServiceConfig;
import com.social.network.config.RedisConfig;
import com.social.network.config.SecurityConfig;
import com.social.network.domain.config.HibernateConfig;
import com.social.network.dto.profile.FullProfileDto;
import com.social.network.services.ProfileService;

/**
 * Created by Yadykin Andrii on 5/27/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootServiceConfig.class, HibernateConfig.class,
        SecurityConfig.class, RedisConfig.class}, loader = AnnotationConfigContextLoader.class)
public class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    public void testGetProfile() throws Exception {
        when(profileService.getProfile()).thenReturn(new FullProfileDto("Name", "LastName", "City", "Country", Locale.US, false));

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile/userProfile"))
                .andExpect(model().attribute("profileDto",
                        allOf(hasProperty("firstName", is("Name")),
                                hasProperty("lastName", is("LastName")),
                                hasProperty("city", is("City")),
                                hasProperty("country", is("Country")))));
    }
}
