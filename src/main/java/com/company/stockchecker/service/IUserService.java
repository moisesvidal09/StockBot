package com.company.stockchecker.service;

import com.company.stockchecker.entity.User;

public interface IUserService extends CrudService<User>{

    User getByChatId(Long chatId);

    boolean existsByChatId(Long chadId);

}
