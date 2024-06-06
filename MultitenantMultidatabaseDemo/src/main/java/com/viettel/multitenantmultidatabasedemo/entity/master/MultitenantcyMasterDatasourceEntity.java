package com.viettel.multitenantmultidatabasedemo.entity.master;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="MULTITENANCY_MASTER_DATASOURCE")
public class MultitenantcyMasterDatasourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATASOURCE_KEY")
    private String dataSourceKey;

    @Column(name = "DATASOURCE_USERNAME")
    private String dataSourceUserName;

    @Column(name = "DATASOURCE_PASSWORD")
    private String dataSourcePassword;

    @Column(name = "DRIVER_CLASSNAME")
    private String driverClassName;

    @Column(name = "URL")
    private String url;

    @Override
    public String toString() {
        return String.format(
                "MultitenantcyMasterDatasourceEntity [id=%s, dataSourceKey=%s, dataSourceUserName=%s, dataSourcePassword=%s, driverClassname=%s, url=%s]",
                id, dataSourceKey, dataSourceUserName, dataSourcePassword, driverClassName, url);
    }

}