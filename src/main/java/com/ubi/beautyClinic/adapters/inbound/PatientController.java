package com.ubi.beautyClinic.adapters.inbound;

import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.inbound.request.JwtRequest;
import com.ubi.beautyClinic.adapters.inbound.request.PatientRequest;
import com.ubi.beautyClinic.adapters.inbound.response.JwtResponse;
import com.ubi.beautyClinic.adapters.inbound.response.PatientResponse;
import com.ubi.beautyClinic.application.core.domain.Patient;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.PatientUseCaseInboundPort;
import com.ubi.beautyClinic.config.JwtTokenUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/patients")
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Beauty Clinic API", version = "1.0"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class PatientController {

    private final PatientUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Tag(name = "Pacientes")
    @Operation(summary = "Autenticação de paciente")
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = inboundPort.loadPatientByEmail(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails, "PATIENT");

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Tag(name = "Pacientes")
    @PostMapping("/register")
    @Operation(summary = "Cadastrar um novo paciente")
    public ResponseEntity<PatientResponse> registerPatient(@RequestBody @Valid PatientRequest requestDTO) {

        var patient = mapper.mapTo(requestDTO, Patient.class);
        var savedPatient = inboundPort.registerPatient(patient);
        var responseDTO = mapper.mapTo(savedPatient, PatientResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Tag(name = "Pacientes")
    @GetMapping
    @Operation(summary = "Obter todos os pacientes")
    public ResponseEntity<List<PatientResponse>> listAll() {

        var patients = inboundPort.getAllPatients();
        return ResponseEntity.ok(mapper.mapToList(patients, new TypeToken<List<PatientResponse>>() {}.getType()));
    }

    @Tag(name = "Pacientes")
    @GetMapping("/{id}")
    @Operation(summary = "Obter paciente pelo ID")
    public ResponseEntity<PatientResponse> findPatientById(@Parameter(description = "ID do paciente") @PathVariable Long id) {

        var patient = inboundPort.findPatientById(id);
        return ResponseEntity.ok(mapper.mapTo(patient, PatientResponse.class));
    }

    @Tag(name = "Pacientes")
    @GetMapping("/search/{fullName}")
    @Operation(summary = "Obter paciente pelos primeiros caracteres do nome")
    public ResponseEntity<List<PatientResponse>> findPatientByFullNameStartingWith(@Parameter(description = "Nome do paciente (parcial ou completo)") @PathVariable(required = false) String fullName) {

        var patients = inboundPort.findPatientByFullNameStartingWith(fullName);
        return ResponseEntity.ok(mapper.mapToList(patients, new TypeToken<List<PatientResponse>>() {}.getType()));
    }

    @Tag(name = "Pacientes")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados de um paciente")
    public ResponseEntity<PatientResponse> updatePatientData (@Parameter(description = "ID do paciente") @Valid @PathVariable Long id, @RequestBody PatientRequest patientRequest) throws BusinessLogicException {

        var patient = mapper.mapTo(patientRequest, Patient.class);
        patient.setId(id);
        return ResponseEntity.ok(mapper.mapTo(inboundPort.updatePatientData(patient), PatientResponse.class));
    }

    @Tag(name = "Pacientes")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir os dados de um paciente")
    public ResponseEntity<PatientResponse> deletePatient(@Parameter(description = "ID do paciente") @PathVariable Long id) {

        inboundPort.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}

