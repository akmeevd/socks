package com.example.socks.controller;

import com.example.socks.dto.StoreKeeperDto;
import com.example.socks.model.StoreKeeper;
import com.example.socks.service.StoreKeeperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storekeepers")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Tag(name = "storekeeper")
public class StoreKeeperController {

    private final StoreKeeperService service;

    public StoreKeeperController(StoreKeeperService service) {
        this.service = service;
    }

    @Operation(summary = "create storekeeper will enter socks to db")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "entered successfully storekeeper to db ",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StoreKeeper.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request or socks not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StoreKeeperDto> create(@RequestBody StoreKeeper storeKeeper) {
        return ResponseEntity.ok(service.create(storeKeeper));
    }
}
