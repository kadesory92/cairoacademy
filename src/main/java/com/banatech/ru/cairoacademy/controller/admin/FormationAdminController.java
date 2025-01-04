package com.banatech.ru.cairoacademy.controller.admin;

import com.banatech.ru.cairoacademy.request.FieldStudyRequest;
import com.banatech.ru.cairoacademy.response.ApiResponse;
import com.banatech.ru.cairoacademy.service.fieldstudy.FieldStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${server.prefix}/admin")
@RequiredArgsConstructor
public class FormationManageController {
    private final FieldStudyService fieldStudyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/fieldstudy/create")
    public ApiResponse createFieldStudy(@RequestBody FieldStudyRequest request){
        return fieldStudyService.createFieldStudy(request);
    }
}
