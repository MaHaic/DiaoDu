package cc.c3q.algo.evolve.fms.web.ctrl;

import javax.servlet.http.HttpSession;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cc.c3q.algo.evolve.fms.web.bean.User;
import cc.c3q.algo.evolve.fms.web.session.UserSession;
import cc.c3q.algo.evolve.fms.web.svc.UserSvc;

import java.util.HashMap;

@RestController
@RequestMapping("/api/session")
public class SessionCtrl
{
	@Autowired
	public UserSession userSession;
	
	@Autowired
	public UserSvc userSvc;
	
//	@PostMapping("/user/login/")
//	public BaseRet login(HttpSession session, @RequestParam String name, @RequestParam String pswd)
//	{
//		Integer id = userSvc.access(name, pswd);
//		if(id == null) return BaseRet.err("用户名或密码错误");
//
//		userSession.setUserId(session, id);
//		return BaseRet.ok(id);
//	}
	@PostMapping("/user/login/")
	public String login(HttpSession session, @RequestBody User user)
	{
		String flag ="error";
		HashMap<String,Object> res=new HashMap<>();
		System.out.println("user:" + user);
		if(user.name!=null){
			flag="ok";
		}
		res.put("name",user.name);
		res.put("flag",flag);
		String res_json= JSONObject.toJSONString(res);
		return res_json;
	}
	
	@GetMapping("/user/loginout/")
	public BaseRet loginout(HttpSession session)
	{
		Integer id = userSession.getUserId(session);
		userSession.setUserId(session, null);
		
		return BaseRet.ok(id);
	}
	
	@GetMapping("/user/")
	public BaseRet currentUser(HttpSession session)
	{
		Integer id = userSession.getUserId(session);
		if(id == null) return BaseRet.ok();
		
		 User user = userSvc.query(id);
		 return BaseRet.ok(user);
	}
}
