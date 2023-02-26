package com.cogiteo.canvas;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

//import org.junit.Test;
import org.apache.tomcat.util.http.parser.MediaType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.UtilisateurDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.controller.UtilisateurController;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.ConnectionDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.InscriptionDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.UpdateDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@WebMvcTest(UtilisateurController.class)
 class UtilisateurControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UtilisateurService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    // Init MockMvc Object and build

    /**
     * @throws Exception
     */
    @Test
    public void getUsersWithValidToken() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("1KoYOSiUl5AzxC55SBDxg6WloB_gt22i");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        when(service.getUserByToken("1KoYOSiUl5AzxC55SBDxg6WloB_gt22i")).thenReturn(utilisateur);
      

        String urlLocalHost = "http://localhost:8100/api/utilisateur?token=1KoYOSiUl5AzxC55SBDxg6WloB_gt22i";
       
        String usersAsJson = new ObjectMapper().writeValueAsString(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get(urlLocalHost)
                .accept("application/json")
                .contentType("application/json")
                .content(usersAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.OK.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }

    @Test
    public void getUsersWithInValidToken() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("1KoYOSiUl5AzxC55SBDx22i");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        when(service.getUserByToken("1KoYOSiUl5AzxC55SBDx22i")).thenReturn(null);
       
      

        String urlLocalHost = "http://localhost:8100/api/utilisateur?token=1KoYOSiUl5AzxC55SBDx22i";
       
        String usersAsJson = new ObjectMapper().writeValueAsString(null);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get(urlLocalHost)
                .accept("application/json")
                .contentType("application/json")
                .content(usersAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.NO_CONTENT.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }

    @Test
    public void registerWithUnRegisteredUserTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String url = "http://localhost:8100/api/utilisateur/register";

        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setNom("testName");
        inscriptionDto.setPrenom("testFirstname");
        inscriptionDto.setEstAdmin(false);
        inscriptionDto.setMail("testFirstname@gamil.com");
        inscriptionDto.setPassword("TestPassword");
        inscriptionDto.setEntreprise("testEntreprise");

        UtilisateurDto utilisateurDto = new UtilisateurDto();
        utilisateurDto.setEntreprise(inscriptionDto.getEntreprise());
        utilisateurDto.setEstAdmin(inscriptionDto.getEstAdmin());
        utilisateurDto.setMail(inscriptionDto.getMail());
        utilisateurDto.setNom(inscriptionDto.getNom());
        utilisateurDto.setPrenom(inscriptionDto.getPrenom());
        utilisateurDto.setToken("testToken");

        Utilisateur registerUser = new Utilisateur();
        registerUser.setPrenom(inscriptionDto.getPrenom());
        registerUser.setNom(inscriptionDto.getNom());
        registerUser.setMail(inscriptionDto.getMail());
        registerUser.setEntreprise(inscriptionDto.getEntreprise());
        registerUser.setPassword(bCryptPasswordEncoder.encode(inscriptionDto.getPassword()));
        registerUser.setEstAdmin(false);
        registerUser.setToken(utilisateurDto.getToken());
        registerUser.setGUIDUtilisateur(1L);

        String registerAsJson = new ObjectMapper().writeValueAsString(inscriptionDto);

        when(service.IsExistedUser(inscriptionDto.getMail())).thenReturn(false);
        when(service.register(any(Utilisateur.class))).thenReturn(registerUser);

        assertEquals("mais non ", service.register(registerUser), registerUser);
        verify(service, times(1)).register((refEq(registerUser)));

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.OK.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);
        verify(service, times(1)).register((refEq(registerUser)));

    }
 
    @Test
    public void registerWithRegisteredUserTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/register";

        InscriptionDto inscriptionDto = new InscriptionDto();
        inscriptionDto.setNom("testName");
        inscriptionDto.setPrenom("testFirstname");
        inscriptionDto.setEstAdmin(false);
        inscriptionDto.setMail("testFirstname@gamil.com");
        inscriptionDto.setPassword("TestPassword");
        inscriptionDto.setEntreprise("testEntreprise");


        String registerAsJson = new ObjectMapper().writeValueAsString(inscriptionDto);

        when(service.IsExistedUser(inscriptionDto.getMail())).thenReturn(true);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.CONFLICT.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);
       // verify(service, times(0)).register((refEq(registerUser)));

    }
 

    @Test
    public void loginWithUnRegisteredUserTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/login";

        ConnectionDto connectionDto = new ConnectionDto();
        connectionDto.setMail("testFirstname@gmail.com");
        connectionDto.setPassword("testPassword");


        String registerAsJson = new ObjectMapper().writeValueAsString(connectionDto);

        when(service.IsExistedUser(connectionDto.getMail())).thenReturn(false);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.UNAUTHORIZED.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 
    @Test
    public void loginWithRegisteredCorrectPasswordAndEmailTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/login";

        ConnectionDto connectionDto = new ConnectionDto();
        connectionDto.setMail("testFirstname@gmail.com");
        connectionDto.setPassword("testPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode(connectionDto.getPassword()));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(connectionDto);

        when(service.IsExistedUser(connectionDto.getMail())).thenReturn(true);
        when(service.getUserByMail(connectionDto.getMail())).thenReturn(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.OK.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 

    @Test
    public void loginWithRegisteredIncorrectPasswordTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/login";

        ConnectionDto connectionDto = new ConnectionDto();
        connectionDto.setMail("testFirstname@gmail.com");
        connectionDto.setPassword("testPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("WrongPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(connectionDto);

        when(service.IsExistedUser(connectionDto.getMail())).thenReturn(true);
        when(service.getUserByMail(connectionDto.getMail())).thenReturn(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.UNAUTHORIZED.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 
    @Test
    public void loginWithRegisteredIncorrectEmailTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/login";

        ConnectionDto connectionDto = new ConnectionDto();
        connectionDto.setMail("testFirstname@gmail.com");
        connectionDto.setPassword("testPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("WrongPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(connectionDto);

        when(service.IsExistedUser(connectionDto.getMail())).thenReturn(false);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.UNAUTHORIZED.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 

    @Test
    public void updateWithUnfoundRegisteredUserTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/update";

        UpdateDto updateDto = new UpdateDto();
        updateDto.setEntreprise("Sopra");
        updateDto.setNom("TOURE");
        updateDto.setPrenom("Alasco");
        updateDto.setToken("toktok");
        updateDto.setEstAdmin(true);
        updateDto.setMail("testFirstname@gmail.com");
        updateDto.setNewPassword("MyNewPassword");
        updateDto.setOlPassword("MyOldPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("MyOldPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(updateDto);

        when(service.getUserByMail(updateDto.getMail())).thenReturn(null);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.NOT_FOUND.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 

    @Test
    public void updateWithIncorrectRegisteredUserTokenTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/update";

        UpdateDto updateDto = new UpdateDto();
        updateDto.setEntreprise("Sopra");
        updateDto.setNom("TOURE");
        updateDto.setPrenom("Alasco");
        updateDto.setToken("toktoktictac");
        updateDto.setEstAdmin(true);
        updateDto.setMail("testFirstname@gmail.com");
        updateDto.setNewPassword("MyNewPassword");
        updateDto.setOlPassword("MyOldPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("MyOldPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(updateDto);

        when(service.getUserByMail(updateDto.getMail())).thenReturn(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.UNAUTHORIZED.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 

    @Test
    public void updateWithIncorrectRegisteredUserOldPasswordTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/update";

        UpdateDto updateDto = new UpdateDto();
        updateDto.setEntreprise("Sopra");
        updateDto.setNom("TOURE");
        updateDto.setPrenom("Alasco");
        updateDto.setToken("toktoktictac");
        updateDto.setEstAdmin(true);
        updateDto.setMail("testFirstname@gmail.com");
        updateDto.setNewPassword("MyNewPassword");
        updateDto.setOlPassword("MyOldPasswordWrong");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("mail@mail.mail");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("MyOldPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(updateDto);

        when(service.getUserByMail(updateDto.getMail())).thenReturn(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.UNAUTHORIZED.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 
    @Test
    public void updateWithRegisteredUserTest() throws Exception {

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        String url = "http://localhost:8100/api/utilisateur/update";

        UpdateDto updateDto = new UpdateDto();
        updateDto.setEntreprise("Sopra");
        updateDto.setNom("TOURE");
        updateDto.setPrenom("Alasco");
        updateDto.setToken("toktok");
        updateDto.setEstAdmin(true);
        updateDto.setMail("testFirstname@gmail.com");
        updateDto.setNewPassword("MyNewPassword");
        updateDto.setOlPassword("MyOldPassword");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("toure");
        utilisateur.setPrenom("allassane");
        utilisateur.setMail("testFirstname@gmail.com");
        utilisateur.setToken("toktok");
        utilisateur.setEntreprise("SDH");
        utilisateur.setEstAdmin(true);
        utilisateur.setGUIDUtilisateur(1L);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        utilisateur.setPassword(bCryptPasswordEncoder.encode("MyOldPassword"));
        
        String registerAsJson = new ObjectMapper().writeValueAsString(updateDto);

        when(service.getUserByMail(updateDto.getMail())).thenReturn(utilisateur);

        when((service.update(any(Utilisateur.class)))).thenReturn(utilisateur);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
                .accept("application/json")
                .contentType("application/json")
                .content(registerAsJson);

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();

        int actualResult = mvcResult.getResponse().getStatus();
        int expectedResult = HttpStatus.OK.value();

        assertEquals("Result not as expected!", expectedResult, actualResult);

    }
 

}
