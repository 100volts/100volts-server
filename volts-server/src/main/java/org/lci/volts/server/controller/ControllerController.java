package org.lci.volts.server.controller;

import lombok.RequiredArgsConstructor;
import org.lci.volts.server.model.request.controller.CollectControllerTimeSheetIfChangedRequest;
import org.lci.volts.server.model.request.controller.ControllerCreateUpdateRequest;
import org.lci.volts.server.model.responce.controller.CollectControllerTimeSheetIfChangedResponse;
import org.lci.volts.server.model.responce.controller.ControllerCreateUpdateResponse;
import org.lci.volts.server.service.ControllerReadTimeSheetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/controller")
@RequiredArgsConstructor
public class ControllerController {
    private final ControllerReadTimeSheetService controllerReadTimeSheetService;

    @PostMapping("/time-sheet")
    public ResponseEntity<CollectControllerTimeSheetIfChangedResponse> controllerGetTimeSheet(@RequestBody CollectControllerTimeSheetIfChangedRequest request){
        return ResponseEntity.ok(controllerReadTimeSheetService.getTimeSheetIfUpdated(request));
    }

    @PostMapping("/time-sheet/set-to-controller")
    public ResponseEntity<CollectControllerTimeSheetIfChangedResponse> controllerHasSetTimeSheet(@RequestBody CollectControllerTimeSheetIfChangedRequest request){
        return ResponseEntity.ok(controllerReadTimeSheetService.controllerHasSetTimeSheet(request));
    }

    @PostMapping("/update")
    public ResponseEntity<ControllerCreateUpdateResponse> createUpdate(@RequestBody ControllerCreateUpdateRequest request){
        return ResponseEntity.ok(controllerReadTimeSheetService.createUpdate(request));
    }
}
