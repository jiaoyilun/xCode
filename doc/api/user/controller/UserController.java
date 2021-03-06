package com.cn.xmf.api.user.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.cn.xmf.model.user.*;
import com.cn.xmf.base.model.*;
import com.cn.xmf.utils.*;
import com.cn.xmf.api.common.CommonService;
import com.cn.xmf.api.user.service.*;
/**
 * UserController(用户信息)
 * @author airufei
 * @version 2018-09-11
 */
@RestController
@RequestMapping("/user/")
@SuppressWarnings("all")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserService userService;
	@Autowired
    private CommonService commonService;

	/**
	 * getList:(获取用户信息分页查询接口)
	 * @Author airufei
	 * @param request
	 * @param parms
	 * @return
	 */
	@RequestMapping("getList")
	public DataReturn getList(HttpServletRequest request, String parms){
		DataReturn dataReturn = new DataReturn();
	   try {
	        logger.info("getList:(获取用户信息分页查询接口) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            int pageNo = json.getIntValue("pageNo");
            int pageSize = json.getIntValue("pageSize");
            JSONObject param = StringUtil.getPageJSONObject(pageNo, pageSize);
            param.putAll(json);
            Partion pt = userService.getList(param);
            List<User> list = null;
            int totalCount = 0;
            if (pt != null) {
                list = (List<User>) pt.getList();
                totalCount = pt.getPageCount();
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", list);
            jsonObject.put("totalCount", totalCount);
            dataReturn.setData(jsonObject);
            dataReturn.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            dataReturn.setMessage("服务繁忙，请稍后再试");
            String msg="getList:(获取用户信息分页查询接口) 异常====>"+ StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("getList", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            e.printStackTrace();
        }
        logger.info("getList:(获取用户信息分页查询接口) 结束  parms={}", parms);
        return dataReturn;
	}

     /**
     * getUserList:(获取用户信息不分页查询接口)
     * @Author airufei
     * @param request
     * @param parms
     * @return
     */
    @RequestMapping("getUserList")
    public DataReturn getUserList(HttpServletRequest request, String parms) {
        DataReturn dataReturn = new DataReturn();
        try {
            logger.info("getUserList:(获取用户信息不分页查询接口) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User user=json.toJavaObject(User.class);
            if (user == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            List<User> list= userService.getUserList(user);
            dataReturn.setData(list);
            dataReturn.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            dataReturn.setMessage("服务器繁忙，请稍后再试");
            String msg="getUserList:(获取用户信息不分页查询接口)====>"+ StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("getUserList", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            e.printStackTrace();
        }
        logger.info("getUserList:(获取用户信息不分页查询接口) 结束  parms={},", parms);
        return dataReturn;
    }


 /**
     * getUserByNo:(查询用户信息单条数据接口-带缓存)
     * @Author airufei
     * @param request
     * @param parms
     * @return
     */
    @RequestMapping("getUser")
    public DataReturn getUserByNo(HttpServletRequest request, String parms) {
        DataReturn dataReturn = new DataReturn();
        try {
            logger.info("getUserByNo:(查询用户信息单条数据接口-带缓存) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User user=json.toJavaObject(User.class);
            if (user == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User retuser= userService.getUser(user);
            dataReturn.setData(retuser);
            dataReturn.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            dataReturn.setMessage("服务器繁忙，请稍后再试");
            String msg="getUserByNo:(查询用户信息单条数据接口-带缓存)  异常====>"+StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("getUserByNo", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            e.printStackTrace();
        }
        logger.info("getUser:getUserByNo:(查询用户信息单条数据接口-带缓存) 结束  parms={},", parms);
        return dataReturn;
    }

     /**
     * getUser:(查询用户信息单条数据接口)
     * @Author airufei
     * @param request
     * @param parms
     * @return
     */
    @RequestMapping("getUser")
    public DataReturn getUser(HttpServletRequest request, String parms) {
        DataReturn dataReturn = new DataReturn();
        try {
            logger.info("getUser:(查询用户信息单条数据接口) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User user=json.toJavaObject(User.class);
            if (user == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User retuser= userService.getUser(user);
            dataReturn.setData(retuser);
            dataReturn.setCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            dataReturn.setMessage("服务器繁忙，请稍后再试");
             String msg="getUser:(查询用户信息单条数据接口) 异常====>"+StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("getUser", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            e.printStackTrace();
        }
        logger.info("getUser:(查询用户信息单条数据接口) 结束  parms={},", parms);
        return dataReturn;
    }

	/**
	 * delete:(逻辑删除用户信息数据接口)
	 * @Author airufei
     * @param request
     * @param parms
     * @return
	 */
	@RequestMapping("delete")
	public DataReturn delete(HttpServletRequest request, String parms){
	  DataReturn dataReturn = new DataReturn();
        dataReturn.setCode(ResultCode.FAILURE);
        try {
            logger.info("delete:(逻辑删除用户信息数据接口) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            Long id = json.getLong("id");
            if (id != null && id > 0) {
                userService.delete(id);
                dataReturn.setMessage("删除成功");
                dataReturn.setCode(ResultCode.SUCCESS);
            } else {
                dataReturn.setMessage("请选择需要删除的数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg="delete:(逻辑删除用户信息数据接口) error===>" + StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("delete", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            dataReturn.setMessage("服务器繁忙，请稍后再试");
        }
        logger.info("delete:(逻辑删除用户信息数据接口) 结束  parms={}", parms);
        return dataReturn;
	}
	
	/**
	 * save:(保存用户信息数据接口)
	 * @Author airufei
     * @param request
     * @param parms
     * @return
	 */
	@RequestMapping(value = "save")
    public DataReturn save(HttpServletRequest request, String parms) {
		DataReturn dataReturn = new DataReturn();
        try {
            logger.info("save:(保存用户信息数据接口) 开始  parms={}", parms);
            if (StringUtil.isBlank(parms)) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            JSONObject json = JSONObject.parseObject(parms);
            if (json == null) {
                dataReturn.setMessage("参数为空");
                return dataReturn;
            }
            User user=json.toJavaObject(User.class);
            // 无保存内容
            if (user == null) {
                dataReturn.setMessage("无保存内容");
                return dataReturn;
            }
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            // 保存数据库
            User ret =userService.save(user);
            if(ret!=null)
            {
              dataReturn.setCode(ResultCode.SUCCESS);
               dataReturn.setMessage("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg="save:(保存用户信息数据接口) error===>" + StringUtil.getExceptionMsg(e);
            logger.error(msg);
            commonService.sendDingMessage("save", parms, JSON.toJSONString(dataReturn), msg, this.getClass());
            dataReturn.setMessage("服务器繁忙，请稍后再试");
            return dataReturn;
        }
        logger.info("save:(保存用户信息数据接口) 结束  parms={}", parms);
        return dataReturn;
	}

}