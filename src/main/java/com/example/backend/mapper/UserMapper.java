package com.example.backend.mapper;

import com.example.backend.entity.User;
import com.example.backend.model.MRegisterResponse;
import com.example.backend.model.MUserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    MRegisterResponse toRegisterResponse(User user);

    MUserProfile toUserProfile(User user);
}
