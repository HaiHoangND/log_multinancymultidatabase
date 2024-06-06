package com.viettel.multitenantmultidatabasedemo.service.impl.master;

import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import com.viettel.multitenantmultidatabasedemo.exception.DatasourceNotFoundException;
import com.viettel.multitenantmultidatabasedemo.repository.master.IMultitenancyMasterDatasourceRepository;
import com.viettel.multitenantmultidatabasedemo.service.master.IMultitenancyMasterDatasourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultitenancyMasterDatasourceServiceImpl implements IMultitenancyMasterDatasourceService {

    @Autowired
    private IMultitenancyMasterDatasourceRepository multitenancyMasterDatasourceRepository;

    @Override
    public MultitenantcyMasterDatasourceEntity findByDataSourceKey(String dataSourceKey) {
        MultitenantcyMasterDatasourceEntity multitenantcyMasterDatasourceEntity = multitenancyMasterDatasourceRepository.findByDataSourceKey(dataSourceKey);
        if(multitenantcyMasterDatasourceEntity == null) {
            throw new DatasourceNotFoundException("Unable to find the datasource " + dataSourceKey);
        }
        return multitenantcyMasterDatasourceEntity;
    }

    public List<MultitenantcyMasterDatasourceEntity> findAll() {
        List<MultitenantcyMasterDatasourceEntity> masterDatasourcesList =  multitenancyMasterDatasourceRepository.findAll();
        if(masterDatasourcesList == null ||  masterDatasourcesList.isEmpty()) {
            throw new DatasourceNotFoundException("There is no datasources");
        }
        return masterDatasourcesList;
    }

    @Override
    public void save(MultitenantcyMasterDatasourceEntity multitenantcyMasterDatasourceEntity) {
        multitenancyMasterDatasourceRepository.save(multitenantcyMasterDatasourceEntity);
    }

    @Override
    public void delete(long Id) {
        multitenancyMasterDatasourceRepository.deleteById(Id);
    }

}