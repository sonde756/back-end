package com.br.techroom.service;

import com.br.techroom.dto.request.AccountLoginRequestDTO;
import com.br.techroom.dto.request.AccountRegisterRequestDTO;
import com.br.techroom.dto.response.AccountLoginResponseDTO;
import com.br.techroom.dto.response.AccountRegisterResponseDTO;
import com.br.techroom.entities.Account;
import com.br.techroom.entities.VerificationCode;
import com.br.techroom.enums.RoleName;
import com.br.techroom.mappers.AccountMapper;
import com.br.techroom.mappers.VerificationCodeMapper;
import com.br.techroom.repository.AccountRepository;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountArgs;
import com.br.techroom.service.business.rules.Account.confirmToken.ConfirmTokenAccountValidation;
import com.br.techroom.service.business.rules.Account.register.RegisterAccountArgs;
import com.br.techroom.service.business.rules.Account.register.RegisterAccountValidation;
import com.br.techroom.utils.ConvertingType;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final List<ConfirmTokenAccountValidation> validationsToken;
    private final List<RegisterAccountValidation> validationsRegister;

    @Autowired
    public AccountService(AccountRepository repository, PasswordEncoder passwordEncoder,
                          AuthenticationManager manager, JwtService jwtService,
                          EmailService emailService, RoleService roleService,
                          List<ConfirmTokenAccountValidation> validationsToken,
                          List<RegisterAccountValidation> validationsRegister) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.manager = manager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.roleService = roleService;
        this.validationsToken = validationsToken;
        this.validationsRegister = validationsRegister;
    }

    /**
     * @param requestDto DTO de representação para o registro do usuário
     * @return AccountRegisterResponseDTO na qual armazena o username e email do usuário
     */
    @Transactional
    public AccountRegisterResponseDTO register(AccountRegisterRequestDTO requestDto) throws MessagingException, UnsupportedEncodingException {

        validationsRegister.forEach(v -> v.verification(new RegisterAccountArgs(requestDto, repository)));

        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        VerificationCode code = generateCode();
        Account entity = AccountMapper.requestDtoToEntity(requestDto);

        entity.setRoles(roleService.separateRolesWithHierarchy(
                ConvertingType.convertStringToEnum(RoleName.class, requestDto.getRole())));

        entity.setToken(code);

        AccountRegisterResponseDTO response = new AccountRegisterResponseDTO(
                entity.getId(), entity.getEmail(), VerificationCodeMapper.entityToResponseDTO(code));

        response.setRoles(entity.getRoles());

        repository.save(entity);


        emailService.sendEmailConfirmation("Confirmação de Cadastro", entity.getEmail(),
                emailService.assembleHTMLEmailStructureForToken(entity.getUsername(), code.getToken()));

        return response;
    }

    @Transactional
    public AccountLoginResponseDTO login(AccountLoginRequestDTO requestDto) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        return new AccountLoginResponseDTO(authentication.getName(),
                jwtService.generateToken((Account) authentication.getPrincipal()));
    }

    @Transactional
    public void confirmEmail(String token) {
        validationsToken.forEach(v -> v.verification(new ConfirmTokenAccountArgs(token, repository)));

        Account entity = repository.findByToken(token);
        entity.setEnabled(true);
        repository.save(entity);
    }

    public static VerificationCode generateCode() {
        String code = RandomStringUtils.randomAlphabetic(64);
        return new VerificationCode(code, LocalDateTime.now().plusDays(1));
    }
}