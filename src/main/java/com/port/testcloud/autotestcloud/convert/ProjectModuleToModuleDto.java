package com.port.testcloud.autotestcloud.convert;

import com.port.testcloud.autotestcloud.domain.ProjectModules;
import com.port.testcloud.autotestcloud.domain.Projects;
import com.port.testcloud.autotestcloud.dto.ModuleDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ProjectModuleToModuleDto
 * @CreateUser: wangxiaohao
 * @CreateDate: 2019-07-26 11:45
 * @Description: ProjectModule -> ModuleDto
 */
public class ProjectModuleToModuleDto {

    public static ModuleDto convert(ProjectModules modules){
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(modules,moduleDto);
        return moduleDto;
    }

    public static List<ModuleDto> convert(List<ProjectModules> modulesList){

        List<ModuleDto> moduleDtoList = new ArrayList<>();

        modulesList.forEach(projectModule -> {
            ModuleDto moduleDto = new ModuleDto();
            BeanUtils.copyProperties(projectModule,moduleDto);
            moduleDtoList.add(moduleDto);
        });
        return moduleDtoList;
    }

}
