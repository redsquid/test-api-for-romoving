package org.example.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.RecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Record management")
@RestController
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record has been created"),
            @ApiResponse(responseCode = "500", description = "Record creating has been failed",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/record")
    public void create(@Parameter(description = "Records will be created with datetime", example = "yyyy-MM-dd.HH:mm:ss")
                           @RequestParam("localDateTime") LocalDateTime localDateTime) {

        service.createRecord(localDateTime);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Removing process has been started"),
            @ApiResponse(responseCode = "500", description = "Starting deletion process has been failed",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/record/deleting:start")
    public void startDeleting(@Parameter(description = "Records with earlier datetime will be deleted", example = "yyyy-MM-dd.HH:mm:ss")
                                  @RequestParam("localDateTime") LocalDateTime localDateTime) {

        service.startDeletingProcess(localDateTime);
    }
}
