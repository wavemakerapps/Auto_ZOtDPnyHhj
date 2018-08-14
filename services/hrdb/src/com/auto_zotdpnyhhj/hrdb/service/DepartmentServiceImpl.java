/*Copyright (c) 2018-2019 wavemaker.com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker.com*/
package com.auto_zotdpnyhhj.hrdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.auto_zotdpnyhhj.hrdb.Department;
import com.auto_zotdpnyhhj.hrdb.Employee;


/**
 * ServiceImpl object for domain model class Department.
 *
 * @see Department
 */
@Service("hrdb.DepartmentService")
@Validated
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("hrdb.EmployeeService")
    private EmployeeService employeeService;

    @Autowired
    @Qualifier("hrdb.DepartmentDao")
    private WMGenericDao<Department, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Department, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Department create(Department department) {
        LOGGER.debug("Creating a new Department with information: {}", department);

        Department departmentCreated = this.wmGenericDao.create(department);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(departmentCreated);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Department getById(Integer departmentId) {
        LOGGER.debug("Finding Department by id: {}", departmentId);
        return this.wmGenericDao.findById(departmentId);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Department findById(Integer departmentId) {
        LOGGER.debug("Finding Department by id: {}", departmentId);
        try {
            return this.wmGenericDao.findById(departmentId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Department found with id: {}", departmentId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public List<Department> findByMultipleIds(List<Integer> departmentIds, boolean orderedReturn) {
        LOGGER.debug("Finding Departments by ids: {}", departmentIds);

        return this.wmGenericDao.findByMultipleIds(departmentIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Department getByDeptCode(String deptCode) {
        Map<String, Object> deptCodeMap = new HashMap<>();
        deptCodeMap.put("deptCode", deptCode);

        LOGGER.debug("Finding Department by unique keys: {}", deptCodeMap);
        return this.wmGenericDao.findByUniqueKey(deptCodeMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "hrdbTransactionManager")
    @Override
    public Department update(Department department) {
        LOGGER.debug("Updating Department with information: {}", department);

        this.wmGenericDao.update(department);
        this.wmGenericDao.refresh(department);

        return department;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public Department delete(Integer departmentId) {
        LOGGER.debug("Deleting Department with id: {}", departmentId);
        Department deleted = this.wmGenericDao.findById(departmentId);
        if (deleted == null) {
            LOGGER.debug("No Department found with id: {}", departmentId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Department.class.getSimpleName(), departmentId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "hrdbTransactionManager")
    @Override
    public void delete(Department department) {
        LOGGER.debug("Deleting Department with {}", department);
        this.wmGenericDao.delete(department);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Department> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Departments");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Department> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Departments");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service hrdb for table Department to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service hrdb for table Department to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "hrdbTransactionManager")
    @Override
    public Page<Employee> findAssociatedEmployees(Integer deptId, Pageable pageable) {
        LOGGER.debug("Fetching all associated employees");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("department.deptId = '" + deptId + "'");

        return employeeService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service EmployeeService instance
     */
    protected void setEmployeeService(EmployeeService service) {
        this.employeeService = service;
    }

}