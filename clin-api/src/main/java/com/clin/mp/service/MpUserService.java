package com.clin.mp.service;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/
public interface MpUserService {




    LinkedHashMap<String, String> login(String  username, String password);
}
