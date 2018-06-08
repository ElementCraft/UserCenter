package com.fqfx.UserCenter.web.mapper;

import com.fqfx.UserCenter.domain.User;
import com.fqfx.UserCenter.web.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper extends EntityMapper<UserInfoDTO, User> {

}
