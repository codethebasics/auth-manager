package br.com.codethebasics.authmanager.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste das configurações de segurança
 *
 * @author codethebasics
 */
@WebMvcTest
class SecurityConfigTest {

    @Autowired
    WebApplicationContext webAppContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .apply(springSecurity())
                .build();
    }

    /**
     * Testa acesso a um recurso público
     */
    @Test
    @DisplayName("When access public resource, then return 200 [OK]")
    public void whenAccessPublicResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/healthcheck"))
                .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso privado por um usuário não autenticado
     */
    @Test
    @DisplayName("Given anonymous user, when access private resource, then return 401 [Unauthorized]")
    public void givenAnonymousUser_whenAccessPrivateResource_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testa acesso a um recurso privado por um usuário autenticado
     */
    @Test
    @WithMockUser("sa")
    @DisplayName("Given user, when access private resource, then return 200 [OK]")
    public void givenUser_whenAccessPrivateResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso privado por um usuário autenticado via http basic
     */
    @Test
    @DisplayName("Given user, when access private resource with http basic authentication, then return 200 [OK]")
    public void givenHttpBasicUser_whenAccessPrivateResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/users").with(httpBasic("testuser", "testpass")))
                .andExpect(status().isOk());
    }

    /**
     * Testa acesso de um usuário comum a um recurso que requer privilégios administrativos
     */
    @Test
    @DisplayName("Given common user, when access admin resource, then return unauthorized [401]")
    public void givenCommonUser_whenAccessAdminResource_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/users/save"))
                .andExpect(status().isUnauthorized());
    }
}