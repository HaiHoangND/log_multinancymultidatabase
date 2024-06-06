package com.viettel.multitenantmultidatabasedemo.controller.master;

import java.sql.SQLException;
import java.util.List;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import com.viettel.multitenantmultidatabasedemo.jpa.TenantDataSource;
import com.viettel.multitenantmultidatabasedemo.service.master.IMultitenancyMasterDatasourceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v2")
@Log4j2
public class MultitenancyMasterDatasourceController {

    @Autowired
    private IMultitenancyMasterDatasourceService multitenancyMasterDatasourceService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/tenants")
    @Transactional
    public ResponseEntity<List<MultitenantcyMasterDatasourceEntity>> getAllTenants() {
        return ResponseEntity.ok(multitenancyMasterDatasourceService.findAll());
    }

    @GetMapping("/loadAllTenants")
    public ResponseEntity<String> index() {
        List<MultitenantcyMasterDatasourceEntity> multitenantcyMasterDatasourcesList = multitenancyMasterDatasourceService.findAll();
        TenantDataSource tenantDataSource = applicationContext.getBean(TenantDataSource.class);
        tenantDataSource.loadAllTenants(multitenantcyMasterDatasourcesList);
        return ResponseEntity.ok("Load tenants thành công");
    }

    @PostMapping(value = SpringDataJPAMultitenantConstants.ADD_TENANT_PATH)
    @Transactional
    public ResponseEntity<String> addTenant(@RequestBody MultitenantcyMasterDatasourceEntity tenant) throws SQLException {
        TenantDataSource tenantDataSource = applicationContext.getBean(TenantDataSource.class);
        tenantDataSource.loadTenant(tenant);
        tenantDataSource.createSchema(tenant.getDataSourceKey());
        multitenancyMasterDatasourceService.save(tenant);
        return ResponseEntity.ok("Thêm " + tenant.getDataSourceKey() + " thành công");
    }

    @DeleteMapping(value = SpringDataJPAMultitenantConstants.DELETE_TENANT_PATH)
    @Transactional
    public ResponseEntity<String> deleteTenant(@PathVariable String tenantName) {
        if (tenantName != null) {
            MultitenantcyMasterDatasourceEntity tenant = multitenancyMasterDatasourceService.findByDataSourceKey(tenantName);
            TenantDataSource tenantDataSource = applicationContext.getBean(TenantDataSource.class);
            tenantDataSource.dropSchema(tenantName);
            multitenancyMasterDatasourceService.delete(tenant.getId());
        } else {
            log.debug("Tenant Name is missing in the request parameter");
        }
        return ResponseEntity.ok("Xóa " + tenantName + " thành công");
    }
}
