package team.keephealth.yjj.controller.recipe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;
import team.keephealth.common.annotation.SystemLog;
import team.keephealth.yjj.domain.dto.recipe.RecipeInfoDto;
import team.keephealth.yjj.domain.vo.ResultVo;
import team.keephealth.yjj.service.recipe.RecipeService;

import javax.annotation.Resource;

@Api(tags = {"我的食谱操作接口"})
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Resource
    private RecipeService recipeService;

    @ApiOperation(value = "添加我的食谱")
    @SystemLog(businessName = "添加我的食谱")
    @PutMapping("/add")
    public ResultVo<T> addRecipe(@RequestBody RecipeInfoDto dto){
        return recipeService.addRecipe(dto);
    }

    @ApiOperation(value = "更改我的食谱")
    @SystemLog(businessName = "更改我的食谱")
    @PutMapping("/update")
    public ResultVo<T> updateRecipe(@RequestBody RecipeInfoDto dto){
        return recipeService.updateRecipe(dto);
    }

    @ApiOperation(value = "获取我的食谱信息")
    @SystemLog(businessName = "获取我的食谱信息")
    @GetMapping("/detail/{recipeId}")
    public ResultVo<T> getDetail(@PathVariable("recipeId") Long recipeId){
        return recipeService.getDetail(recipeId);
    }

    @ApiOperation(value = "删除我的食谱")
    @SystemLog(businessName = "删除我的食谱")
    @DeleteMapping("/delete/{recipeId}")
    public ResultVo<T> deleteRecipe(@PathVariable("recipeId") Long recipeId){
        return recipeService.deleteRecipe(recipeId);
    }

}
