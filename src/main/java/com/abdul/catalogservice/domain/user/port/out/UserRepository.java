package com.abdul.catalogservice.domain.user.port.out;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface UserRepository {
    PageInfo<UserInfo> getAll(UserFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo sortInfo);

    UserInfo getUserById(Long id);

    UserInfo createUser(UserInfo userInfo);

    UserInfo updateUser(UserInfo userInfo);

    UserInfo findFirstArtistUser();

    UserInfo findArtistUserAfterArtistId(Long lastArtistId);
}
