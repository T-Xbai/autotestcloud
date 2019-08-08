package com.port.testcloud.autotestcloud.controller.projects;

import com.port.testcloud.autotestcloud.VO.ModuleVO;
import com.port.testcloud.autotestcloud.VO.ResultVO;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import com.port.testcloud.autotestcloud.enums.DeleteStatusEnums;
import com.port.testcloud.autotestcloud.enums.ResultEnums;
import com.port.testcloud.autotestcloud.exception.AutoTestException;
import com.port.testcloud.autotestcloud.form.ModuleForm;
import com.port.testcloud.autotestcloud.service.projects.ProjectModuleService;
import com.port.testcloud.autotestcloud.service.projects.ProjectsService;
import com.port.testcloud.autotestcloud.utils.KeyUtil;
import com.port.testcloud.autotestcloud.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ProjectModuleController
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-26 12:25
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/project/module")
public class ProjectModuleController {

    @Autowired
    private ProjectModuleService moduleService;

    @Autowired
    private ProjectsService projectsService;


    @GetMapping("/{moduleId}")
    public ResultVO findOne(@PathVariable("moduleId") String moduleId) {

        ModuleDto moduleDto = moduleService.findOne(moduleId);
        if (moduleDto == null || moduleDto.getIsDelete().equals(DeleteStatusEnums.DEL.getCode())) {
            log.error("【模块查询】模块状态异常，moduleId = {},queryResult={}", moduleId, moduleDto);
            throw new AutoTestException(ResultEnums.MODULE_ERROR);
        }

        List<ModuleDto> moduleDtoList = moduleService.sonAll(moduleId);
        moduleDto.setSonList(moduleDtoList);

        ModuleVO moduleVO = new ModuleVO();
        BeanUtils.copyProperties(moduleDto, moduleVO);
        return ResultVOUtil.success(moduleVO);
    }


    @GetMapping
    public ResultVO findALl(@RequestParam("projectId") String projectId) {

        Projects project = projectsService.findOne(projectId);
        if (project == null || project.getId() == null) {
            log.error("【查询项目下的所有模块】projectId 不存在：{}", projectId);
            throw new AutoTestException(ResultEnums.PROJECT_ID_NOT_EXIST);
        }
        List<ModuleDto> moduleDtoList = moduleService.findAllByProjectId(projectId);
        moduleDtoList.sort(Comparator.comparing(ModuleDto::getIndexs));
        return ResultVOUtil.success(moduleDtoList);
    }


    @PostMapping
    public ResultVO create(@RequestBody @Valid ModuleForm form, BindingResult result) {

        ModuleDto moduleDto = new ModuleDto();
        if (result.hasErrors()) {
            log.error("【模块创建】请求参数错误：{}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.FORM_PARAM_ERROR);
        }

        BeanUtils.copyProperties(form, moduleDto);
        moduleDto.setId(KeyUtil.unique());
        moduleService.save(moduleDto);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("moduleId", moduleDto.getId());

        return ResultVOUtil.success(responseBody);
    }


    @PutMapping("/{moduleId}")
    public ResultVO update(@PathVariable("moduleId") String moduleId,
                           @RequestBody @Valid ModuleForm form,
                           BindingResult result) {

        if (result.hasErrors()) {
            log.error("【模块更新】请求参数错误: {}", result.getFieldError().getDefaultMessage());
            throw new AutoTestException(ResultEnums.FORM_PARAM_ERROR);
        }

        ModuleDto moduleDto = moduleService.findOne(moduleId);
        if (moduleDto == null || moduleDto.getId() == null) {
            log.error("【模块更新】模块不存在，moduleId = {},queryResult = {}", moduleId, moduleDto);
            throw new AutoTestException(ResultEnums.MODULE_NOT_EXIST);
        }

        BeanUtils.copyProperties(form,moduleDto);
        moduleService.save(moduleDto);

        return ResultVOUtil.success(moduleDto);
    }


}
