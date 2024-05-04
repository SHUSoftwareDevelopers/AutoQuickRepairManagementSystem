package com.shiyulu.controller;

import com.shiyulu.mapper.CommonMapper;
import com.shiyulu.pojo.Client;
import com.shiyulu.pojo.Result;
import com.shiyulu.pojo.User;
import com.shiyulu.service.CommonService;
import com.shiyulu.utils.JwtUtil;
import com.shiyulu.utils.Md5Util;
import com.shiyulu.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.hibernate.validator.constraints.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/common")
@Validated
public class CommonController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonMapper commonMapper;

    //注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{3,16}$") String account, @Pattern(regexp = "^\\S{5,16}$") String password, String username, String trueName, Integer userType){
        User user = commonService.findByAccount(account);
        if(user == null){
            commonService.addUser(account, password, username, trueName, userType);
            return Result.success();
        }
        return Result.error("该账号已被占用！");
    }

    //登录
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{3,16}$") String account, @Pattern(regexp = "^\\S{5,16}$") String password){
        User user = commonService.findByAccount(account);

        if(user==null){
            return Result.error("账号错误！");
        }

        if(Md5Util.getMD5String(password).equals(user.getPassword())){
            //获得对应的ID 员工/客户ID
            Integer id = commonService.getId(account,user.getUserType());
            System.out.println(id);

            //构造业务数据
            Map<String, Object> claims = new HashMap<>();
            claims.put("account", user.getAccount());
            claims.put("usertype", user.getUserType());
            claims.put("id",id);

            //颁发令牌
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);

            return Result.success(token);
        }
        return Result.error("密码错误！");
    }

    //查询公共的基本属性
    @GetMapping("/queryInfo")
    public Result queryInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");

        User user = commonService.findByAccount(account);
        return Result.success(user);
    }

    //修改公共的基本属性
    @PutMapping("/updateInfo")
    public Result updateInfo(@RequestBody @Validated User user){
        commonService.updateInfo(user);
        return Result.success();
    }

    //更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        commonService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestParam Map<String, String> params, @RequestHeader("Authorization") String token) {
        //1.校验参数
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd))
            return Result.error("缺少必要的参数");

        //检查原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        User loginUser = commonService.findByAccount(account);
        if(loginUser == null) {
            return Result.error("用户不存在！");
        }

        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd)))
            return Result.error("原密码填写不正确");

        //校验newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的密码不一致");
        }

        //2.调用service完成密码更新
        commonService.updatePwd(account, newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
