package com.viettel.multitenantmultidatabasedemo.service.master;

import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;

import java.util.List;

public interface IMultitenancyMasterDatasourceService {

    MultitenantcyMasterDatasourceEntity findByDataSourceKey(String dataSourceKey);

    List<MultitenantcyMasterDatasourceEntity> findAll();

    public void save(MultitenantcyMasterDatasourceEntity multitenantcyMasterDatasourceEntity);

    public void delete(long Id);
}
