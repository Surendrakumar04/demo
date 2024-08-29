package com.example.demo;
import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User(  "John Doe", 35,"john.doe@example.com" );
        mockMvc.perform(post("http://localhost:8080/user/save")
                        .contentType("application/json")
                        .content(asJsonString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserByName() throws Exception {
        User user = new User( "John Doe", 35,"john.doe@example.com");
        when(userService.findUserByName("John Doe")).thenReturn(user);
        mockMvc.perform(get("http://localhost:8080/user/getByName/John Doe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("John Doe")).exists())
                .andExpect(jsonPath("$.email", is("john.doe@example.com")).exists());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(  "John Doe", 35,"john.doe@example.com" ));
        users.add(new User(  "Jane Doe", 30,"jane.doe@example.com" ));
        when(userService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("http://localhost:8080/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].name", is("John Doe")).exists())
                .andExpect( jsonPath("$[0].email", is("john.doe@example.com")).exists())
                .andExpect( jsonPath("$[1].name", is("Jane Doe")).exists())
                .andExpect( jsonPath("$[1].email", is("jane.doe@example.com")).exists());
    }

    @Test
    public void testGenerateRandomUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/user/generateRandomUser"))
                .andExpect(status().isOk());
    }

    private static String asJsonString( User obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
