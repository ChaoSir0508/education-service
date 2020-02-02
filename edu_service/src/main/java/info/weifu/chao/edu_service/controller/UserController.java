package info.weifu.chao.edu_service.controller;

import info.weifu.chao.edu_common.R;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    /**
     * 用户登录
     * @return
     */
    @PostMapping("login")
    public R login() {
        return R.OK().data("token","admin");
    }

    /**
     * 用户信息
     * @return
     */
    @GetMapping("info")
    public R info(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.OK().data(map);
    }

}
