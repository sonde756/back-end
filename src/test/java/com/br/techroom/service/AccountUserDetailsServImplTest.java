//package com.br.techroom.service;
//
//import com.br.techroom.constants.StatusConstants;
//import com.br.techroom.entities.Account;
//import com.br.techroom.enums.RoleName;
//import com.br.techroom.repository.AccountRepository;
//import com.br.techroom.service.StatusService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AccountUserDetailsServImplTest {
//
//
//    @InjectMocks
//    AccountUserDetailsService accountUserDetailsServ;
//
//    @Mock
//    AccountRepository accountRepository;
//
//    @Mock
//    StatusService statusService;
//
//    String username;
//    String email;
//
//    final Account accountModel = new Account();
//
//    @BeforeEach
//    void setUp() {
//        username = "techroom";
//        email = "techroom@techroom.com.br";
//        accountModel.setUsername("techroom");
//        accountModel.setEmail("techroom@techroom.com.br");
//        accountModel.setPassword("12345");
//        accountModel.setStatus(new Role());
//    }
//
//    @Test
//    void loadUserByUsernameSuccessfullyWhenPassValidUsername() {
//        when(this.accountRepository.findByUsername(username)).thenReturn(Optional.of(accountModel));
//        UserDetails userDetails = this.accountUserDetailsServ.loadUserByUsername(username);
//        lenient().when(this.statusService.findByStatus(StatusConstants.TYPE_USER, StatusConstants.USER_NEW)).thenReturn(new Role());
//                assertEquals(userDetails.getUsername(), accountModel.getUsername());
//        assertEquals(userDetails.getPassword(), accountModel.getPassword());
//
//    }
//
//    @Test
//    void loadUserByUsernameSuccessfullyWhenPassValidEmail() {
//        when(this.accountRepository.findByEmail(email)).thenReturn(Optional.of(accountModel));
//        UserDetails userDetails = this.accountUserDetailsServ.loadUserByUsername(email);
//        lenient().when(this.statusService.findByStatus(StatusConstants.TYPE_USER, StatusConstants.USER_NEW)).thenReturn(new Role());
//        assertEquals(userDetails.getUsername(), accountModel.getUsername());
//        assertEquals(userDetails.getPassword(), accountModel.getPassword());
//    }
//
//    @Test
//    void loadUserByUsernameThrowsUsernameNotFoundExceptionWhenPassInvalidUsername() {
//
//        when(this.accountRepository.findByUsername(username)).thenReturn(Optional.empty());
//        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
//                () -> this.accountUserDetailsServ.loadUserByUsername(username));
//
//        Assertions.assertEquals("Usuário não encontrado", thrown.getMessage());
//
//
//    }
//
//    @Test
//    void loadUserByUsernameThrowsUsernameNotFoundExceptionWhenPassInvalidEmail() {
//
//        when(this.accountRepository.findByEmail(email)).thenReturn(Optional.empty());
//        UsernameNotFoundException thrown = Assertions.assertThrows(UsernameNotFoundException.class,
//                () -> this.accountUserDetailsServ.loadUserByUsername(email));
//
//        Assertions.assertEquals("Usuário não encontrado", thrown.getMessage());
//    }
//}