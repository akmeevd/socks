package com.example.socks.controller;

import com.example.socks.model.Socks;
import com.example.socks.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @Operation(summary = "enter incoming socks to db and calculate sum of certain socks")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "incoming socks successfully entered to db",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Socks.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request or socks not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
                    @PostMapping("/income")
            public ResponseEntity<Socks>income(@Parameter(description = "socks color") @RequestParam String color,
                                               @Parameter(description = "percent of cotton in socks")@RequestParam byte cottonPart,
                                               @Parameter(description = "socks quantity" ) @RequestParam int quantity) {
        return ResponseEntity.ok(socksService.income(color, cottonPart, quantity));
    }

    @Operation(summary = "enter outcoming socks to db and calculate sum of certain socks")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "outcoming socks successfully entered to db",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Socks.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request or socks not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @PostMapping("/outcome")
    public ResponseEntity<Socks> outcome(@Parameter(description = "socks color") @RequestParam String color,
                                         @Parameter(description = "percent of cotton in socks") @RequestParam byte cottonPart,
                                         @Parameter(description = "socks quantity" ) @RequestParam int quantity) {
        return ResponseEntity.ok(socksService.outcome(color, cottonPart, quantity));
    }

    @Operation(summary = "get sum socks by color and cotton part")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "received successfully sum of certain socks ",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))}),
                    @ApiResponse(responseCode = "400", description = "bad request or socks not found"),
                    @ApiResponse(responseCode = "500", description = "server trouble")})
    @GetMapping
    public ResponseEntity<Integer> getSocksNumberByColorAndCottonPart
            (@Parameter(description = "socks color") @RequestParam String color,
             @Parameter(description = "this parameter must have three variables: lessThan, moreThan, equal")
                     @RequestParam String operation,
             @Parameter(description = "percent of cotton in socks") @RequestParam byte cottonPart) {
        return ResponseEntity.ok(socksService.getSocksNumberByColorAndCottonPart(color, operation, cottonPart));
    }

}
