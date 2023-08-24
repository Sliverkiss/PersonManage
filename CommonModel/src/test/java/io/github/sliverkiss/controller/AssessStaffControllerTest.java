package io.github.sliverkiss.controller;

import io.github.sliverkiss.domain.entity.assess.AssessStaff;
import io.github.sliverkiss.service.AssessStaffService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/8/18
 */
@SpringBootTest
@Slf4j
class AssessStaffControllerTest {

    @Autowired
    private AssessStaffService service;

    @Test
    void save() {
        AssessStaff staff = new AssessStaff ();
        staff.setAssessId ( 1 ).setDeptId ( 6 );
        log.info ( service.saveStaffAndDeclare ( staff ).toString () );

    }
}