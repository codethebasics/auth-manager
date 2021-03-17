package br.com.codethebasics.authmanager.config;

import br.com.codethebasics.authmanager.entity.User;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @DisplayName("Given anonymous user, when access public resource, then return 200 [OK]")
    public void givenUser_whenAccessPublicResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/public/healthcheck"))
                .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso protegido por um usuário não autenticado
     */
    @Test
    @DisplayName("Given anonymous user, when access protected resource, then return 401 [Unauthorized]")
    public void givenAnonymousUser_whenAccessProtectedResource_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/protected/users"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testa acesso a um recurso privado por um usuário não autenticado
     */
    @Test
    @DisplayName("Given anonymous user, when access private resource, then return 401 [Unauthorized]")
    public void givenAnonymousUser_whenAccessPrivateResource_thenReturn401() throws Exception {
        mockMvc.perform(put("/api/v1/private/manager/user/block/1", new User()))
            .andExpect(status().isUnauthorized());
    }

    /**
     * Testa acesso a um recurso protegido por um usuário comum autenticado
     */
    @Test
    @WithMockUser(username = "sa", roles = {"USER"})
    @DisplayName("Given common user, when access protected resource, then return 200 [OK]")
    public void givenCommonUser_whenAccessProtectedResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/protected/users"))
            .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso privado por um usuário comum autenticado
     */
    @Test
    @WithMockUser(username = "sa", roles = {"USER"})
    @DisplayName("Given common user, when access private resource, then return 403 [Forbidden]")
    public void givenCommonUser_whenAccessPrivateResource_thenReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/private/manager"))
            .andExpect(status().isForbidden());
    }

    /**
     * Testa acesso a um recurso protegido por um usuário admin autenticado
     */
    @Test
    @WithMockUser(username = "sa", roles = {"ADMIN"})
    @DisplayName("Given admin user, when access protected resource, then return 200 [OK]")
    public void givenAdminUser_whenAccessProtectedResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/protected/users"))
            .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso privado por um usuário admin autenticado
     */
    @Test
    @WithMockUser(username = "sa", roles = {"ADMIN"})
    @DisplayName("Given admin user, when access private resource, then return 200 [OK]")
    public void givenAdminUser_whenAccessPrivateResource_thenReturn200() throws Exception {
        mockMvc.perform(put("/api/v1/private/manager/user/block/1", new User()))
            .andExpect(status().isOk());
    }

    /**
     * Testa acesso a um recurso privado por um usuário autenticado
     */
    @Test
    @WithMockUser("sa")
    @DisplayName("Given user, when access private resource, then return 200 [OK]")
    public void givenUser_whenAccessPrivateResource_thenReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/protected/users"))
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