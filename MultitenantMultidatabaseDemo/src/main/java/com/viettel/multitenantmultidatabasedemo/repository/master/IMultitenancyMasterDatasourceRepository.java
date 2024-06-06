package com.viettel.multitenantmultidatabasedemo.repository.master;

import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMultitenancyMasterDatasourceRepository extends JpaRepository<MultitenantcyMasterDatasourceEntity, Long> {

    MultitenantcyMasterDatasourceEntity findByDataSourceKey(String dataSourceKey);
}
