package com.rsm.bookstoremanager.users.controller;

import com.rsm.bookstoremanager.users.dto.MessageDTO;
import com.rsm.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("system users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess user creation"),
            @ApiResponse(code = 400, message = "Missing required filed, or an error on validation filed rules")
    })
    MessageDTO create(UserDTO userToCreateDTO);
}
