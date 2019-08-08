package com.port.testcloud.autotestcloud.repository;

import com.port.testcloud.autotestcloud.domain.DataVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: DataVariableRepository
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-08-08 23:25
 * @Description:
 */
public interface DataVariableRepository extends JpaRepository<DataVariable,String> {

    List<DataVariable> findByProjectId(String projectId);



}
