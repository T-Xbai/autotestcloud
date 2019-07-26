package com.port.testcloud.autotestcloud.controller;

import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.form.ProjectsForm;
import com.port.testcloud.autotestcloud.service.ProjectsService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ProjectsController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-25 10:30
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectsController {


    @Autowired
    private ProjectsService projectsService;


    @PostMapping
    public ResultVO create(@Valid @RequestBody ProjectsForm form, BindingResult result) {
        Projects projects = new Projects();

        if (result.hasErrors()) {
            log.error("【项目创建】参数错误：{}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.PROJECT_FORM_ERROR);
        }

        BeanUtils.copyProperties(form, projects);
        projects.setId(KeyUtil.unique());
        Projects saveResult = projectsService.save(projects);

        Map<String, Object> projectMap = new HashMap<>();
        projectMap.put("id", saveResult.getId());
        return ResultVOUtil.success(projectMap);
    }


    @PutMapping("/{projectId}")
    public ResultVO update(@PathVariable("projectId") String projectId,
                           @Valid @RequestBody ProjectsForm form,
                           BindingResult result) {
        Projects projects = new Projects();

        if (result.hasErrors()) {
            log.error("【项目创建】参数错误：{}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.PROJECT_FORM_ERROR);
        }

        Projects queryResult = projectsService.findOne(projectId);
        if (queryResult == null) {
            log.error("【项目创建】项目不存在，projectId = {} ", projectId);
            throw new AutoTestException(ResultEnums.PROJECT_ID_NOT_EXIST);
        }


        BeanUtils.copyProperties(form, projects);
        projects.setId(projectId);
        Projects saveResult = projectsService.save(projects);
        return ResultVOUtil.success(saveResult);
    }

    @GetMapping("/{projectId}")
    public ResultVO findOne(@PathVariable("projectId") String projectId) {

        Projects projects = projectsService.findOne(projectId);
        if (projects == null) {
            log.error("【项目查询】projectId 不存在 ：{}", projectId);
            throw new AutoTestException(ResultEnums.PROJECT_ID_NOT_EXIST);
        }
        return ResultVOUtil.success(projects);


    }

    @GetMapping
    public ResultVO findAll() {
        List<Projects> projectsList = projectsService.findAll();
        return ResultVOUtil.success(projectsList);
    }


    @GetMapping("/search")
    public ResultVO findByProjectName(@RequestParam("projectName") String projectName) {
        List<Projects> projectsList = projectsService.findByProjectNameLike(projectName);
        return ResultVOUtil.success(projectsList);
    }


}
