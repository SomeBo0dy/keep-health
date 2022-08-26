package team.keephealth.xyj.modules.keephealth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import team.keephealth.xyj.modules.keephealth.domain.ResponseResult;
import team.keephealth.xyj.modules.keephealth.domain.dto.FollowDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectOtherWatchPageDto;
import team.keephealth.xyj.modules.keephealth.domain.dto.SelectUserPageDto;
import team.keephealth.xyj.modules.keephealth.domain.entity.WatchList;

/**
 * 关注列表(WatchList)表服务接口
 *
 * @author xyj
 * @since 2022-07-29 14:30:06
 */
public interface WatchListService extends IService<WatchList> {

    ResponseResult followUserById(FollowDto followDto);

    ResponseResult unFollowUserById(Long userId);

    ResponseResult getWatchList(SelectUserPageDto userPageDto);

    ResponseResult getWatchListById(SelectOtherWatchPageDto userPageDto);
}

