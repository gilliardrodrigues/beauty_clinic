package com.ubi.beautyClinic.adapters.inbound;

import com.ubi.beautyClinic.adapters.inbound.mappers.GenericMapper;
import com.ubi.beautyClinic.adapters.inbound.request.AppointmentRequest;
import com.ubi.beautyClinic.adapters.inbound.response.AppointmentResponse;
import com.ubi.beautyClinic.application.core.domain.Appointment;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import com.ubi.beautyClinic.application.ports.in.AppointmentUseCaseInboundPort;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Beauty Clinic API", version = "1.0"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class AppointmentController {

    private final AppointmentUseCaseInboundPort inboundPort;
    private final GenericMapper mapper;

    @Tag(name = "Agendamentos")
    @PostMapping
    @Operation(summary = "Solicitar um agendamento")
    public ResponseEntity<AppointmentResponse> requestAppointment(@RequestBody @Valid AppointmentRequest requestDTO) {

        var appointment = mapper.mapTo(requestDTO, Appointment.class);
        var savedAppointment = inboundPort.requestAppointment(appointment);
        var responseDTO = mapper.mapTo(savedAppointment, AppointmentResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Tag(name = "Agendamentos")
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir os dados de um agendamento")
    public ResponseEntity<AppointmentResponse> deleteAppointment(@Parameter(description = "ID do agendamento") @PathVariable Long id) {

        inboundPort.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Agendamentos")
    @GetMapping("/{id}")
    @Operation(summary = "Obter agendamento pelo ID")
    public ResponseEntity<AppointmentResponse> findAppointmentById(@Parameter(description = "ID do agendamento") @PathVariable Long id) {

        var appointment = inboundPort.getAppointmentById(id);
        return ResponseEntity.ok(mapper.mapTo(appointment, AppointmentResponse.class));
    }

    @Tag(name = "Agendamentos")
    @GetMapping
    @Operation(summary = "Listar agendamentos do usuário logado")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsForLoggedUser() {

        var appointments = inboundPort.listAppointmentsForLoggedUser();
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }

    @Tag(name = "Agendamentos")
    @GetMapping("/filter/by-date/{date}")
    @Operation(summary = "Filtrar agendamentos pela data")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByDate(@Parameter(description = "Data do agendamento") @PathVariable OffsetDateTime date) {

        var appointments = inboundPort.listAppointmentsByDate(date);
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }
    @Tag(name = "Agendamentos")
    @GetMapping("/filter/by-datetime/{dateTime}")
    @Operation(summary = "Filtrar agendamentos pela data e hora")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByDateTime(@Parameter(description = "Data e hora do agendamento") @PathVariable OffsetDateTime dateTime) {

        var appointments = inboundPort.listAppointmentsByDateTime(dateTime);
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }

    @Tag(name = "Agendamentos")
    @GetMapping("/filter/by-service/{service}")
    @Operation(summary = "Filtrar agendamentos pelo serviço")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByService(@Parameter(description = "Serviço agendado") @PathVariable ServiceEnum service) {

        var appointments = inboundPort.listAppointmentsByService(service);
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }

    @Tag(name = "Agendamentos")
    @GetMapping("/filter/by-status/{status}")
    @Operation(summary = "Filtrar agendamentos pelo status")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByStatus(@Parameter(description = "Status do agendamento") @PathVariable Status status) {

        var appointments = inboundPort.listAppointmentsByStatus(status);
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }

    @Tag(name = "Agendamentos")
    @GetMapping("/filter/by-rating/{rating}")
    @Operation(summary = "Filtrar agendamentos pela avaliação")
    public ResponseEntity<List<AppointmentResponse>> listAppointmentsByRating(@Parameter(description = "Avaliação do agendamento") @PathVariable Integer rating) {

        var appointments = inboundPort.listAppointmentsByRating(rating);
        return ResponseEntity.ok(mapper.mapToList(appointments, new TypeToken<List<AppointmentResponse>>() {}.getType()));
    }

    @Tag(name = "Agendamentos")
    @PutMapping("/{id}/accept")
    @Operation(summary = "Aceitar consulta")
    public ResponseEntity<AppointmentResponse> acceptAppointment(@PathVariable Long id) {

        var appointment = inboundPort.acceptAppointment(id);
        return ResponseEntity.ok(mapper.mapTo(appointment, AppointmentResponse.class));
    }

    @Tag(name = "Agendamentos")
    @PutMapping("/{id}/refuse")
    @Operation(summary = "Recusar consulta")
    public ResponseEntity<AppointmentResponse> refuseAppointment(@PathVariable Long id) {

        var appointment = inboundPort.refuseAppointment(id);
        return ResponseEntity.ok(mapper.mapTo(appointment, AppointmentResponse.class));
    }
}
