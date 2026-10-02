package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface GetUsersUseCase {
    PageInfo<UserInfo> execute(UserFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo sortInfo);
}
